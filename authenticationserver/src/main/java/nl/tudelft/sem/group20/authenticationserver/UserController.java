package nl.tudelft.sem.group20.authenticationserver;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
     * @param user User to be created.
     * @return JSON file containing the ID of a new user.
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Long> createUser(@RequestBody User user) {

        return Collections.singletonMap("ID", userService.createUser(user));
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
}
