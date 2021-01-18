package nl.tudelft.sem.group20.contentserver.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import exceptions.AuthorizationFailedException;
import exceptions.BoardIsLockedException;
import exceptions.BoardNotFoundException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PermissionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.CheckRequest;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.VerifyAuth;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.VerifyBoard;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
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
@WebMvcTest(ThreadService.class)
@ContextConfiguration(classes = ContentServer.class)
public class ThreadServiceTest {


    transient VerifyBoard verifyBoard;
    transient VerifyAuth verifyAuth;

    transient CheckRequest cr;


    private transient TestBoardThreadBuilder builder;
    transient AuthResponse authResponse;
    transient String token;
    transient BoardThread thread1;
    transient IsLockedResponse boardUnlocked;
    transient IsLockedResponse boardLocked;
    transient ResponseEntity<Boolean> responseEntity;

    transient ThreadService threadService;


    transient List<BoardThread> threads;


    @MockBean
    transient ThreadRepository threadRepository;

    @MockBean
    transient RestTemplate restTemplate;




    @BeforeEach
    void initialize() {




        restTemplate = mock(RestTemplate.class);

        cr = new CheckRequest("token", 1, restTemplate);
        verifyBoard = new VerifyBoard();
        verifyAuth = new VerifyAuth();


        threadRepository = mock(ThreadRepository.class);
        authResponse = new AuthResponse(true, "jayson");
        boardUnlocked = new IsLockedResponse(false);
        boardLocked = new IsLockedResponse(true);

        builder = new TestBoardThreadBuilder();
        thread1 = builder.makeBoardThread();
        builder.setBoardId(1);


        responseEntity = new ResponseEntity<>(false, HttpStatus.OK);

        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(ResponseEntity.class)))
                .thenReturn(responseEntity);
        Mockito.when(threadRepository.saveAndFlush(any(BoardThread.class)))
                .then(returnsFirstArg());
        Mockito.when(threadRepository.getById(builder.getBoardId()))
                .thenReturn(Optional.of(thread1));

        threadService = new ThreadService(threadRepository, restTemplate);


    }

    @Test
    void testGetThreads() {

        List<BoardThread> threads  = new ArrayList<>();
        threads.add(builder.makeBoardThread());

        Mockito.when(threadRepository.findAll())
                .thenReturn(threads);

        assertThat(threadService.getThreads())
            .hasSize(threads.size())
            .hasSameElementsAs(threads);
    }

    @Test
    void testCreateThreadSuccessful() throws Exception {


        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(IsLockedResponse.class))).thenReturn(boardUnlocked);

        assertTrue(verifyBoard.handle(cr));
        assertTrue(verifyAuth.handle(cr));


        //builder.setBoardId(thread1.getBoardId());

        assertEquals(0, threadService.createThread(token, builder.createBoardThreadRequest()));

        verify(threadRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void testBoardVerifyException() throws Exception {


        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(IsLockedResponse.class))).thenReturn(boardLocked);

        //builder.setBoardId(thread1.getBoardId());

        assertThrows(BoardIsLockedException.class, () ->
                threadService.createThread(token,
                        builder.createBoardThreadRequest()));
    }


    @Test
    void testCreateThreadUnsuccessfulAuthorization() {

        AuthResponse authResponse2 = new AuthResponse();
        IsLockedResponse locked = new IsLockedResponse(false);

        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse2);

        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(IsLockedResponse.class))).thenReturn(locked);


        builder.setBoardId(thread1.getId());

        assertThrows(AuthorizationFailedException.class, () ->
                threadService.createThread(token,
                        builder.createBoardThreadRequest()));


        assertThrows(AuthorizationFailedException.class, () ->
                verifyAuth.handle(cr));



        //check that no post was added
        verify(threadRepository, times(0)).saveAndFlush(any(BoardThread.class));
    }

    @Test
    void testCreatePostUnsuccessfulAuthorization() {

        AuthResponse authResponse2 = new AuthResponse();
        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse2);

        builder.setBoardId(thread1.getId());
        assertThrows(AuthorizationFailedException.class, () ->
                threadService.createThread(token,
                        builder.createBoardThreadRequest()));



        //check that no post was added
        verify(threadRepository, times(0)).saveAndFlush(any(BoardThread.class));
    }

    @Test
    void testCreateThreadUnsuccessfulBoardLocked() {

        /*responseEntity = new ResponseEntity<>(true, HttpStatus.OK);

        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(ResponseEntity.class)))
                .thenReturn(responseEntity);*/

        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(IsLockedResponse.class))).thenReturn(boardLocked);

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        assertThrows(BoardIsLockedException.class,
                () -> threadService.createThread(token, builder.createBoardThreadRequest()));
        verify(threadRepository, times(0)).saveAndFlush(any());


    }

    @Test
    void testCreateThreadUnsuccessfulBoardNotFound() {

        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(IsLockedResponse.class))).thenReturn(null);

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        assertThrows(BoardNotFoundException.class,
                () -> threadService.createThread(token, builder.createBoardThreadRequest()));
        verify(threadRepository, times(0)).saveAndFlush(any());

        assertThrows(BoardNotFoundException.class,
                () -> verifyBoard.handle(cr));
    }

    @Test
    void updateThreadSuccesful() throws Exception {

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));


        threadService.updateThread(token, builder.editBoardThreadRequest());

        verify(threadRepository, times(1)).saveAndFlush(any());

    }

    @Test
    void updateThreadDoesNotExistFail() {

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.empty());

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        assertThrows(BoardThreadNotFoundException.class, () ->
                threadService.updateThread(token,
                        builder.editBoardThreadRequest()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void updateThreadAuthenticationFail() {

        AuthResponse failAuth = new AuthResponse();

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(failAuth);


        assertThrows(AuthorizationFailedException.class, () ->
                threadService.updateThread(token,
                        builder.editBoardThreadRequest()));



        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void updateThreadDifferingUsernamesFail() {

        AuthResponse auth = new AuthResponse(true, "Elmo");

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        assertThrows(PermissionException.class, () ->
                threadService.updateThread(token,
                        builder.editBoardThreadRequest()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void succesfulThreadLock() throws Exception {
        AuthResponse auth = new AuthResponse(true, "Elmo");

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        builder.setBoardThreadNumber(4);
        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        assertThat(threadService.lockThread(token, builder.getBoardThreadId()))
               .isEqualTo("Thread with ID " + builder.getBoardThreadId()
                       + " has been locked");
        verify(threadRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void failedThreadLockAuthentication() {

        AuthResponse auth = new AuthResponse();

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        assertThrows(AuthorizationFailedException.class, () ->
                threadService.lockThread(token, builder.getBoardThreadId()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void failedLockThreadDoesNotExist() {


        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(BoardThreadNotFoundException.class, () ->
                threadService.lockThread(token, builder.getBoardThreadId()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void lockFailNotaTeacher() {

        AuthResponse auth = new AuthResponse(false, "Raul");

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        assertThrows(AuthorizationFailedException.class, () ->
                threadService.lockThread(token, builder.getBoardThreadId()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void alreadyLockedThread() throws Exception {

        AuthResponse auth = new AuthResponse(true, "Raul");

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        builder.setLocked(true);
        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        assertThat(threadService.lockThread(token, builder.getBoardThreadId()))
                .isEqualTo("Thread with ID " + builder.getBoardThreadId()
                        + " is already locked");

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void succesfulThreadUnlock() throws Exception {

        AuthResponse auth = new AuthResponse(true, "Elmo");

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        builder.setBoardThreadNumber(4);
        builder.setLocked(true);
        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        assertThat(threadService.unlockThread(token, builder.getBoardThreadId()))
                .isEqualTo("Thread on ID " + builder.getBoardThreadId()
                        + " has been unlocked");

        verify(threadRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void failedThreadUnlockAuthentication() {

        AuthResponse auth = new AuthResponse();

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        assertThrows(AuthorizationFailedException.class, () ->
                threadService.unlockThread(token, builder.getBoardThreadId()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void failedUnlockThreadDoesNotExist() {


        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(authResponse);

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(BoardThreadNotFoundException.class, () ->
                threadService.unlockThread(token, builder.getBoardThreadId()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void unlockFailNotaTeacher() {

        AuthResponse auth = new AuthResponse(false, "Gavin");

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        assertThrows(AuthorizationFailedException.class, () ->
                threadService.unlockThread(token, builder.getBoardThreadId()));

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void alreadyUnlockedThread() throws Exception {

        AuthResponse auth = new AuthResponse(true, "Gavin");

        when(restTemplate.postForObject(Mockito.anyString(),
                Mockito.any(AuthRequest.class),
                Mockito.eq(AuthResponse.class))).thenReturn(auth);

        when(threadRepository.getById(anyLong()))
                .thenReturn(Optional.of(builder.makeBoardThread()));

        assertThat(threadService.unlockThread(token, builder.getBoardThreadId()))
                .isEqualTo("Thread on ID " + builder.getBoardThreadId()
                        + " is already unlocked");

        verify(threadRepository, times(0)).saveAndFlush(any());
    }

    @Test
    void gettingAllThreadsFromBoard() throws Exception {
        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(IsLockedResponse.class))).thenReturn(boardUnlocked);

        List<BoardThread> threads  = new ArrayList<>();
        threads.add(builder.makeBoardThread());

        builder.setBoardId(1);

        Mockito.when(threadRepository.findAll())
                .thenReturn(threads);

        assertThat(threadService.getThreadsPerBoard(1))
                .hasSize(threads.size())
                .hasSameElementsAs(threads);

    }

    @Test
    void gettingAllThreadsFromLockedBoard() {
        when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(IsLockedResponse.class))).thenReturn(boardLocked);

        assertThrows(BoardIsLockedException.class, () ->
                threadService.getThreadsPerBoard(1));

    }

}
















