package nl.tudelft.sem.group20.boardserver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.boardserver.BoardServer;
import nl.tudelft.sem.group20.boardserver.controllers.BoardController;
import nl.tudelft.sem.group20.boardserver.controllers.BoardControllerAdvice;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.requests.CreateBoardRequest;
import nl.tudelft.sem.group20.boardserver.requests.EditBoardRequest;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
import nl.tudelft.sem.group20.exceptions.UserNotFoundException;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@AutoConfigureMockMvc
@WebMvcTest(BoardController.class)
@ContextConfiguration(classes = BoardServer.class)
class BoardControllerTest {

    transient Board board;
    transient CreateBoardRequest createRequest;
    transient EditBoardRequest editRequest;
    transient List<Board> list;

    @Autowired
    @MockBean
    transient BoardService boardService;
    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient ObjectMapper objectMapper;

    transient BoardControllerAdvice controllerAdvice;

    transient String createUrl;
    transient String editUrl;

    transient String tokenHeaderName;
    transient String token;

    @BeforeEach
    void initialize() {
        token = "A token.";
        tokenHeaderName = "user-token";
        controllerAdvice = new BoardControllerAdvice();

        createUrl = "/board/create";
        editUrl = "/board/edit";

        board = new Board(1, "Board 1", "description", false, "user");
        createRequest = new CreateBoardRequest(board.getName(), board.getDescription());
        editRequest = new EditBoardRequest(board.getName(), board.getDescription(),
                board.isLocked(), board.getId());
        list = Collections.singletonList(board);
    }


    @Test
    void testCreateBoardSuccessful() {
        try {
            when(boardService.createBoard(any(CreateBoardRequest.class), anyString()))
                    .thenReturn(board.getId());
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(createUrl)
                    .contentType(APPLICATION_JSON)
                    .header(tokenHeaderName, token)
                    .content(objectMapper.writeValueAsString(createRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string(
                            "A new board with ID: " + board.getId() + " has been created"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateBoardUserNotFound() {
        //token is not valid

        try {
            when(boardService.createBoard(any(CreateBoardRequest.class), any(String.class)))
                    .thenThrow(new UserNotFoundException("This user does not exist."));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(createUrl)
                    .contentType(APPLICATION_JSON)
                    .header(tokenHeaderName, token)
                    .content(objectMapper.writeValueAsString(editRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(result ->
                            assertTrue(result.getResolvedException()
                                    instanceof UserNotFoundException))
                    .andExpect(result ->
                            assertEquals(controllerAdvice.handleUserNotFoundException().getBody(),
                                    result.getResolvedException().getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateBoardAccessDenied() {
        //user is not a teacher

        try {
            when(boardService.createBoard(any(CreateBoardRequest.class), any(String.class)))
                    .thenThrow(new AccessDeniedException("Unauthorized action."));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(createUrl)
                    .contentType(APPLICATION_JSON)
                    .header(tokenHeaderName, token)
                    .content(objectMapper.writeValueAsString(createRequest)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(result ->
                            assertTrue(result.getResolvedException()
                                    instanceof AccessDeniedException))
                    .andExpect(result ->
                            assertEquals(controllerAdvice.handleAccessDeniedException().getBody(),
                                    result.getResolvedException().getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBoardsTest() {

        when(boardService.getBoards()).thenReturn(list);

        try {

            mockMvc.perform(get("/board/get")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("Board 1"))
                    .andExpect(jsonPath("$[0].id").value("1"));

            Mockito.verify(boardService, times(1)).getBoards();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void editBoardSuccessful() {

        try {
            when(boardService.updateBoard(any(EditBoardRequest.class),
                    any(String.class))).thenReturn(true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(editUrl)
                    .contentType(APPLICATION_JSON)
                    .header(tokenHeaderName, token)
                    .content(objectMapper.writeValueAsString(editRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("The board was successfully updated."));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    void testEditBoardUserNotFound() {
        //token is invalid

        try {
            when(boardService.updateBoard(any(EditBoardRequest.class), any(String.class)))
                    .thenThrow(new UserNotFoundException("This user does not exist."));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(editUrl)
                    .contentType(APPLICATION_JSON)
                    .header(tokenHeaderName, token)
                    .content(objectMapper.writeValueAsString(editRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(result ->
                            assertTrue(result.getResolvedException()
                                    instanceof UserNotFoundException))
                    .andExpect(result ->
                            assertEquals(controllerAdvice.handleUserNotFoundException().getBody(),
                                    result.getResolvedException().getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEditBoardAccessDenied() {
        //user is not a teacher

        try {
            when(boardService.updateBoard(any(EditBoardRequest.class), any(String.class)))
                    .thenThrow(new AccessDeniedException("Unauthorized action."));
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(editUrl)
                    .contentType(APPLICATION_JSON)
                    .header(tokenHeaderName, token)
                    .content(objectMapper.writeValueAsString(editRequest)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(result ->
                            assertTrue(result.getResolvedException()
                                    instanceof AccessDeniedException))
                    .andExpect(result ->
                            assertEquals(controllerAdvice.handleAccessDeniedException().getBody(),
                                    result.getResolvedException().getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void editBoardFailure() {
        //Can't edit the board if it is not in the database

        try {
            when(boardService.updateBoard(any(EditBoardRequest.class),
                    any(String.class))).thenReturn(false);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post(editUrl)
                    .contentType(APPLICATION_JSON)
                    .header(tokenHeaderName, token)
                    .content(objectMapper.writeValueAsString(editRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("This board does not exist."));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void testGetBoardByIdSuccessful() {

        when(boardService.getById(1)).thenReturn(board);

        try {

            mockMvc.perform(get("/board/get/1")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect(status().isOk());

            Mockito.verify(boardService, times(1)).getById(1);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void testGetBoardByIdFailure() {
        //Can't get the board if it is not in the database

        when(boardService.getById(2)).thenReturn(null);

        try {
            mockMvc.perform(get("/board/get/2")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("This board does not exist."));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void testisBoardLockedSuccessful() {

        when(boardService.getById(1)).thenReturn(board);

        try {

            mockMvc.perform(get("/board/checklocked/1")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect((ResultMatcher) new IsLockedResponse(false));

            Mockito.verify(boardService, times(1)).getById(1);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void testisBoardLockedFailure() {
        //Can't get the board if it is not in the database

        when(boardService.getById(2)).thenReturn(null);

        try {

            mockMvc.perform(get("/board/checklocked/2")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect((ResultMatcher) new IsLockedResponse());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}