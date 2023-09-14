package lt.rimuok.security.repositories;

import lt.rimuok.security.entities.User;
import lt.rimuok.security.services.UserService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link User} entity
 * @see UserService
 * @version 1.0, 15 Aug 2023
 * @since 1.0, 3 Aug 2023
 * @author Maksim Pavlenko
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findById(String id);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	@Query(value = "{}", fields = "{'username': 1}")
	List<String> findAllUsernames();

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
}
