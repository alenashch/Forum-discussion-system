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
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.controller.ThreadController;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
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
@WebMvcTest(ThreadController.class)
@ContextConfiguration(classes = ContentServer.class)
class ThreadControllerTest {

    private final transient String token = "1";
    private final transient String tokenName = "token";

    @Autowired
    @MockBean
    transient ThreadService threadService;

    @Autowired
    transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    private transient TestBoardThreadBuilder builder;


    @BeforeEach
    void setUp() {

        builder = new TestBoardThreadBuilder();
    }


    @Test
    void createThreadTest() {
        //BoardThread thread = createTestThread();

        CreateBoardThreadRequest createThreadReq = builder.createBoardThreadRequest();

        when(threadService.createThread(anyString(),
                any(CreateBoardThreadRequest.class)))
                .thenReturn(builder.getBoardThreadId());

        try {
            mockMvc.perform(post("/thread/create")
                    .contentType(APPLICATION_JSON)
                    .header(tokenName, token)
                    .content(objectMapper.writeValueAsString(createThreadReq)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string("A new thread with ID: "
                            + builder.getBoardThreadId()
                            + " has been created"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getBoardThreadTest() {

        builder.setBoardId(1L);
        BoardThread board = builder.makeBoardThread();
        when(threadService.getSingleThread(builder.getBoardId())).thenReturn(board);

        try {

            MvcResult result = mockMvc.perform(get("/thread/get/1"))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(objectMapper.writeValueAsString(board),
                    result.getResponse().getContentAsString());
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    void getAllThreadsTest() {
        List<BoardThread> list = Collections.singletonList(builder.makeBoardThread());
        when(threadService.getThreads()).thenReturn(list);

        try {
            mockMvc.perform(get("/thread/get")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect(jsonPath("$[0].threadTitle").value(builder.getTitle()))
                    .andExpect(jsonPath("$[0].id").value(builder.getBoardThreadId()));

            Mockito.verify(threadService, times(1)).getThreads();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    @Test
    void editThreadTest() {

        EditBoardThreadRequest editRequest = builder.editBoardThreadRequest();

        try {
            mockMvc.perform(post("/thread/edit")
                    .contentType(APPLICATION_JSON)
                    .header(tokenName, token)
                    .content(objectMapper.writeValueAsString(editRequest))
                    .accept(APPLICATION_JSON))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string("The thread with ID: " + builder.getBoardThreadId()
                            + " has been updated"));


        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    @Test
    void lockThreadTest() {

        try {

            mockMvc.perform(post("/thread/lock/1")
                    .contentType(APPLICATION_JSON)
                    .header(tokenName, token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(status().isOk())
                    .andReturn();

            Mockito.verify(threadService, times(1))
                    .lockThread(token, 1);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void unlockThreadTest() {

        try {

            mockMvc.perform(post("/thread/unlock/1")
                    .contentType(APPLICATION_JSON)
                    .header(tokenName, token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(status().isOk())
                    .andReturn();

            Mockito.verify(threadService, times(1))
                    .unlockThread(token, 1);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void getAllThreadsFromBoardTest() {

        try {

            mockMvc.perform(post("/thread/unlock/1")
                    .contentType(APPLICATION_JSON)
                    .header(tokenName, token))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(status().isOk())
                    .andReturn();

            Mockito.verify(threadService, times(1))
                    .unlockThread(token, 1);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    public void getThreadsOfBoardTest() {

        List<BoardThread> list = Collections.singletonList(builder.makeBoardThread());
        when(threadService.getThreadsPerBoard(1)).thenReturn(list);

        builder.setBoardId(1L);
        try {

            mockMvc.perform(get("/thread/get/allthreads/1")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect(jsonPath("$[0].threadTitle").value(builder.getTitle()))
                    .andExpect(jsonPath("$[0].id").value(builder.getBoardThreadId()));

            Mockito.verify(threadService, times(1)).getThreadsPerBoard(1);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*




    private BoardThread createTestThread() {

        LocalDateTime time = LocalDateTime.now();
        return new  BoardThread(123L, "Title", "Question", "Jay",
                time, false);
    }

    private String createJsonRequest(BoardThread thread) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(thread);
    }

    */

}
