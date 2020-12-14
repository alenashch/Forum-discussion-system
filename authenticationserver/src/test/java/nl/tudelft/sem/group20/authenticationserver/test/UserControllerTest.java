package nl.tudelft.sem.group20.authenticationserver.test;

import static nl.tudelft.sem.group20.shared.StatusResponse.Status.success;
import static org.mockito.ArgumentMatchers.any;
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
import nl.tudelft.sem.group20.authenticationserver.AuthenticationServer;
import nl.tudelft.sem.group20.authenticationserver.controllers.UserController;
import nl.tudelft.sem.group20.authenticationserver.embeddable.RegisterRequest;
import nl.tudelft.sem.group20.authenticationserver.entities.AuthToken;
import nl.tudelft.sem.group20.authenticationserver.entities.User;
import nl.tudelft.sem.group20.authenticationserver.services.UserService;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

//@AutoConfigureMockMvc
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AuthenticationServer.class)
//@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = AuthenticationServer.class)
class UserControllerTest {
    @Autowired
    @MockBean
    private transient UserService userService;
    @Autowired
    private transient MockMvc mockMvc;



    @Test
    void createUserTest() {
        RegisterRequest registerRequest = new RegisterRequest("pwd", "test@gmal.com", "test");
        User user = constructDefaultUser();
        when(userService.createUser(registerRequest));
        //.thenReturn(new StatusResponse(success, "A new user was succesfully made"));

        try {
            mockMvc.perform(post("/user/create")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(user)))
                    .andDo(print())
                    .andExpect(
                            jsonPath("$.ID")
                                    .value(1));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Test
    void loginUserTest() {
        when(userService.login("test", "test1"))
                .thenReturn(new AuthToken("abc", false, "abc2"));

        JSONObject data = new JSONObject();

        try {
            data.put("email", "test");
            data.put("password", "test1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/user/login")
                    .contentType(APPLICATION_JSON)
                    .content(data.toString()))
                    .andDo(print())
                    .andExpect(
                            jsonPath("$.token")
                                    .value("abc"))
                    .andExpect(
                            jsonPath("$.type")
                                    .value(false))
                    .andExpect(
                            jsonPath("$.username")
                                    .value("abc2"));
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Test
    void authenticateTest() {
        when(userService.authenticate("abc1"))
                .thenReturn(new AuthResponse(false, "abc2"));

        JSONObject data = new JSONObject();

        try {
            data.put("token", "abc1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/user/authenticate")
                    .contentType(APPLICATION_JSON)
                    .content(data.toString()))
                    .andDo(print())
                    .andExpect(
                            jsonPath("$.status")
                                    .value("success"))
                    .andExpect(
                            jsonPath("$.type")
                                    .value(false));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }


    /*@Test
    void getUsersTest() {

        User user = constructDefaultUser();
        List<User> list = List.of(user);

        when(userService.getUsers()).thenReturn(list);

        try {

            mockMvc.perform(get("/user/get")
                .contentType(APPLICATION_JSON))
                .andDo(print())
                    .andExpect((ResultMatcher) jsonPath("$[0].username").value("Bob"))
                    .andExpect((ResultMatcher) jsonPath("$[0].password").value("123"));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }*/

    /*@Test
    void editUsersTest() {
        User user = new User("Bob", "123", "bob@gmail.com", false);
        AuthRequest authRequest = new AuthRequest("token1");
        when(userService.updateUser(user,authRequest))
                .thenReturn(new StatusResponse(success,"reason"));

        JSONObject data = new JSONObject();

        JSONObject userJS = new JSONObject();

        JSONObject tokenJS = new JSONObject();

        try {
            userJS.put("username", "Bob");
            userJS.put("password", "123");
            userJS.put("email", "bob@gmail.com");
            userJS.put("type", false);

            tokenJS.put("token","token1");

            data.put("user", user.toString());
            data.put("token", tokenJS.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/user/edit")
                    .contentType(APPLICATION_JSON)
                    .content(data.toString()))
                    .andDo(print())
                    .andExpect(
                            jsonPath("$.status")
                                    .value("success"));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }*/

    @Test
    void logoutUserTest() {
        when(userService.logout("random7"))
                .thenReturn(new StatusResponse(success, "Successfully logged out."));

        JSONObject data = new JSONObject();

        try {
            data.put("token", "random7");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/user/logout")
                    .contentType(APPLICATION_JSON)
                    .content(data.toString()))
                    .andDo(print())
                    .andExpect(
                            jsonPath("$.status")
                                    .value("success"))
                    .andExpect(
                            jsonPath("$.message")
                                    .value("Successfully logged out."));

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

        return new User("Bob", "123", "bob@gmail.com", false);
    }

}