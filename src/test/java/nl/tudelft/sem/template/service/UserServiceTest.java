package nl.tudelft.sem.template.service;

import nl.tudelft.sem.template.model.User;
import nl.tudelft.sem.template.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
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

    private UserService userService;
    List<User> users;

    @MockBean
    private UserRepository userRepository;

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

        users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAll())
                .thenReturn(users);

        userService = new UserService(userRepository);
    }

    @Test
    void testGetUsers(){
        assertThat(userService.getUsers()).hasSize(users.size()).hasSameElementsAs(users);
    }


}
