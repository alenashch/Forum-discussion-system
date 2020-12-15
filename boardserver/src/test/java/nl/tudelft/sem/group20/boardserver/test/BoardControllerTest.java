package nl.tudelft.sem.group20.boardserver.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.boardserver.BoardServer;
import nl.tudelft.sem.group20.boardserver.controllers.BoardController;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(BoardController.class)
@ContextConfiguration(classes = BoardServer.class)
class BoardControllerTest {

    transient Board board;
    transient List<Board> list;

    @Autowired
    @MockBean
    transient BoardService boardService;
    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient ObjectMapper objectMapper;

    transient String token;

    @BeforeEach
    void initialize() {
        token = "A token.";

        board = new Board(1, "Board 1", "description", false, "user");
        list = Collections.singletonList(board);
    }


    @Test
    void testCreateBoardSuccessful() {

        try {
            when(boardService.createBoard(board, new AuthRequest(token))).thenReturn(board.getId());
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/board/create")
                    .contentType(APPLICATION_JSON)
                    .param(token)
                    .content(objectMapper.writeValueAsString(board)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string(
                            "A new board with ID: " + board.getId() + " has been created"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //Board is already in the database
    @Test
    void testCreateBoardFailure() {

        try {
            when(boardService.createBoard(board, new AuthRequest(token))).thenReturn(-1L);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/board/create")
                    .contentType(APPLICATION_JSON)
                    .param(token)
                    .content(objectMapper.writeValueAsString(board)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("A board with this id already exists."));

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
            when(boardService.updateBoard(board, new AuthRequest(token))).thenReturn(true);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/board/edit")
                    .contentType(APPLICATION_JSON)
                    .param(token)
                    .content(objectMapper.writeValueAsString(board)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("The board was successfully updated."));

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    //Can't edit the board if it is not in the database
    @Test
    void editBoardFailure() {

        try {
            when(boardService.updateBoard(board, new AuthRequest(token))).thenReturn(false);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        try {
            mockMvc.perform(post("/board/edit")
                    .contentType(APPLICATION_JSON)
                    .param(token)
                    .content(objectMapper.writeValueAsString(board)))
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

    //Can't get the board if it is not in the database
    @Test
    void testGetBoardByIdFailure() {

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



    private String createJsonRequest(Board board) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(board);
    }
}