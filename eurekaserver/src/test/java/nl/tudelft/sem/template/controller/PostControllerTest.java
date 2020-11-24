package nl.tudelft.sem.template.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.template.model.Post;
import nl.tudelft.sem.template.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
class PostControllerTest {


    @Autowired
    @MockBean
    transient PostService postService;
    @Autowired
    private transient MockMvc mockMvc;

    @Test
    void createPost() {

        LocalDateTime time = LocalDateTime.now();
        Post post = new Post(123, 1, "abc", time);
        when(postService.createPost(any(Post.class))).thenReturn(1L);

        try {
            MvcResult result = mockMvc.perform(post("/post/create")
                .contentType(APPLICATION_JSON)
                .content(createJsonRequest(post)))
                .andDo(print())
                .andReturn();

            String json = result.getResponse().getContentAsString();
            Long response = new ObjectMapper().readValue(json, Long.class);

            Assertions.assertEquals(response, 1L);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Test
    void getPosts() {

        LocalDateTime time = LocalDateTime.now();
        List<Post> list = Collections.singletonList(new Post(123, 1, "abc", time));

        when(postService.getPosts()).thenReturn(list);

        try {

            MvcResult result = mockMvc.perform(get("/post/get")
                .contentType(APPLICATION_JSON))
                .andReturn();

            String json = result.getResponse().getContentAsString();

            System.out.println(json);

            Post responsePost = new ObjectMapper().readValue(json, Post.class);

            Assertions.assertEquals(list.get(0), responsePost);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void editPost() {
        LocalDateTime time = LocalDateTime.now();
        Post post = new Post(123, 1, "abc", time);

        //when(postService.updatePost(post)).thenReturn(true);

        given(postService.updatePost(any(Post.class))).willReturn(true);
        try {

            MvcResult result = mockMvc.perform(post("/post/edit")
                .contentType(APPLICATION_JSON)
                .content(createJsonRequest(post)))
                .andReturn();

            String json = result.getResponse().getContentAsString();

            Boolean response = new ObjectMapper().readValue(json, Boolean.class);

            Assertions.assertTrue(response);
            // System.out.println(json);

            // Post responsePost = new ObjectMapper().readValue(json, Boolean.class);

            //AssertEquals(post, responsePost);

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private String createJsonRequest(Post post) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(post);
    }
}