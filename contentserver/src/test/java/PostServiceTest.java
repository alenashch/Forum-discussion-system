import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = PostService.class)
@AutoConfigureMockMvc
@WebMvcTest(PostService.class)
@ContextConfiguration(classes = ContentServer.class)
public class PostServiceTest {

    transient long demoId1;
    transient int demoNumber1;
    transient String demoBody1;
    transient LocalDateTime demoCreated1;
    transient Post demoPost1;

    transient long demoId2;
    transient int demoNumber2;
    transient String demoBody2;
    transient LocalDateTime demoCreated2;
    transient Post demoPost2;

    transient long demoId3;
    transient int demoNumber3;
    transient String demoBody3;
    transient LocalDateTime demoCreated3;
    transient Post demoPost3;

    transient List<Post> posts;

    transient PostService postService;

    @MockBean
    transient PostRepository postRepository;

    @BeforeEach
    void initialize() {
        demoId1 = 1;
        demoId2 = 2;
        demoId3 = 3;

        demoNumber1 = 12;
        demoNumber2 = 14;
        demoNumber3 = 10;

        demoBody1 = "First body.";
        demoBody2 = "Second body.";
        demoBody3 = "Third body.";

        demoCreated1 = LocalDateTime.now();
        demoCreated2 = LocalDateTime.now().plusHours(2);
        demoCreated3 = LocalDateTime.now().minusDays(1);

        demoPost1 = new Post(demoId1, demoNumber1, demoBody1, demoCreated1);
        demoPost2 = new Post(demoId2, demoNumber2, demoBody2, demoCreated2);
        demoPost3 = new Post(demoId3, demoNumber3, demoBody3, demoCreated3);

        posts = new ArrayList<>();
        posts.add(demoPost1);
        posts.add(demoPost2);

        postRepository = Mockito.mock(PostRepository.class);
        Mockito.when(postRepository.findAll())
                .thenReturn(posts);
        Mockito.when(postRepository.saveAndFlush(any(Post.class)))
                .then(returnsFirstArg());
        Mockito.when(postRepository.getById(2))
                .thenReturn(Optional.of(demoPost2));

        postService = new PostService(postRepository);
    }

    @Test
    void testGetPosts() {
        assertThat(postService.getPosts()).hasSize(posts.size()).hasSameElementsAs(posts);
    }

    @Test
    void testCreatePostSuccessful() {
        assertEquals(postService.createPost(demoPost3), demoPost3.getId());

        verify(postRepository, times(1)).saveAndFlush(demoPost3);
    }

    @Test
    void testCreatePostUnsuccessful() {
        assertEquals(-1, postService.createPost(demoPost2));

        //check that no post was added
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }

    @Test
    void updatePostSuccessful() {
        demoPost2.setBody("Updated body.");

        assertTrue(postService.updatePost(demoPost2));
        verify(postRepository, times(1)).saveAndFlush(demoPost2);
    }

    @Test
    void updatePostUnsuccessful() {
        Mockito.when(postRepository.getById(3))
                .thenReturn(Optional.empty());

        assertFalse(postService.updatePost(demoPost3));
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }

}
