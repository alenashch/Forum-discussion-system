package nl.tudelft.sem.group20.contentserver.test;

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
import java.util.Set;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.controller.PostController;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditPostRequest;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    void createPostSuccessTest() throws Exception {

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
    public void getPostTest() throws Exception {

        builder.setPostId(1L);
        Post post = builder.createTestPost();
        when(postService.getPostById(builder.getPostId())).thenReturn(post);

        try {

            MvcResult result = mockMvc.perform(get("/post/get/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(post),
                result.getResponse().getContentAsString());
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    public void getPostsFromThreadTest() throws Exception {

        builder.setThreadId(1L);
        Set<Post> posts = Set.of(builder.createTestPost());

        when(postService.getPostsFromThread(builder.getThreadId())).thenReturn(posts);

        try {

            MvcResult result = mockMvc.perform(get("/post/get/fromthread/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(posts),
                result.getResponse().getContentAsString());
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    public void isEditedTest() throws Exception {

        builder.setPostId(1L);
        when(postService.isEdited(builder.getPostId())).thenReturn(true);

        try {

            MvcResult result =
                mockMvc.perform(get("/post/checkedited/1").contentType(APPLICATION_JSON))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(true),
                result.getResponse().getContentAsString());
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}