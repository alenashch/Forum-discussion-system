package nl.tudelft.sem.group20.authenticationserver.controllers;

import nl.tudelft.sem.group20.authenticationserver.embeddable.LoginRequest;
import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import nl.tudelft.sem.group20.authenticationserver.entities.AuthToken;
import nl.tudelft.sem.group20.authenticationserver.services.UserService;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private transient UserService userService;

    /**
     * Create user request.
     *
     * @param registerRequest User to be created.
     * @return JSON file containing the ID of a new user.
     */
    @PostMapping("/create")
    @ResponseBody
    public StatusResponse createUser(@RequestBody RegisterRequest registerRequest) {

        //return Collections.singletonMap("ID", userService.createUser(registerRequest));
        return userService.createUser(registerRequest);
    }

    /**
     * Logs a user in.
     *
     * @param login the request to process
     * @return Token response of status
     */
    @PostMapping("/login")
    @ResponseBody
    public AuthToken login(@RequestBody LoginRequest login) {
        return userService.login(login.getEmail(), login.getPassword());
    }

    /**
     * Logs a user out.
     *
     * @param authRequest - the token to delete from the database.
     * @return - StatusResponse telling if it was a success or not.
     */
    @PostMapping("/logout")
    @ResponseBody
    public StatusResponse logout(@RequestBody AuthRequest authRequest) {
        return userService.logout(authRequest.getToken());
    }

    /**
     * Logs a user in.
     *
     * @param authRequest to process
     * @return Token response of status
     */
    @PostMapping("/authenticate")
    @ResponseBody
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return userService.authenticate(authRequest.getToken());
    }

}
