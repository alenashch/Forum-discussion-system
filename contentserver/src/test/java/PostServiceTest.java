import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import exceptions.AuthorizationFailedException;
import exceptions.BoardIsLockedException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PostNotFoundException;
import exceptions.ThreadIsLockedException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc
@WebMvcTest(PostService.class)
@ContextConfiguration(classes = ContentServer.class)
public class PostServiceTest {

    transient AuthResponse authResponse;
    transient String token;
    transient Post demoPost;
    transient ResponseEntity<Boolean> responseEntity;
    transient IsLockedResponse success;
    transient IsLockedResponse failed;

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

        builder = new TestThreadPostBuilder();
        demoPost = builder.createTestPost();

        //board is not locked
        success = new IsLockedResponse(false);
        //board is locked
        failed = new IsLockedResponse(true);

        postRepository = mock(PostRepository.class);

        responseEntity = new ResponseEntity<>(false, HttpStatus.OK);
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(ResponseEntity.class)))
            .thenReturn(responseEntity);

        Mockito.when(postRepository.saveAndFlush(any(Post.class)))
            .then(returnsFirstArg());
        Mockito.when(postRepository.getById(builder.getPostId()))
            .thenReturn(Optional.of(demoPost));

        postService = new PostService(postRepository, threadRepository, restTemplate);
    }

    @Test
    void testGetPosts() {

        List<Post> posts = new ArrayList<>();
        posts.add(builder.createTestPost());


        Mockito.when(postRepository.findAll())
            .thenReturn(posts);

        assertThat(postService.getPosts()).hasSize(posts.size()).hasSameElementsAs(posts);
    }

    @Test
    void testCreatePostSuccessful() throws Exception {
        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);
        when(restTemplate.getForObject(anyString(), eq(IsLockedResponse.class)))
            .thenReturn(success);

        builder.setPostId(demoPost.getId());
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
        when(restTemplate.getForObject(anyString(), eq(IsLockedResponse.class)))
                .thenReturn(success);

        builder.setPostId(demoPost.getId());
        assertThrows(AuthorizationFailedException.class, () ->
            postService.createPost(token,
                builder.createTestCreatePostRequest())
        );

        //check that no post was added
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }


    @Test
    void testCreatePostUnsuccessfulBoardLocked() {

        responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(ResponseEntity.class)))
            .thenReturn(responseEntity);
        when(restTemplate.getForObject(anyString(), eq(IsLockedResponse.class)))
                .thenReturn(failed);

        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);
        assertThrows(BoardIsLockedException.class,
            () -> postService.createPost(token, builder.createTestCreatePostRequest()));

        verify(postRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void testCreatePostUnsuccessfulThreadLocked() {

        responseEntity = new ResponseEntity<>(false, HttpStatus.OK);
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(ResponseEntity.class)))
            .thenReturn(responseEntity);
        //board is not locked
        when(restTemplate.getForObject(anyString(), eq(IsLockedResponse.class)))
                .thenReturn(success);

        builder.setLocked(true);
        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        assertThrows(ThreadIsLockedException.class,
            () -> postService.createPost(token, builder.createTestCreatePostRequest()));

        verify(postRepository, times(0)).saveAndFlush(any());
    }


    @Test
    void testCreatePostUnsuccessfulBoardNotFound() {

        responseEntity = new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(ResponseEntity.class)))
            .thenReturn(responseEntity);

        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);
        when(threadRepository.getById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BoardThreadNotFoundException.class, () ->
            postService.createPost(token,
                builder.createTestCreatePostRequest())
        );

        //check that no post was added
        verify(postRepository, times(0)).saveAndFlush(any(Post.class));
    }


    @Test
    void updatePostSuccessful() throws Exception {

        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        builder.setBody("Updated body.");
        builder.setCreatorName("bob");
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
    void authenticateUserSuccessful() throws Exception {

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

    @Test
    void updatePostUserNotFound() {

        when(postRepository.getById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.updatePost(token,
            builder.createTestEditPostRequest()));
    }

    @Test
    void updatePostWrongCreator() {

        builder.setCreatorName("Mr. Robot");
        when(restTemplate.postForObject(Mockito.anyString(),
            Mockito.any(AuthRequest.class),
            Mockito.eq(AuthResponse.class))).thenReturn(authResponse);
        when(postRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestPost()));
        when(threadRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));

        assertThrows(AuthorizationFailedException.class, () -> postService.updatePost(token,
            builder.createTestEditPostRequest()));
    }

    @Test
    void getPostByIdTest() throws Exception {

        Post post = builder.createTestPost();

        when(postRepository.getById(builder.getPostId())).thenReturn(Optional.of(post));

        assertEquals(post, postService.getPostById(builder.getPostId()));
    }

    @Test
    void getPostByIdFailTest() {

        when(postRepository.getById(builder.getPostId())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
            () -> postService.getPostById(builder.getPostId()));
    }

    @Test
    void getPostsFromThreadTest() throws Exception {

        Set<Post> posts = Set.of(builder.createTestPost());
        BoardThread boardThread = builder.createTestBoardThread();
        boardThread.setPosts(posts);

        when(threadRepository.getById(builder.getThreadId())).thenReturn(Optional.of(boardThread));

        assertEquals(posts, postService.getPostsFromThread(builder.getThreadId()));
    }

    @Test
    void getPostsFromThreadFailTest() {

        when(threadRepository.getById(builder.getThreadId())).thenReturn(Optional.empty());

        assertThrows(BoardThreadNotFoundException.class,
            () -> postService.getPostsFromThread(builder.getThreadId()));
    }

    @Test
    void isEditedTest() throws Exception {

        Post post = builder.createTestPost();

        when(postRepository.getById(builder.getPostId())).thenReturn(Optional.of(post));

        assertFalse(postService.isEdited(builder.getPostId()));

        post.setEdited(LocalDateTime.now().plus(Duration.ofHours(1)));

        assertTrue(postService.isEdited(builder.getPostId()));
    }

    @Test
    void isEditedTestFail() {

        when(postRepository.getById(builder.getPostId())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
            () -> postService.isEdited(builder.getPostId()));
    }
}
