package tumminia.wrpsrlibrary.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	User findByEmailAndUsername(String email, String username);

	Optional<User> findByNome(String nome);

}
