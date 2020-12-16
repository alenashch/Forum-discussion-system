import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@AutoConfigureMockMvc
@WebMvcTest(PostService.class)
@ContextConfiguration(classes = ContentServer.class)
public class PostServiceTest {

    transient String user1;
    transient int demoNumber1;
    transient String demoBody1;
    transient LocalDateTime demoCreated1;
    transient Post demoPost1;

    transient String user2;
    transient int demoNumber2;
    transient String demoBody2;
    transient LocalDateTime demoCreated2;
    transient Post demoPost2;

    transient String user3;
    transient int demoNumber3;
    transient String demoBody3;
    transient LocalDateTime demoCreated3;
    transient Post demoPost3;

    transient List<Post> posts;

    transient PostService postService;

    private transient TestThreadPostBuilder builder;

    @MockBean
    transient PostRepository postRepository;

    @MockBean
    transient ThreadRepository threadRepository;

    @BeforeEach
    void initialize() {

        user1 = "Ja";
        user2 = "ys";
        user3 = "on";

        demoNumber1 = 12;
        demoNumber2 = 14;
        demoNumber3 = 10;

        demoBody1 = "First body.";
        demoBody2 = "Second body.";
        demoBody3 = "Third body.";

        demoCreated1 = LocalDateTime.now();
        demoCreated2 = LocalDateTime.now().plusHours(2);
        demoCreated3 = LocalDateTime.now().minusDays(1);

        demoPost1 = new Post(demoNumber1, user1, demoBody1, null, demoCreated1);
        demoPost2 = new Post(demoNumber2, user2, demoBody2, null, demoCreated2);
        demoPost3 = new Post(demoNumber3, user3, demoBody3, null, demoCreated3);

        posts = new ArrayList<>();
        posts.add(demoPost1);
        posts.add(demoPost2);

        postRepository = Mockito.mock(PostRepository.class);

        builder = new TestThreadPostBuilder();

        Mockito.when(postRepository.findAll())
            .thenReturn(posts);
        Mockito.when(postRepository.saveAndFlush(any(Post.class)))
            .then(returnsFirstArg());
        Mockito.when(postRepository.getById(builder.getPostId()))
            .thenReturn(Optional.of(demoPost2));

        postService = new PostService(postRepository, threadRepository);
    }

    @Test
    void testGetPosts() {
        assertThat(postService.getPosts()).hasSize(posts.size()).hasSameElementsAs(posts);
    }

    @Test
    void testCreatePostSuccessful() {

        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));
        builder.setPostId(demoPost2.getId());
        assertEquals(0, postService.createPost(builder.createTestCreatePostRequest()));

        verify(postRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void testCreatePostUnsuccessful() {

        builder.setPostId(demoPost2.getId());
        assertEquals(-1, postService.createPost(builder.createTestCreatePostRequest()));

        //check that no post was added
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }

    @Test
    void updatePostSuccessful() {
        demoPost2.setBody("Updated body.");

        builder.setBody(demoPost2.getBody());
        builder.setPostId(demoPost2.getId());

        when(postRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestPost()));
        when(threadRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));

        assertTrue(postService.updatePost(builder.createTestEditPostRequest()));
        verify(postRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void updatePostUnsuccessful() {
        Mockito.when(postRepository.getById(3))
            .thenReturn(Optional.empty());

        builder.setPostId(3);
        assertFalse(postService.updatePost(builder.createTestEditPostRequest()));
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }

}
