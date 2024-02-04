package tumminia.wrpsrlibrary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tumminia.wrpsrlibrary.exceptions.UnauthorizedException;
import tumminia.wrpsrlibrary.users.LoginSuccessfullPayload;
import tumminia.wrpsrlibrary.users.NewUserPayload;
import tumminia.wrpsrlibrary.users.Role;
import tumminia.wrpsrlibrary.users.User;
import tumminia.wrpsrlibrary.users.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	JWTTools jwtTools;

	@Autowired
	PasswordEncoder bcrypt;

	// ROTTE E METODI DI REGISTRAZIONE, LOGIN E LOGOUT

	@PostMapping("/register-user")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody NewUserPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		User created = userService.save(body, Role.USER);
		return created;
	}

	// ROTTE E METODI DI REGISTRAZIONE, LOGIN E LOGOUT
	@PostMapping("/register-admin")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveAdmin(@RequestBody NewUserPayload body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		User created = userService.save(body, Role.ADMIN);
		return created;
	}

	@PostMapping("/login")
	public LoginSuccessfullPayload login(@RequestBody NewUserPayload body) {
		User user = userService.findByEmail(body.getEmail());
		if (bcrypt.matches(body.getPassword(), user.getPassword())) {
			String token = jwtTools.createToken(user);
			return new LoginSuccessfullPayload(token);

		} else {
			throw new UnauthorizedException("Credenziali non valide!");
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		System.out.println("Logout effettuato con successo");
		return ResponseEntity.ok("Logout effettuato con successo");

	}
}
