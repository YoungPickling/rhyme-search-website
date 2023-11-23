package lt.rimuok.security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lt.rimuok.security.dto_response.AbstractResponse;
import lt.rimuok.security.entities.Token;
import lt.rimuok.security.entities.User;
import lt.rimuok.security.services.HttpResponseService;
import lt.rimuok.security.services.TokenService;
import lt.rimuok.security.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {
    private final TokenService tokenService;
    private final HttpResponseService responseService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Labas!";
    }

    @GetMapping("/role")
    public ResponseEntity<AbstractResponse> checkRole(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (responseService.isBearer(authHeader))
            return responseService.error(HttpStatus.UNAUTHORIZED,"Bad credentials");

        String jwt = authHeader.substring(7);
        Token token = tokenService.findByToken(jwt).orElse(null);
        if (token == null)
            return responseService.error(HttpStatus.UNAUTHORIZED,"Token not found");

        User user = tokenService.getUserByToken(jwt).orElse(null);
        if (user == null)
            return responseService.error(HttpStatus.UNAUTHORIZED,"Bad credentials");

        String role = user.getRole().name();
        return responseService.msg(role) ;
    }

    private final UserService userService;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id).get();
    }

    // TODO: we need to finnish user CRUD operation endpoints
//    @PutMapping("/users/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User user) {
//        return userService.updateUser(id, user);
//    }
//
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        boolean res = userService.deleteUser(id);
//    }

}

