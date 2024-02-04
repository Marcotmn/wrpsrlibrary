package tumminia.wrpsrlibrary.books;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tumminia.wrpsrlibrary.users.UserService;

@RestController
@RequestMapping("/admin")
public class AdminBookController {

	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;

	@PostMapping("/upload-libro")
	public ResponseEntity<Book> registerBook(@RequestBody NewBookPayload payload) {
		Book savedBook = bookService.save(payload);
		return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
	}

	@DeleteMapping("/cancella-libro/{idLibro}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> deleteBook(@PathVariable UUID idLibro) {
		bookService.findByIdAndDelete(idLibro);
		return new ResponseEntity<String>("Il Libro è stato eliminato con successo!", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/modifica-libro/{idLibro}")
	public ResponseEntity<Book> updateBook(@PathVariable UUID idLibro, @RequestBody NewBookPayload body) {
		Book updatedBook = bookService.findByIdAndUpdate(idLibro, body);
		return new ResponseEntity<>(updatedBook, HttpStatus.CREATED);

	}

}
