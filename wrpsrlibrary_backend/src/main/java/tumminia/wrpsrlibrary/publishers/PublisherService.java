package tumminia.wrpsrlibrary.publishers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tumminia.wrpsrlibrary.exceptions.BadRequestException;
import tumminia.wrpsrlibrary.exceptions.NotFoundException;
import tumminia.wrpsrlibrary.users.UserService;

@Service
public class PublisherService {

	@Autowired
	PublisherRepository publisherRepository;

	@Autowired
	UserService userService;

	// METODO PER SALVARE I PUBLISHER
	public Publisher save(NewPublisherPayload body) {
		publisherRepository.findByEditore(body.getEditore()).ifPresent(publisher -> {
			throw new BadRequestException("L'editore è già stato registrato");
		});
		Publisher newPublisher = new Publisher(body.getEditore());
		return publisherRepository.save(newPublisher);

	}

	public Publisher findPublisherByName(String editore) {
		return publisherRepository.findByEditore(editore)
				.orElseThrow(() -> new NotFoundException("Editore non trovato con il nome: " + editore));

	}

	// METODO GET TUTTI I PUBLISHER
	public List<Publisher> getPublishers() {
		return publisherRepository.findAll();

	}

}
