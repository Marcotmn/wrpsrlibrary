package tumminia.wrpsrlibrary.books;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tumminia.wrpsrlibrary.exceptions.BadRequestException;
import tumminia.wrpsrlibrary.exceptions.NotFoundException;
import tumminia.wrpsrlibrary.publishers.Publisher;
import tumminia.wrpsrlibrary.publishers.PublisherRepository;
import tumminia.wrpsrlibrary.publishers.PublisherService;
import tumminia.wrpsrlibrary.users.User;
import tumminia.wrpsrlibrary.users.UserRepository;
import tumminia.wrpsrlibrary.users.UserService;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	PublisherRepository publisherRepository;

	@Autowired
	PublisherService publisherService;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	// METODO PER SALVARE IL LIBRO (CONTROLLO SU TITOLO E PUBLISHER) -> ADMIN
	public Book save(NewBookPayload body) {
		bookRepository.findByTitolo(body.getTitolo()).ifPresent(book -> {
			throw new BadRequestException("Il titolo di questo libro è gia presente nel database");
		});
		Publisher editore = publisherService.findPublisherByName(body.getEditore());
		if (editore == null) {
			throw new BadRequestException("L'editore non esiste");
		}

		Book newBook = new Book(body.getTitolo(), editore);
		System.out.println("Libro salvato");
		return bookRepository.save(newBook);
	}

	// METODO PER AGGIORNARE IL LIBRO (CONTROLLO SU TITOLO E PUBLISHER) -> ADMIN
	public Book findByIdAndUpdate(UUID idLibro, NewBookPayload body) {
		// VERIFICA SE IL LIBRO è PRESENTE IN DB
		Optional<Book> bookToUpdate = bookRepository.findById(idLibro);

		if (bookToUpdate.isPresent()) {
			Book updatedBook = bookToUpdate.get();

			// AGGIORNA IL TITOLO
			String newTitle = body.getTitolo();
			// VERIFICA CHE IL NUOVO TITOLO NON SIA UGUALE ALL'ORIGINALE
			if (!newTitle.equals(updatedBook.getTitolo())) {
				// VERIFICA CHE IL NUOVO TITOLO NON SIA UGUALE AD UN ALTRO TITOLO
				if (bookRepository.existsByTitolo(newTitle)) {
					throw new BadRequestException("Questo titolo è già in uso da un altro libro");
				}
				updatedBook.setTitolo(newTitle);
			}

			// AGGIORNA L'EDITORE (VERIFICA CHE ESISTA IN DB)
			String editore = body.getEditore();
			Publisher publisher = publisherRepository.findByEditore(editore)
					.orElseThrow(() -> new NotFoundException("Editore non trovato: " + editore));
			updatedBook.setEditore(publisher);

			System.out.println("Libro modificato");
			return bookRepository.save(updatedBook);

		} else {
			throw new NotFoundException("Il libro con id " + idLibro + " non esiste!");
		}

	}

	// METODO PER CANCELLARE IL LIBRO (RICERCA PER ID ED ELIMINAZIONE) -> ADMIN
	public void findByIdAndDelete(UUID idLibro) {
		if (bookRepository.existsById(idLibro)) {
			System.out.println("Libro cancellato");
			bookRepository.deleteById(idLibro);
		} else {
			throw new NotFoundException("Il libro con id " + idLibro + " non esiste!");
		}

	}

	// METODO PER VISUALIZZARE LA LISTA DI TUTTI I LIBRI IN DB
	public List<Book> getBooks() {
		return bookRepository.findAll();

	}

	// METODO PER VISUALIZZARE UN LIBRO SPECIFICO
	public Book findById(UUID idLibro) {
		Optional<Book> book = bookRepository.findById(idLibro);
		if (book.isPresent()) {
			return book.get();
		} else {
			throw new NotFoundException("Il libro selezionato non è disponibile");
		}

	}

	// METODO PER OTTENERE LA LIBRERIA PERSONALE DELL'UTENTE LOGGATO
	public Set<Book> getLibrary() {
		User currentUser = userService.getCurrentUser();
		return currentUser.getLibri();

	}

	// METODO PER AGGIUNGERE UN LIBRO ALLA LIBRERIA PERSONALE
	public void addBookToMyLibrary(UUID idLibro) {
		User currentUser = userService.getCurrentUser();
		Optional<Book> bookToAdd = bookRepository.findById(idLibro);

		if (bookToAdd.isPresent()) {
			Book book = bookToAdd.get();
			if (!currentUser.getLibri().contains(book)) {
				currentUser.getLibri().add(book);
				userRepository.save(currentUser);
				bookRepository.save(book);
			} else {
				throw new BadRequestException("Il libro con ID " + idLibro + " è già presente nella tua libreria!");
			}
		} else {
			throw new NotFoundException("Il libro con ID " + idLibro + " non esiste!");
		}

	}

	// METODO PER RIMUOVERE UN LIBRO DALLA LIBRERIA PERSONALE
	public void removeBookFromMyLibrary(UUID idLibro) {
		User currentUser = userService.getCurrentUser();
		Optional<Book> bookToRemove = bookRepository.findById(idLibro);
		if (bookToRemove.isPresent()) {

			currentUser.getLibri().remove(bookToRemove.get());
			userRepository.save(currentUser);
		} else {
			throw new NotFoundException("Il libro con ID " + idLibro + " non esiste!");
		}

	}

}
