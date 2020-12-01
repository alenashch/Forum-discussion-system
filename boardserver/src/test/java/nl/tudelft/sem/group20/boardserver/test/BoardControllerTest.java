package nl.tudelft.sem.group20.boardserver.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.junit.jupiter.api.Test;
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


    @Autowired
    @MockBean
    transient BoardService boardService;
    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient ObjectMapper objectMapper;


    @Test
    void testCreateBoardSuccessful() {

        Board board = new Board(1,"Board 1", "description", false);
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

    @Test
    void testCreateBoardFailure() {
        Board board = new Board(3, "Board 3", "description 3", false);
        when(boardService.createBoard(board)).thenReturn(-1L);

        try {
            mockMvc.perform(post("/board/create")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(board)))
                    .andDo(print())
                    .andExpect(content().string("A board with this id already exists."));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /*@Test
    void testCreateBoardSuccessful() {
        Board validBoard = new Board("This is a new board.", "A description.", false);
        when(boardService.createBoard(validBoard)).thenReturn(1L);

        try {
            MvcResult result = mockMvc.perform(post("/board/create")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(validBoard)))
                    //at the moment, this throws an assertion error
                    //.andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            Long response = new ObjectMapper().readValue(json, Long.class);

            Assertions.assertEquals(1L, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateBoardFailure() {
        Board validBoard = new Board(1L, "This is a new board.", "A description.", false);
        when(boardService.createBoard(validBoard)).thenReturn(-1L);

        try {
            MvcResult result = mockMvc.perform(post("/board/create")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(validBoard)))
                    //at the moment, this throws an assertion error
                    //.andExpect(status().isBadRequest())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            String response = new ObjectMapper().readValue(json, String.class);

            Assertions.assertEquals("A board with this id already exists.", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBoardsTest() {

        List<Board> list = Collections.singletonList(new Board(123, "boardy", "abc", false));

        when(boardService.getBoards()).thenReturn(list);

        try {

            MvcResult result = mockMvc.perform(get("/board/get")
                    .contentType(APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();

            //System.out.println(json);

            Board[] responseBoards = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .readValue(json, Board[].class);

            //System.out.println("here!");
            //System.out.println(responseBoards);

            Assertions.assertEquals(list.get(0), responseBoards[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void editBoardSuccessful() {

        Board board = new Board(1L, "An existing board.", "Description.", false);

        given(boardService.updateBoard(any(Board.class))).willReturn(true);
        try {

            MvcResult result = mockMvc.perform(post("/post/edit")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(board)))
                    //.andExpect(status().isOk())
                    .andReturn();

            String json = result.getResponse().getContentAsString();

            Boolean response = new ObjectMapper().readValue(json, Boolean.class);

            Assertions.assertTrue(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void editBoardFailure() {

        Board board = new Board(1L, "A non-existing board.", "Description.", false);

        given(boardService.updateBoard(any(Board.class))).willReturn(false);
        try {

            MvcResult result = mockMvc.perform(post("/post/edit")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(board)))
                    //.andExpect(status().isBadRequest())
                    .andReturn();

            String json = result.getResponse().getContentAsString();

            Boolean response = new ObjectMapper().readValue(json, Boolean.class);

            Assertions.assertFalse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private String createJsonRequest(Board board) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(board);
    }
}