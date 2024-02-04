package tumminia.wrpsrlibrary.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class NewUserPayload {
	private Role ruolo;
	private String nome;
	private String cognome;
	private String username;
	private String email;
	private String password;

}
