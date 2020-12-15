package nl.tudelft.sem.group20.boardserver.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.boardserver.BoardServer;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.repos.BoardRepository;
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
    transient long id1;
    transient String name1;
    transient String description1;
    transient boolean locked1;
    transient String user1;

    transient Board board2;
    transient long id2;
    transient String name2;
    transient String description2;
    transient boolean locked2;
    transient String user2;

    transient Board board3;
    transient long id3;
    transient String name3;
    transient String description3;
    transient boolean locked3;
    transient String user3;

    transient AuthRequest tokenRequest;
    transient StatusResponse failed;
    transient StatusResponse studentResponse;
    transient StatusResponse teacherResponse;

    transient List<Board> boardsList;

    transient BoardService boardService;

    @MockBean
    transient BoardRepository boardRepository;

    @MockBean
    transient RestTemplate restTemplate;

    @BeforeEach
    void initialize() {
        id1 = 1;
        name1 = "name 1";
        description1 = "description 1";
        locked1 = false;
        user1 = "user1";

        id2 = 2;
        name2 = "name 2";
        description2 = "description 2";
        locked2 = false;
        user2 = "user2";

        id3 = 3;
        name3 = "name 3";
        description3 = "description 3";
        locked3 = false;
        user3 = "user3";

        tokenRequest = new AuthRequest("A random token.");

        failed = new AuthResponse();
        studentResponse = new AuthResponse(false, "student");
        teacherResponse = new AuthResponse(true, "teacher");

        board1 = new Board(id1, name1, description1, locked1, user1);
        board2 = new Board(id2, name2, description2, locked2, user2);
        board3 = new Board(id3, name3, description3, locked3, user3);


        boardsList = new ArrayList<>();
        boardsList.add(board1);
        boardsList.add(board2);

        boardRepository = Mockito.mock(BoardRepository.class);
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
        Mockito.when(restTemplate.postForObject(BoardService.getAuthenticateUserUrl(),
                        tokenRequest, StatusResponse.class))
                .thenReturn(teacherResponse);

        try {
            assertEquals(boardService.createBoard(board3, tokenRequest), board3.getId());
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        verify(boardRepository, times(1)).saveAndFlush(board3);
    }

    @Test
    public void testCreateBoardUserNotFound() {
        Mockito.when(restTemplate.postForObject(BoardService.getAuthenticateUserUrl(),
                        tokenRequest, StatusResponse.class))
                .thenReturn(failed);
        assertThrows(UserNotFoundException.class,
                        () -> boardService.createBoard(board1, tokenRequest));
        verify(boardRepository, times(0)).saveAndFlush(board1);
    }

    @Test
    public void testCreateBoardPermissionDenied() {
        Mockito.when(restTemplate.postForObject(BoardService.getAuthenticateUserUrl(),
                        tokenRequest, StatusResponse.class))
                .thenReturn(studentResponse);
        assertThrows(AccessDeniedException.class,
                        () -> boardService.createBoard(board2, tokenRequest));
        verify(boardRepository, times(0)).saveAndFlush(board2);
    }

    @Test
    public void testCreateExistingBoard() {
        //Doesn't create a board if it already exists in the database
        Mockito.when(restTemplate.postForObject(BoardService.getAuthenticateUserUrl(),
                        tokenRequest, StatusResponse.class))
                .thenReturn(teacherResponse);

        try {
            assertEquals(boardService.createBoard(board2, tokenRequest), -1);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        verify(boardRepository, times(0)).saveAndFlush(board2);
    }

    @Test
    public void testUpdateBoard() {
        board2.setName("New name 2");

        try {
            assertTrue(boardService.updateBoard(board2, tokenRequest));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        verify(boardRepository, times(1)).saveAndFlush(board2);
    }

    @Test
    public void testUpdateNonExistingBoard() {
        //Doesn't update a board that is not in the database
        board3.setName("New name 3");

        try {
            assertFalse(boardService.updateBoard(board3, tokenRequest));
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
