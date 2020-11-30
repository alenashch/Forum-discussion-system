package nl.tudelft.sem.group20.authenticationserver.test;

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
import nl.tudelft.sem.group20.authenticationserver.controllers.UserController;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import nl.tudelft.sem.group20.authenticationserver.repos.UserRepository;
import nl.tudelft.sem.group20.authenticationserver.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = UserService.class)
@AutoConfigureMockMvc
@WebMvcTest(UserService.class)
@ContextConfiguration(classes = AuthenticationServer.class)
public class UserServiceTest {
    transient User user1;
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

    private transient UserService userService;
    transient List<User> users;

    @MockBean
    private transient UserRepository userRepository;

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

        user1 = new User(id1, username1, password1, email1, type1);
        user2 = new User(id2, username2, password2, email2, type2);
        user3 = new User(id3, username3, password3, email3, type3);

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        //users.add(user3);

        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAll())
                .thenReturn(users);
        Mockito.when(userRepository.saveAndFlush(any(User.class)))
                .then(returnsFirstArg());
        Mockito.when(userRepository.getById(2))
                .thenReturn(Optional.of(user2));


        userService = new UserService(userRepository);
    }

    @Test
    void testGetUsers() {
        assertThat(userService.getUsers()).hasSize(users.size()).hasSameElementsAs(users);
    }

    @Test
    void testCreateUserSuccessful() {
        Mockito.when(userRepository.saveAndFlush(any(User.class)))
                .then(returnsFirstArg());
        assertEquals(userService.createUser(user3), user3.getId());

        verify(userRepository, times(1)).saveAndFlush(user3);
    }

    @Test
    void testCreateUserUnsuccessful() {
        assertEquals(-1, userService.createUser(user2));

        verify(userRepository, times(0)).saveAndFlush(any(User.class));
    }

    @Test
    void updateUserSuccessful() {
        user2.setUsername("Pippin");

        assertTrue(userService.updateUser(user2));
        verify(userRepository, times(1)).saveAndFlush(user2);
    }

    @Test
    void updateUserUnsuccessful() {
        assertFalse(userService.updateUser(user3));
        verify(userRepository, times(0)).saveAndFlush(any(User.class));
    }




}
