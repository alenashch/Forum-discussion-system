package nl.tudelft.sem.group20.boardserver.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.boardserver.BoardServer;
import nl.tudelft.sem.group20.boardserver.controllers.BoardController;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
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

    @Test
    void createPostTest() {

        Board board = new Board(123, "boardy", "abc", false);
        when(boardService.createBoard(board)).thenReturn(1L);

        try {
            MvcResult result = mockMvc.perform(post("/board/create")
                .contentType(APPLICATION_JSON)
                .content(createJsonRequest(board)))
                .andDo(print())
                .andReturn();

            String json = result.getResponse().getContentAsString();
            Long response = new ObjectMapper().readValue(json, Long.class);

            Assertions.assertEquals(response, 1L);
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
                .andReturn();

            String json = result.getResponse().getContentAsString();

            System.out.println(json);

            Board responseBoard = new ObjectMapper().readValue(json, Board.class);

            Assertions.assertEquals(list.get(0), responseBoard);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void editBoardTest() {

        Board board = new Board(123, "boardy", "abc", false);

        given(boardService.updateBoard(any(Board.class))).willReturn(true);
        try {

            MvcResult result = mockMvc.perform(post("/post/edit")
                .contentType(APPLICATION_JSON)
                .content(createJsonRequest(board)))
                .andReturn();

            String json = result.getResponse().getContentAsString();

            Boolean response = new ObjectMapper().readValue(json, Boolean.class);

            Assertions.assertTrue(response);

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