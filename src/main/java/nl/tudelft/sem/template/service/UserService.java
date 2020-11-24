package nl.tudelft.sem.template.service;

import java.util.List;
import nl.tudelft.sem.template.model.User;
import nl.tudelft.sem.template.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final transient UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a User and adds it to the database.
     *
     * @param newUser - the User to be added.
     * @return -1 if the User already exists in the database or the id of the newly created user
     *      if creation was successful.
     */
    public long createUser(User newUser) {
        if (userRepository.getById(newUser.getId()).isPresent()) {
            return -1;
        }
        userRepository.saveAndFlush(newUser);
        return newUser.getId();
    }

    /**
     * Updates a User in the database.
     *
     * @param toUpdate - the user to be updated.
     * @return false if the User does not exist in the database, and true otherwise.
     */
    public boolean updateUser(User toUpdate) {
        if (userRepository.getById(toUpdate.getId()).isEmpty()) {
            return false;
        }

        userRepository.saveAndFlush(toUpdate);
        return true;
    }


}
