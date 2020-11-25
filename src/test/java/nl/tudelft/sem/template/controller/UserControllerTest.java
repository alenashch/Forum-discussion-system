package nl.tudelft.sem.template.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import nl.tudelft.sem.template.model.User;
import nl.tudelft.sem.template.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    @MockBean
    private transient UserService userService;
    @Autowired
    private transient MockMvc mockMvc;

    @Test
    void createUserTest() {

        User user = constructDefaultUser();
        when(userService.createUser(user)).thenReturn(1L);

        try {
            mockMvc.perform(post("/user/create")
                .contentType(APPLICATION_JSON)
                .content(createJsonRequest(user)))
                .andDo(print())
                .andExpect((ResultMatcher) jsonPath("$.ID").value(1));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Test
    void getUsersTest() {

        User user = constructDefaultUser();
        List<User> list = List.of(user);

        when(userService.getUsers()).thenReturn(list);

        try {

            mockMvc.perform(get("/user/get")
                .contentType(APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].username").value("Bob"))
                .andExpect((ResultMatcher) jsonPath("$[0].password").value("123"));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void editUsersTest() {

        User user = constructDefaultUser();

        given(userService.updateUser(any(User.class))).willReturn(true);
        try {

            mockMvc.perform(post("/user/edit")
                .contentType(APPLICATION_JSON)
                .content(createJsonRequest(user)))
                .andExpect((ResultMatcher) jsonPath("$.success").value(true));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private String createJsonRequest(User user) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(user);
    }

    private User constructDefaultUser() {

        return new User(1, "Bob", "123", "bob@gmail.com", false);
    }
}