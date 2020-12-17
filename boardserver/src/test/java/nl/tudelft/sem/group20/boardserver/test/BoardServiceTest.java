package nl.tudelft.sem.group20.boardserver.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.boardserver.BoardServer;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.repos.BoardRepository;
import nl.tudelft.sem.group20.boardserver.requests.CreateBoardRequest;
import nl.tudelft.sem.group20.boardserver.requests.EditBoardRequest;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
import nl.tudelft.sem.group20.exceptions.UserNotFoundException;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;


@AutoConfigureMockMvc
@WebMvcTest(BoardService.class)
@ContextConfiguration(classes = BoardServer.class)
public class BoardServiceTest {
    transient Board board1;
    transient String name1;
    transient String description1;
    transient boolean locked1;
    transient String user1;
    transient CreateBoardRequest createRequest1;

    transient Board board2;
    transient String name2;
    transient String description2;
    transient boolean locked2;
    transient String user2;
    transient CreateBoardRequest createRequest2;
    transient EditBoardRequest editRequest2;

    transient Board board3;
    transient String name3;
    transient String description3;
    transient boolean locked3;
    transient String user3;
    transient EditBoardRequest editRequest3;

    transient String token;
    transient AuthResponse failed;
    transient AuthResponse studentResponse;
    transient AuthResponse teacherResponse;
    transient AuthResponse randomUserResponse;

    transient List<Board> boardsList;

    transient BoardService boardService;

    @MockBean
    transient BoardRepository boardRepository;

    @MockBean
    transient RestTemplate restTemplate;

    @BeforeEach
    void initialize() {
        name1 = "name 1";
        description1 = "description 1";
        locked1 = false;
        user1 = "student";

        name2 = "name 2";
        description2 = "description 2";
        locked2 = false;
        user2 = "teacher";

        name3 = "name 3";
        description3 = "description 3";
        locked3 = false;
        user3 = "user3";

        token = "A random token.";

        failed = new AuthResponse();
        studentResponse = new AuthResponse(false, user1);
        teacherResponse = new AuthResponse(true, user2);
        randomUserResponse = new AuthResponse(true, user3);

        board1 = new Board(1, name1, description1, locked1, user2);
        board2 = new Board(2, name2, description2, locked2, user2);
        board3 = new Board(3, name3, description3, locked3, user3);

        createRequest1 = new CreateBoardRequest(board1.getName(), board1.getDescription());
        createRequest2 = new CreateBoardRequest(board2.getName(), board2.getDescription());
        editRequest2 = new EditBoardRequest(board2.getName(), board2.getDescription(),
                board2.isLocked(), board2.getId());
        editRequest3 = new EditBoardRequest(board3.getName(), board3.getDescription(),
                board3.isLocked(), 3);

        boardsList = new ArrayList<>();
        boardsList.add(board1);
        boardsList.add(board2);

        boardRepository = Mockito.mock(BoardRepository.class);
        Mockito.when(boardRepository.saveAndFlush(any(Board.class)))
                .thenReturn(new Board(123L, "name", "description", false, "username"));
        Mockito.when(boardRepository.findAll())
                .thenReturn(boardsList);
        Mockito.when(boardRepository.getById(2))
                .thenReturn(Optional.of(board2));

        Mockito.when(boardRepository.getOne(2L)).thenReturn(board2);
        Mockito.when(boardRepository.getOne(3L)).thenReturn(null);


        boardService = new BoardService(boardRepository, restTemplate);
    }

    @Test
    public void testGetBoards() {
        assertThat(boardService.getBoards()).hasSize(boardsList.size())
                .hasSameElementsAs(boardsList);
    }

    @Test
    public void testCreateBoardSuccessful() {
        Mockito.when(restTemplate.postForObject(eq(BoardService.getAuthenticateUserUrl()),
                        any(AuthRequest.class), eq(AuthResponse.class)))
                .thenReturn(teacherResponse);

        try {
            assertEquals(123, boardService.createBoard(createRequest1, token));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        verify(boardRepository, times(1)).saveAndFlush(any(Board.class));
    }

    @Test
    public void testCreateBoardUserNotFound() {
        Mockito.when(restTemplate.postForObject(eq(BoardService.getAuthenticateUserUrl()),
                        any(AuthRequest.class), eq(AuthResponse.class)))
                .thenReturn(failed);

        assertThrows(UserNotFoundException.class,
                        () -> boardService.createBoard(createRequest1, token));

        verify(boardRepository, times(0)).saveAndFlush(any(Board.class));
    }

    @Test
    public void testCreateBoardPermissionDenied() {
        Mockito.when(restTemplate.postForObject(eq(BoardService.getAuthenticateUserUrl()),
                        any(AuthRequest.class), eq(AuthResponse.class)))
                .thenReturn(studentResponse);

        assertThrows(AccessDeniedException.class,
                        () -> boardService.createBoard(createRequest2, token));

        verify(boardRepository, times(0)).saveAndFlush(any(Board.class));
    }

    @Test
    public void testUpdateBoardSuccessful() {
        Mockito.when(restTemplate.postForObject(eq(BoardService.getAuthenticateUserUrl()),
                any(AuthRequest.class), eq(AuthResponse.class)))
                .thenReturn(teacherResponse);

        try {
            assertTrue(boardService.updateBoard(editRequest2, token));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        verify(boardRepository, times(1)).saveAndFlush(any(Board.class));
    }

    @Test
    public void testUpdateBoardUserNotFound() {
        Mockito.when(restTemplate.postForObject(eq(BoardService.getAuthenticateUserUrl()),
                any(AuthRequest.class), eq(AuthResponse.class)))
                .thenReturn(failed);

        assertThrows(UserNotFoundException.class,
                () -> boardService.updateBoard(editRequest2, token));

        verify(boardRepository, times(0)).saveAndFlush(board1);
    }

    @Test
    public void testUpdateBoardPermissionDenied() {
        Mockito.when(restTemplate.postForObject(eq(BoardService.getAuthenticateUserUrl()),
                any(AuthRequest.class), eq(AuthResponse.class)))
                .thenReturn(randomUserResponse);

        Mockito.when(boardRepository.getById(editRequest2.getId()))
                .thenReturn(Optional.of(board2));

        assertThrows(AccessDeniedException.class,
                () -> boardService.updateBoard(editRequest2, token));

        verify(boardRepository, times(0)).saveAndFlush(board1);
    }

    @Test
    public void testUpdateNonExistingBoard() {
        //Doesn't update a board that is not in the database
        board3.setName("New name 3");
        Mockito.when(restTemplate.postForObject(eq(BoardService.getAuthenticateUserUrl()),
                any(AuthRequest.class), eq(AuthResponse.class)))
                .thenReturn(randomUserResponse);

        try {
            assertFalse(boardService.updateBoard(editRequest3, token));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        verify(boardRepository, times(0)).saveAndFlush(board3);
    }

    @Test
    public void testGetByIdSuccess() {
        assertEquals(boardService.getById(2L), board2);
    }

    @Test
    public void testGetByIdFail() {
        assertNull(boardService.getById(3L));
    }

}
