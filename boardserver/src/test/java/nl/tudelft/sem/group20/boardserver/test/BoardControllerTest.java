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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.boardserver.BoardServer;
import nl.tudelft.sem.group20.boardserver.controllers.BoardController;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    @BeforeEach
    void initialize() {
        board = new Board(1, "Board 1", "description", false);
        list = Collections.singletonList(new Board(1, "Board 1", "description", false));
    }


    @Test
    void testCreateBoardSuccessful() {

        when(boardService.createBoard(board)).thenReturn(1L);

        try {
            mockMvc.perform(post("/board/create")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(board)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string("A new board with ID: 1 has been created"));

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    //Board is already in the database
    @Test
    void testCreateBoardFailure() {

        when(boardService.createBoard(board)).thenReturn(-1L);

        try {
            mockMvc.perform(post("/board/create")
                    .contentType(APPLICATION_JSON)
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

        when(boardService.updateBoard(board)).thenReturn(true);

        try {
            mockMvc.perform(post("/board/edit")
                    .contentType(APPLICATION_JSON)
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

        when(boardService.updateBoard(board)).thenReturn(false);

        try {
            mockMvc.perform(post("/board/edit")
                    .contentType(APPLICATION_JSON)
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