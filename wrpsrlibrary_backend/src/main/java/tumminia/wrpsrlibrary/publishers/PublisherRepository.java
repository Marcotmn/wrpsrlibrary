package tumminia.wrpsrlibrary.publishers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
	Optional<Publisher> findByEditore(String editore);

}
