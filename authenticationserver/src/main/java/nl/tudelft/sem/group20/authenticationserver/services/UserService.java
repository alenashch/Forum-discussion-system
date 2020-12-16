package nl.tudelft.sem.group20.authenticationserver.services;

import static nl.tudelft.sem.group20.shared.StatusResponse.Status.fail;
import static nl.tudelft.sem.group20.shared.StatusResponse.Status.success;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import nl.tudelft.sem.group20.authenticationserver.entities.AuthToken;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import nl.tudelft.sem.group20.authenticationserver.repos.AuthTokenRepository;
import nl.tudelft.sem.group20.authenticationserver.repos.UserRepository;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.stereotype.Service;

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


    /*public List<User> getUsers() {
        return userRepository.findAll();
    }*/

    /**
     * Creates a User and adds it to the database.
     *
     * @param newRegisterRequest - the User to be added.
     * @return statusresponse telling if it was successful or not.
     */
    public StatusResponse createUser(RegisterRequest newRegisterRequest) {
        //is the email address already in the database?
        if (userRepository.getByEmail(newRegisterRequest.getEmail()).isPresent()) {
            return new StatusResponse(fail, "Email address already exists");
        } else if (userRepository.getByUsername(newRegisterRequest.getUsername()).isPresent()) {
            //is the username already in the database?
            return new StatusResponse(fail, "username already exists");
        }
        //Create a new user with the given name, password and email.
        // Hash the password and set the type to student (false).
        User newUser = new User(newRegisterRequest.getUsername(), getMd5(newRegisterRequest
                .getPassword()), newRegisterRequest.getEmail(), newRegisterRequest.getType());
        userRepository.saveAndFlush(newUser);
        return new StatusResponse(success, "A new user was successfully made");
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
                        AuthToken loginToken = new AuthToken(token,
                                user.isType(), user.getUsername());
                        authTokenRepository.save(loginToken);

                        return loginToken;
                    }
                }
            }
        }
        return new AuthToken();
    }

    /**
     * Logs out the user by deleting their token from the database.
     *
     * @param token - Token to be deleted.
     * @return - statusresponse telling if it was succesfull or not.
     */
    public StatusResponse logout(String token) {
        Optional<AuthToken> optionalAuthToken = authTokenRepository.findByToken(token);
        if (optionalAuthToken.isPresent()) {
            authTokenRepository.delete(optionalAuthToken.get());
            return new StatusResponse(success, "Successfully logged out.");
        }
        return new StatusResponse(fail, "You are already logged out.");
    }

    /**
     * Checks if token is valid.
     *
     * @param tokenStr to check
     * @return StatusResponse with fail or success
     */
    public AuthResponse authenticate(String tokenStr) {
        Optional<AuthToken> tokenOptional = authTokenRepository.findByToken(tokenStr);
        if (tokenOptional.isPresent()) {
            AuthToken token = tokenOptional.get();

            return new AuthResponse(token.isType(), token.getUsername());
        }
        return new  AuthResponse();
    }

    /**
     * Updates a User in the database.
     *
     * @param toUpdate - the user to be updated.
     * @return StatusResponse with response
     */
    public StatusResponse updateUser(User toUpdate, String token) {
        AuthResponse authResponse = authenticate(token);
        if (authResponse.getStatus().equals(success) && authResponse.isType()) {
            if (userRepository.getByUsername(toUpdate.getUsername()).isEmpty()) {
                return new StatusResponse(fail, "Does not exist");
            }
            userRepository.saveAndFlush(toUpdate);
            return new StatusResponse(success, "Success!");
        }
        return new StatusResponse(fail, "Not allowed");
    }

    private String getRandomToken(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = (int) (48 + Math.round(Math.random() * 74));
            stringBuffer.append((char) num);
        }
        return stringBuffer.toString();
    }

    /**
     * Hashes the input.
     *
     * @param input - String to be hashed.
     * @return - the hashed string.
     */
    public static String getMd5(String input) {
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
