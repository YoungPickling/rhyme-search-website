package lt.rimuok.security.repositories;

import lt.rimuok.security.entities.Token;
import lt.rimuok.security.entities.User;
//import lt.rimuok.security.services.TokenService;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Token} entity
 * @see TokenService
 * @version 1.0, 15 Aug 2023
 * @since 1.0, 3 Aug 2023
 * @author Maksim Pavlenko
 */

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
  List<Token> findAllByUser_IdAndExpiredFalseAndRevokedFalse(String id);

  Optional<Token> findByToken(String token);

  @Query(value = "{'token': ?0}", fields = "{'user': 1}")
  Optional<User> getUserByToken(String token);
}