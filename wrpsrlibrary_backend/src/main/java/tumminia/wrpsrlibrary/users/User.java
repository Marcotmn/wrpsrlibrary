package tumminia.wrpsrlibrary.users;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import tumminia.wrpsrlibrary.books.Book;

@SuppressWarnings("serial")
@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "password" })
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private UUID idUtente;

	@Enumerated(EnumType.STRING)
	private Role ruolo;

	@Column(length = 60)
	private String nome;
	@Column(length = 60)
	private String cognome;
	@Column(length = 60)
	private String username;
	@Convert(converter = EmailConverter.class)
	private String email;
	@Column(length = 60)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Libreria_utente", joinColumns = @JoinColumn(name = "idUtente"), inverseJoinColumns = @JoinColumn(name = "idLibro"))
	private Set<Book> libri = new HashSet<>();

	public User(Role ruolo, String nome, String cognome, String username, String email, String password) {
		this.ruolo = ruolo;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(ruolo.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User{" + "idUtente=" + idUtente + ", ruolo=" + ruolo + ", nome='" + nome + '\'' + ", cognome='"
				+ cognome + '\'' + ", username='" + username + '\'' + ", email='" + email + '\'' + ", password='"
				+ password + '\'' + ", libri=" + libri + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUtente, ruolo, nome, cognome, username, email, password);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(idUtente, user.idUtente);
	}
}