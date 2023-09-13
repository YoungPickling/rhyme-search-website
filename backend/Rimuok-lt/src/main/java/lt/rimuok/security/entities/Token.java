package lt.rimuok.security.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>&emsp;Entity class representing a security token
 * associated with a {@link User}. This class encapsulates information
 * about the token, including its unique identifier - {@link #id},
 * the associated {@link #user}, the {@link #token}'s value, type,
 * expiration status, and revocation status.</p>
 *
 * <p>&emsp;Tokens are used for authentication and
 * authorization purposes within the application.</p>
 *
 * @version 1.0, 15 Aug 2023
 * @since 1.0, 3 Aug 2023
 * @author Maksim Pavlenko
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tokens")
public class Token {
  //The unique identifier for the token.
  @Id
  public String id;

  //The value of the token.
  public String token;

  //The type of the token.
  public TokenType tokenType = TokenType.BEARER;

  //Indicates whether the token has been revoked.
  public boolean revoked;

  //Indicates whether the token has expired.
  public boolean expired;

  //The user associated with the token.
  @DBRef
  public User user;
}