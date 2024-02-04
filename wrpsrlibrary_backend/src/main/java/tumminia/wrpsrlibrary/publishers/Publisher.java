package tumminia.wrpsrlibrary.publishers;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "editori")
@Data
@NoArgsConstructor
public class Publisher {

	@Id
	@GeneratedValue
	private UUID idEditore;

	@Column(length = 60)
	private String editore;

	public Publisher(String editore) {
		this.editore = editore;
	}

	@Override
	public String toString() {
		return "Publisher{" + "idEditore=" + idEditore + ", editore='" + editore + '\'' + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEditore, editore);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Publisher publisher = (Publisher) o;
		return Objects.equals(idEditore, publisher.idEditore);
	}
}