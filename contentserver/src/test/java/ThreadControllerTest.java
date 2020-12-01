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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.controller.ThreadController;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@WebMvcTest(ThreadController.class)
@ContextConfiguration(classes = ContentServer.class)
class ThreadControllerTest {

    @Autowired
    @MockBean
    transient ThreadService threadService;

    @Autowired
    transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;


    @Test
    void createThreadTest() {
        //BoardThread thread = createTestThread();
        when(threadService.createThread(Mockito.any())).thenReturn(11L);

        try {
            mockMvc.perform(post("/thread/create")
                    .contentType(APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(status().isCreated())
                    .andExpect(content().string("A new thread with ID:11 has been created"))
                    .andReturn();
            Mockito.verify(threadService, times(1)).createThread(Mockito.any());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void failedThreadCreationTest() {
        //BoardThread thread = createTestThread();
        when(threadService.createThread(Mockito.any())).thenReturn(-1L);

        try {

            mockMvc.perform(post("/thread/create")
                    .contentType(APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(content()
                            .string("This thread could not be created, it may already exist"))
                    .andReturn();

            Mockito.verify(threadService, times(1)).createThread(Mockito.any());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void getThreadTest() {
        List<BoardThread> list = Collections.singletonList(createTestThread());
        when(threadService.getThreads()).thenReturn(list);

        try {
            mockMvc.perform(get("/thread/get")
                    .contentType(APPLICATION_JSON)).andDo(print())
                    .andExpect(jsonPath("$[0].threadTitle").value("Title"))
                    .andExpect(jsonPath("$[0].id").value("123"));

            Mockito.verify(threadService, times(1)).getThreads();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void editThreadTest() {
        BoardThread thread = createTestThread();
        when(threadService.updateThread(thread)).thenReturn(true);

        try {
            mockMvc.perform(post("/thread/edit")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(thread)).accept(APPLICATION_JSON))
                    .andDo(print()).andExpect(status().isOk());
            //.andExpect((ResultMatcher) jsonPath("$.success").value(true));


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Test
    void failedThreadEditTest() {
        BoardThread thread = createTestThread();
        when(threadService.updateThread(thread)).thenReturn(false);


        try {
            mockMvc.perform(post("/thread/edit")
                    .contentType(APPLICATION_JSON)
                    .content(createJsonRequest(thread)).accept(APPLICATION_JSON))
                    .andDo(print()).andExpect(status().is4xxClientError());
            //.andExpect((ResultMatcher) jsonPath("$.success").value(true));


        } catch (Exception e) {

            e.printStackTrace();
        }

    }



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

}
