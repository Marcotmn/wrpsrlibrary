package tumminia.wrpsrlibrary.publishers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tumminia.wrpsrlibrary.users.UserService;

@RestController
@RequestMapping("/editori")
public class PublisherController {

	@Autowired
	PublisherService publisherService;
	@Autowired
	UserService userService;

	@PostMapping("/upload-editore")
	@ResponseStatus(HttpStatus.CREATED)
	public Publisher registerPublisher(@RequestBody NewPublisherPayload payload) {
		Publisher created = publisherService.save(payload);
		return created;
	}

	@GetMapping("/elenco-editori")
	public List<Publisher> getPublishers() {
		return publisherService.getPublishers();

	}

}
