package tumminia.wrpsrlibrary.books;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libreria-personale")
public class LibraryController {

	@Autowired
	BookService bookService;

	// ENDPOINT PER VISUALIZZARE LA LIBRERIA PERSONALE
	@GetMapping("/elenco")
	@ResponseStatus(HttpStatus.OK)
	public Set<Book> getMyLibrary() {
		Set<Book> userLibrary = bookService.getLibrary();
		return userLibrary;
	}

	@PostMapping("/aggiungi/{idLibro}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> addBookToMyLibrary(@PathVariable UUID idLibro) {
		bookService.addBookToMyLibrary(idLibro);
		return new ResponseEntity<>(Map.of("message", "Il libro è stato aggiunto alla tua lista"), HttpStatus.CREATED);
	}

	@DeleteMapping("/cancella/{idLibro}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> removeBookFromMyLibrary(@PathVariable UUID idLibro) {
		bookService.removeBookFromMyLibrary(idLibro);
		return new ResponseEntity<>(Map.of("message", "Il libro è stato rimosso correttamente"), HttpStatus.OK);
	}

}
