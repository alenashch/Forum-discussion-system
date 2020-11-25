package nl.tudelft.sem.group20.postserver.test;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.postserver.Post;
import nl.tudelft.sem.group20.postserver.PostController;
import nl.tudelft.sem.group20.postserver.PostServer;
import nl.tudelft.sem.group20.postserver.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
@ContextConfiguration(classes = PostServer.class)
class PostControllerTest {


    @Autowired
    @MockBean
    private transient PostService postService;
    @Autowired
    private transient MockMvc mockMvc;

    /*
        @Test
        void createPostTest() {

            LocalDateTime time = LocalDateTime.now();
            Post post = new Post(123, 1, "abc", null);
            when(postService.createPost(any(Post.class))).thenReturn(1L);

            try {
                mockMvc.perform(post("/post/create")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(post)))
                    .andDo(print())
                    .andExpect((ResultMatcher) jsonPath("$.id").value(123))
                    .andExpect(jsonPath("$.postNumber").value(1));

                String json = result.getResponse().getContentAsString();

                var response = new ObjectMapper().readValue(json, HashMap.class);


                Assertions.assertEquals(response.get("id"), 1L);
            } catch (Exception e) {

                e.printStackTrace();
            }


               }
    */
    @Test
    void getPostsTest() {

        LocalDateTime time = LocalDateTime.of(2020, 10, 1, 10, 34, 0);
        List<Post> list = Collections.singletonList(new Post(123, 1, "abc", time));

        when(postService.getPosts()).thenReturn(list);

        try {

            MvcResult result = mockMvc.perform(get("/post/get")
                .contentType(APPLICATION_JSON)).andReturn();
            // .andExpect((ResultMatcher) jsonPath("$[0].body").value("abc"))
            //.andExpect((ResultMatcher) jsonPath("$[0].id").value("123"));

            String json = result.getResponse().getContentAsString();

            System.out.println(json);

            var responsePost = new ObjectMapper().readValue(json,
                new TypeReference<List<Post>>() {
                });

            Assertions.assertEquals(list.get(0), responsePost.get(0));
        } catch (Exception e) {

            e.printStackTrace();
        }

    } /*

        @Test
        void editPostTest () {
        LocalDateTime time = LocalDateTime.now();
        Post post = new Post(123L, 1, "abc", null);

        //when(postService.updatePost(post)).thenReturn(true);

        given(postService.updatePost(any(Post.class))).willReturn(true);
        try {

            ObjectMapper mapper = new ObjectMapper();

             MvcResult result =  mockMvc.perform(post("/post/edit")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(post)).accept(APPLICATION_JSON))
                 .andReturn();
                //.andExpect((ResultMatcher) jsonPath("$.success").value(true));

            String json = result.getResponse().getContentAsString();

            Boolean response = new ObjectMapper().readValue(json, Boolean.class);

            Assertions.assertTrue(response);

             System.out.println(json);

             Boolean responsePost = new ObjectMapper().readValue(json, Boolean.class);

            Assertions.assertTrue(responsePost);


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

            private String createJsonRequest (Post post) throws JsonProcessingException {

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
                ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

                return ow.writeValueAsString(post);
            } */
}