package nl.tudelft.sem.template.service;

import nl.tudelft.sem.template.model.User;
import nl.tudelft.sem.template.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final transient UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
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
        if (userRepository.getById(newUser.getId()).isPresent()){
            return -1;
        }
        userRepository.saveAndFlush(newUser);
        return newUser.getId();
    }

}
