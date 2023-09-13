package lt.rimuok.security.services;

import lombok.RequiredArgsConstructor;
import lt.rimuok.security.entities.Token;
import lt.rimuok.security.entities.User;
import lt.rimuok.security.repositories.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling operations related to tokens.
 * @version 1.0, 15 Aug 2023
 * @since 1.0, 3 Aug 2023
 * @author Maksim Pavlenko
 */

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository repo;
    {
        System.out.println("Привет! ąčęėįšųū");
    }
    /**
     * Retrieves the user associated with the given token.
     *
     * @param token The token for which to retrieve the associated user.
     * @since 1.0, 3 Aug 2023
     * @return An optional containing the user associated with the token, or an empty optional if not found.
     */

    public Optional<User> getUserByToken(String token) {
        return repo.getUserByToken(token);
    }

    /**
     * Finds a token by its value.
     *
     * @param token The token value to search for.
     * @since 1.0, 3 Aug 2023
     * @return An optional containing the found token, or an empty optional if not found.
     */

    public Optional<Token> findByToken(String token){ return repo.findByToken(token); }

    /**
     * Saves a token in the repository.
     *
     * @param storedToken The token to be saved.
     * @since 1.0, 3 Aug 2023
     * @return The saved token.
     */

    public Token save(Token storedToken) { return repo.save(storedToken); }

    /**
     * Saves a list of tokens in the repository.
     *
     * @param tokens The list of tokens to be saved.
     * @since 1.0, 3 Aug 2023
     * @return The list of saved tokens.
     */

    public List<Token> saveAll(List<Token> tokens) { return repo.saveAll(tokens); }

    /**
     * Finds all valid tokens associated with a specific user.
     *
     * @param id The user ID for which to retrieve valid tokens.
     * @since 1.0, 3 Aug 2023
     * @return The list of valid tokens associated with the user.
     */

    public List<Token> findAllValidTokenByUser(String id) {return repo.findAllValidTokenByUser(id); }
}
