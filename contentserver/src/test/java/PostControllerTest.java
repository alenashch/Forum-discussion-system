import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.controller.PostController;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
@ContextConfiguration(classes = ContentServer.class)
class PostControllerTest {


    @Autowired
    @MockBean
    private transient PostService postService;
    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient ObjectMapper objectMapper;

    @Test
    void createPostTest() {

        Post post = createTestPost();
        when(postService.createPost(post)).thenReturn(123L);

        try {
            mockMvc.perform(post("/post/create")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("A new post with ID: 123 has been created"));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Test
    void getPostsTest() {

        List<Post> list = Collections.singletonList(createTestPost());

        when(postService.getPosts()).thenReturn(list);

        try {

            mockMvc.perform(get("/post/get")
                .contentType(APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$[0].body").value("abc"))
                .andExpect(jsonPath("$[0].id").value("123"));

            Mockito.verify(postService, times(1)).getPosts();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void editPostTest() {

        Post post = createTestPost();

        when(postService.updatePost(post)).thenReturn(true);

        //  given(postService.updatePost(any(Post.class))).willReturn(true);
        try {
            mockMvc.perform(post("/post/edit")
                .contentType(APPLICATION_JSON)
                .content(createJsonRequest(post)).accept(APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
            //.andExpect((ResultMatcher) jsonPath("$.success").value(true));


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private Post createTestPost() {

        LocalDateTime time = LocalDateTime.now();
        return new Post(123L, 1, "abc", time);
    }

    private String createJsonRequest(Post post) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(post);
    }
}