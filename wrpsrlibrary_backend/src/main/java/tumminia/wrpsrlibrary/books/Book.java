package tumminia.wrpsrlibrary.books;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tumminia.wrpsrlibrary.publishers.Publisher;

@Entity
@Table(name = "libri")
@Data
@NoArgsConstructor

public class Book {

	@Id
	@GeneratedValue
	private UUID idLibro;
	@Column(length = 60)
	private String titolo;

	@ManyToOne()
	@JoinColumn(name = "id_editore")
	private Publisher editore;

	public Book(String titolo, Publisher editore) {
		this.titolo = titolo;
		this.editore = editore;
	}

	@Override
	public String toString() {
		return "Book{" + "idLibro=" + idLibro + ", titolo='" + titolo + '\'' + ", editore=" + editore + '}';

	}

	@Override
	public int hashCode() {
		return Objects.hash(idLibro, titolo, editore);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Book book = (Book) o;
		return Objects.equals(idLibro, book.idLibro);
	}
}