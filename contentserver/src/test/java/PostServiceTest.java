import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import exceptions.AuthorizationFailedException;
import exceptions.BoardThreadNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc
@WebMvcTest(PostService.class)
@ContextConfiguration(classes = ContentServer.class)
public class PostServiceTest {

    transient AuthResponse authResponse;
    transient String token;

    transient String demoName;
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

    private transient TestThreadPostBuilder builder;

    @MockBean
    transient PostRepository postRepository;

    @MockBean
    transient ThreadRepository threadRepository;

    @MockBean
    transient RestTemplate restTemplate;

    @BeforeEach
    void initialize() {

        restTemplate = mock(RestTemplate.class);
        authResponse = new AuthResponse(true, "bob");
        token = "1";

        demoName = "Bob";
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

        demoPost1 = new Post(demoNumber1, demoName, demoBody1, null, demoCreated1);
        demoPost2 = new Post(demoNumber2, demoName, demoBody2, null, demoCreated2);
        demoPost3 = new Post(demoNumber3, demoName, demoBody3, null, demoCreated3);

        posts = new ArrayList<>();
        posts.add(demoPost1);
        posts.add(demoPost2);

        postRepository = mock(PostRepository.class);

        builder = new TestThreadPostBuilder();

        Mockito.when(postRepository.findAll())
            .thenReturn(posts);
        Mockito.when(postRepository.saveAndFlush(any(Post.class)))
            .then(returnsFirstArg());
        Mockito.when(postRepository.getById(builder.getPostId()))
            .thenReturn(Optional.of(demoPost2));

        postService = new PostService(postRepository, threadRepository, restTemplate);
    }

    @Test
    void testGetPosts() {
        assertThat(postService.getPosts()).hasSize(posts.size()).hasSameElementsAs(posts);
    }

    @Test
    void testCreatePostSuccessful() {

        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);
        builder.setPostId(demoPost2.getId());
        assertEquals(0, postService.createPost(token, builder.createTestCreatePostRequest()));

        verify(postRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void testCreatePostUnsuccessfulAuthorization() {

        AuthResponse authResponse2 = new AuthResponse();
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse2);
        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));

        builder.setPostId(demoPost2.getId());
        assertThrows(AuthorizationFailedException.class, () ->
            postService.createPost(token,
                builder.createTestCreatePostRequest())
        );

        //check that no post was added
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }

    @Test
    void testCreatePostUnsuccessfulBoardNotFound() {

        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);
        when(threadRepository.getById(anyLong())).thenReturn(Optional.empty());

        builder.setPostId(demoPost2.getId());
        assertThrows(BoardThreadNotFoundException.class, () ->
            postService.createPost(token,
                builder.createTestCreatePostRequest())
        );

        //check that no post was added
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }


    @Test
    void updatePostSuccessful() {

        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        demoPost2.setBody("Updated body.");

        builder.setBody(demoPost2.getBody());
        builder.setPostId(demoPost2.getId());

        when(postRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestPost()));
        when(threadRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));

        postService.updatePost(token, builder.createTestEditPostRequest());
        verify(postRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void updatePostUnsuccessfulAuthorization() {

        builder.setPostId(3);
        Mockito.when(postRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestPost()));
        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));

        AuthResponse authResponse2 = new AuthResponse();
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse2);

        assertThrows(AuthorizationFailedException.class, () -> postService.updatePost(token,
            builder.createTestEditPostRequest()));
    }

    @Test
    void authenticateUserSuccessful() {

        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        assertEquals("bob", postService.authenticateUser(token));
    }

    @Test
    void authenticateUserUnsuccessful() {

        AuthResponse authResponse2 = new AuthResponse();
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse2);

        assertThrows(AuthorizationFailedException.class, () -> postService.authenticateUser(token));
    }
}
