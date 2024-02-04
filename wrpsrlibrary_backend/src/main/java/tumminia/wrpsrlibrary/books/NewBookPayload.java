package tumminia.wrpsrlibrary.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NewBookPayload {
	private String titolo;
	private String editore;

}
