package nl.tudelft.sem.group20.authenticationserver.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.tudelft.sem.group20.authenticationserver.embeddable.AuthRequest;
import nl.tudelft.sem.group20.authenticationserver.entities.AuthToken;
import nl.tudelft.sem.group20.authenticationserver.embeddable.LoginRequest;
import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import nl.tudelft.sem.group20.authenticationserver.embeddable.StatusResponse;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import nl.tudelft.sem.group20.authenticationserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
     * Logs a user in.
     *
     * @param authRequest to process
     * @return Token response of status
     */
    @PostMapping("/authenticate")
    @ResponseBody
    public StatusResponse authenticate(@RequestBody AuthRequest authRequest) {
        return userService.authenticate(authRequest.getToken());
    }

    /**
     * Get posts request.
     *
     * @return JSON containing list of all users.
     */
    @GetMapping("/get")
    @ResponseBody
    public List<User> getUsers() {

        return userService.getUsers();
    }

    /**
     * Edit user request.
     *
     * @param user - User to be edited. With the old ID and new parameters to be set.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Boolean> editUser(@RequestBody User user) {

        return Collections.singletonMap("success", userService.updateUser(user));
    }

    /**
     * Test Request.
     *
     * @return response
     */
    @RequestMapping("/hello")
    public @ResponseBody String getAllThreads() {
        return "hello";
    }
}
