package nl.tudelft.sem.group20.authenticationserver.test;

import static nl.tudelft.sem.group20.shared.StatusResponse.Status.fail;
import static nl.tudelft.sem.group20.shared.StatusResponse.Status.success;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.authenticationserver.AuthenticationServer;
import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import nl.tudelft.sem.group20.authenticationserver.entities.AuthToken;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import nl.tudelft.sem.group20.authenticationserver.repos.AuthTokenRepository;
import nl.tudelft.sem.group20.authenticationserver.repos.UserRepository;
import nl.tudelft.sem.group20.authenticationserver.services.UserService;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = UserService.class)
@AutoConfigureMockMvc
@WebMvcTest(UserService.class)
@ContextConfiguration(classes = AuthenticationServer.class)
public class UserServiceTest {
    transient User user1;
    transient RegisterRequest registerRequest1;
    transient RegisterRequest registerRequest2;
    transient long id1;
    transient String username1;
    transient String password1;
    transient String email1;
    transient boolean type1;

    transient User user2;
    transient long id2;
    transient String username2;
    transient String password2;
    transient String email2;
    transient boolean type2;

    transient User user3;
    transient long id3;
    transient String username3;
    transient String password3;
    transient String email3;
    transient boolean type3;
    transient String token1;



    private transient UserService userService;

    @MockBean
    private transient UserRepository userRepository;

    @MockBean
    private transient AuthTokenRepository tokenRepository;

    transient List<User> users;

    @BeforeEach
    void initialize() {
        id1 = 1;
        id2 = 2;
        id3 = 3;

        username1 = "Frodo";
        username2 = "Merry";
        username3 = "Samwise";

        password1 = "ring";
        password2 = "pipe";
        password3 = "friend";

        email1 = "frodo@gmail.com";
        email2 = "merry@gmail.com";
        email3 = "samwise@gmail.com";

        type1 = true;
        type2 = false;
        type3 = false;

        user1 = new User(username1, UserService.getMd5(password1), email1, type1);
        user2 = new User(username2, UserService.getMd5(password2), email2, type2);
        user3 = new User(username3, UserService.getMd5(password3), email3, type3);

        registerRequest1 = new RegisterRequest(password3, email3, username3);
        registerRequest2 = new RegisterRequest(password1, email1, username1);

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        //users.add(user3);

        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAll())
                .thenReturn(users);
        Mockito.when(userRepository.saveAndFlush(any(User.class)))
                .then(returnsFirstArg());
        Mockito.when(userRepository.getById(1))
                .thenReturn(Optional.of(user2));
        Mockito.when(userRepository.getByEmail(email1))
                .thenReturn(Optional.of(user1));
        Mockito.when(userRepository.getByUsername(username2))
                .thenReturn(Optional.of(user2));

        ArrayList<AuthToken> authTokens = new ArrayList<AuthToken>();

        token1 = "abc";

        AuthToken authToken = new AuthToken(token1, true, "abc4");
        AuthToken authToken2 = new AuthToken("abc2", true, "abc5");
        AuthToken authToken3 = new AuthToken("abc3", true, "abc6");

        authTokens.add(authToken);
        authTokens.add(authToken2);
        authTokens.add(authToken3);

        tokenRepository = Mockito.mock(AuthTokenRepository.class);
        Mockito.when(tokenRepository.findAll())
                .thenReturn(authTokens);
        Mockito.when(tokenRepository.findByToken(token1)).thenReturn(Optional.of(authToken));
        Mockito.when(tokenRepository.findByToken("abc2")).thenReturn(Optional.of(authToken2));
        Mockito.when(tokenRepository.findByToken("abc3")).thenReturn(Optional.of(authToken3));
        Mockito.when(tokenRepository.saveAndFlush(any(AuthToken.class)))
                .then(returnsFirstArg());

        Mockito.when(tokenRepository.findByToken("random7"))
                .thenReturn(Optional.of(new AuthToken("random7", false, "abc7")));

        userService = new UserService(userRepository, tokenRepository);
    }
    /*
    @Test
    void testGetUsers() {
        assertThat(userService.getUsers()).hasSize(users.size()).hasSameElementsAs(users);
    }*/

    @Test
    void testCreateUserSuccessful() {
        assertEquals(new StatusResponse(success, "A new user was succesfully made"),
                userService.createUser(registerRequest1));
    }

    @Test
    void testCreateUserUnsuccessful() {
        assertEquals(new StatusResponse(fail, "Email address already exists"),
                userService.createUser(registerRequest2));

        verify(userRepository, times(0)).saveAndFlush(any(User.class));
    }

    @Test
    void testUpdateUserSuccessful() {
        user2.setEmail("hello");
        assertEquals(success, userService.updateUser(user2, token1).getStatus());
        //verify(userRepository, times(1)).saveAndFlush(user2);
    }

    @Test
    void testUpdateUserUnsuccessful() {
        assertEquals(fail, userService.updateUser(user3, token1).getStatus());

        verify(userRepository, times(0)).saveAndFlush(any(User.class));
    }

    @Test
    void loginUserNonExisting() {
        AuthToken token = new AuthToken(id);
        assertEquals(token, userService.login("asdasd", "asdas"));
    }

    @Test
    void loginUserWrongPassword() {
        AuthToken token = new AuthToken(id);
        assertEquals(token, userService.login("frodo@gmail.com", "ring2"));
    }

    @Test
    void loginUserRight() {
        assertEquals(StatusResponse.Status.success,
                userService.login("frodo@gmail.com", "ring").getStatus());
    }

    @Test
    void testlogoutUserRightToken() {
        assertEquals(new StatusResponse(success, "Successfully logged out."),
                userService.logout("random7"));
    }

    @Test
    void tokenRight() {
        assertEquals(StatusResponse.Status.success,
                userService.authenticate(token1).getStatus());

    }

    @Test
    void tokenWrong() {
        assertEquals(StatusResponse.Status.success,
                userService.authenticate("abc2").getStatus());
    }

    @Test
    void tokenPermission() {
        assertTrue(
                userService.authenticate("abc3").isType());
    }

    @Test
    void testlogoutUserWrongToken() {
        assertEquals(new StatusResponse(fail, "You are already logged out."),
                userService.logout("horse8"));
    }

}
