package lt.rimuok.security.repositories;

import lt.rimuok.security.entities.Token;
import lt.rimuok.security.entities.User;
import lt.rimuok.security.services.TokenService;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Token} entity
 * @see TokenService
 * @version 1.0, 15 Aug 2023
 * @since 1.0, 3 Aug 2023
 * @author Maksim Pavlenko
 */

public interface TokenRepository extends MongoRepository<Token, String> {
  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(String id);

  Optional<Token> findByToken(String token);

  @Query("SELECT t.user FROM Token t JOIN t.user u WHERE t.token = :token")
  Optional<User> getUserByToken(String token);
}