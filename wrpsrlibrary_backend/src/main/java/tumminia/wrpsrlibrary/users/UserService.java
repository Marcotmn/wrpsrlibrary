package tumminia.wrpsrlibrary.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tumminia.wrpsrlibrary.exceptions.BadRequestException;
import tumminia.wrpsrlibrary.exceptions.NotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	// METODO CHE VERIFICA L'UTENTE AUTENTICATO
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		if (principal instanceof User) {
			return (User) principal;
		} else {
			throw new IllegalStateException("Si è verificato un problema! Per favore riprova");
		}

	}

	// METODO SAVE PER USER (RUOLO PASSATO COME PARAMETRO PER DIFFERENZIARE ENDPOINT
	// USER/ADMIN)
	public User save(NewUserPayload body, Role ruolo) {
		userRepository.findByEmail(body.getEmail()).ifPresent(utente -> {
			throw new BadRequestException("L'email " + body.getEmail() + " è gia stata utilizzata");
		});
		User newUser = new User(ruolo, body.getNome(), body.getCognome(), body.getUsername(), body.getEmail(),
				body.getPassword());
		return userRepository.save(newUser);

	}

	// FIND BY EMAIL
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));

	}

	// FIND BY ID
	public User findById(UUID idUtente) {
		Optional<User> utente = userRepository.findById(idUtente);
		if (utente.isPresent()) {
			return utente.get();
		} else {
			throw new NotFoundException("Utente non trovato con ID: " + idUtente);
		}

	}

}