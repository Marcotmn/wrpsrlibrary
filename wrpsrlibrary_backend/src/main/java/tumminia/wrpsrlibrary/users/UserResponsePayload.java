package tumminia.wrpsrlibrary.users;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponsePayload {

	private UUID idUtente;
	private String nome;
	private String cognome;
	private String username;
	private String email;
	private Role ruolo;

}
