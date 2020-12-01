package nl.tudelft.sem.group20.authenticationserver.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.authenticationserver.embeddable.AuthToken;
import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import nl.tudelft.sem.group20.authenticationserver.embeddable.StatusResponse;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import nl.tudelft.sem.group20.authenticationserver.repos.AuthTokenRepository;
import nl.tudelft.sem.group20.authenticationserver.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static nl.tudelft.sem.group20.authenticationserver.embeddable.StatusResponse.Status.fail;
import static nl.tudelft.sem.group20.authenticationserver.embeddable.StatusResponse.Status.success;

@Service
public class UserService {

    private transient UserRepository userRepository;
    private transient AuthTokenRepository authTokenRepository;

    /**
     * Creates UserService.
     *
     * @param userRepository user repository
     * @param tokenRepository token repository
     */
    public UserService(UserRepository userRepository, AuthTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.authTokenRepository = tokenRepository;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates a User and adds it to the database.
     *
     * @param newRegisterRequest - the User to be added.
     * @return statusresponse if the User already exists in the database or the id of the newly created user.
     *      if creation was successful.
     */
    public StatusResponse createUser(RegisterRequest newRegisterRequest) {
        //is the email address already in the database?
        if (userRepository.getByEmail(newRegisterRequest.getEmail()).isPresent()) {
            return new StatusResponse(fail, "Email address already exists");
        }
        //is the username already in the database?
        else if(userRepository.getByUsername(newRegisterRequest.getUsername()).isPresent()){
            return new StatusResponse(fail, "username already exists");
        }
        //Create a new user with the given name, password and email. Hash the password and set the type to student (false).
        User newUser = new User(newRegisterRequest.getUsername(), getMd5(newRegisterRequest.getPassword()), newRegisterRequest.getEmail(), false);
        userRepository.saveAndFlush(newUser);
        return new StatusResponse(success, "A new user was succesfully made");
    }

    /**
     * Logs the user in.
     *
     * @param email email to check
     * @param password password to check
     * @return AuthToken with fail or success and token information
     */
    public AuthToken login(String email, String password) {
        Optional<User> userOptional = userRepository.getByEmail(email);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (user.getPassword().equals(getMd5(password))) {
                while (true) {
                    String token = getRandomToken(20);
                    Optional<AuthToken> optionalAuthToken = authTokenRepository.findByToken(token);
                    if (optionalAuthToken.isEmpty()) {
                        AuthToken loginToken =
                                new AuthToken(token);
                        authTokenRepository.save(loginToken);

                        return loginToken;
                    }
                }
            }
        }
        return new AuthToken();
    }

    /**
     * Updates a User in the database.
     *
     * @param toUpdate - the user to be updated.
     * @return false if the User does not exist in the database, and true otherwise.
     */
    public boolean updateUser(User toUpdate) {
        if (userRepository.getByEmail(toUpdate.getEmail()).isEmpty()) {
            return false;
        }

        userRepository.saveAndFlush(toUpdate);
        return true;
    }

<<<<<<< authenticationserver/src/main/java/nl/tudelft/sem/group20/authenticationserver/services/UserService.java
    private String getRandomToken(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = (int) (48 + Math.round(Math.random() * 74));
            stringBuffer.append((char) num);
        }
        return stringBuffer.toString();
    }

    /**
     * Gets md5 of string.
     *
     * @param input string to get md5 from
     * @return hashed string
     */
    public static String getMd5(String input) {
=======
    /**
     * Hashes the input.
     *
     * @param input - String to be hashed.
     * @return - the hashed string.
     */
    public static String getMd5(String input)
    {
>>>>>>> authenticationserver/src/main/java/nl/tudelft/sem/group20/authenticationserver/services/UserService.java
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
<<<<<<< authenticationserver/src/main/java/nl/tudelft/sem/group20/authenticationserver/services/UserService.java
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

=======
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
>>>>>>> authenticationserver/src/main/java/nl/tudelft/sem/group20/authenticationserver/services/UserService.java

}
