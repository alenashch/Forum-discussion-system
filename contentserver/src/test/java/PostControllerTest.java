import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.controller.PostController;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditPostRequest;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
@ContextConfiguration(classes = ContentServer.class)
class PostControllerTest {

    private transient String token = "1";
    private transient String tokenName = "token";

    @Autowired
    @MockBean
    private transient PostService postService;
    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient ObjectMapper objectMapper;

    private transient TestThreadPostBuilder builder;


    @BeforeEach
    void setUp() {

        builder = new TestThreadPostBuilder();
    }

    @Test
    void createPostSuccessTest() {

        CreatePostRequest createPostRequest = builder.createTestCreatePostRequest();
        when(postService.createPost(anyString(), any(CreatePostRequest.class)))
            .thenReturn(builder.getPostId());

        try {
            mockMvc.perform(post("/post/create")
                .contentType(APPLICATION_JSON)
                .header(tokenName, token)
                .content(objectMapper.writeValueAsString(createPostRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                    .string("A new post with ID: " + builder.getPostId() + " has been created"));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Test
    void createPostFailTest() {

        CreatePostRequest createPostRequest = builder.createTestCreatePostRequest();
        when(postService.createPost(anyString(), any(CreatePostRequest.class))).thenReturn(-1L);

        try {
            mockMvc.perform(post("/post/create")
                .contentType(APPLICATION_JSON)
                .header(tokenName, token)
                .content(objectMapper.writeValueAsString(createPostRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(
                    content().string("This post could not be created, it may already exist"));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void getPostsTest() {

        List<Post> list = Collections.singletonList(builder.createTestPost());

        when(postService.getPosts()).thenReturn(list);

        try {

            mockMvc.perform(get("/post/get")
                .contentType(APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$[0].body").value(builder.getBody()))
                .andExpect(jsonPath("$[0].id").value(builder.getPostId()));

            Mockito.verify(postService, times(1)).getPosts();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void editPostSuccessTest() {

        EditPostRequest editPostRequest = builder.createTestEditPostRequest();

        when(postService.updatePost(any(EditPostRequest.class))).thenReturn(true);

        //  given(postService.updatePost(any(Post.class))).willReturn(true);
        try {
            mockMvc.perform(post("/post/edit")
                .contentType(APPLICATION_JSON)
                .header(tokenName, token)
                .content(objectMapper.writeValueAsString(editPostRequest)).accept(APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("The post with ID: " + builder.getPostId() + " has "
                    + "been updated"));
            //.andExpect((ResultMatcher) jsonPath("$.success").value(true));


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Test
    void editPostFailTest() {

        EditPostRequest editPostRequest = builder.createTestEditPostRequest();

        when(postService.updatePost(any(EditPostRequest.class))).thenReturn(false);

        //  given(postService.updatePost(any(Post.class))).willReturn(true);
        try {
            mockMvc.perform(post("/post/edit")
                .contentType(APPLICATION_JSON)
                .header(tokenName, token)
                .content(new ObjectMapper().writeValueAsString(editPostRequest))
                .accept(APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string("Post with ID: " + editPostRequest.getPostId()
                    + " could not be updated"));
            //.andExpect((ResultMatcher) jsonPath("$.success").value(true));


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    /*
    private String createJsonRequest(Post post) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(post);
    }
    */

}