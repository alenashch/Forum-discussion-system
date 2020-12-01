import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.controller.ThreadController;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import org.apache.http.protocol.HTTP;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@AutoConfigureMockMvc
@WebMvcTest(ThreadController.class)
@ContextConfiguration(classes = ContentServer.class)
class ThreadControllerTest {

    @Autowired
    @MockBean
    transient ThreadService threadService;
    @Autowired
    transient MockMvc mockMvc;

    transient BoardThread thread;
    transient LocalDateTime time;

    @BeforeEach
    void setup() {
        time = LocalDateTime.now();
        thread = new BoardThread(0L, "Title", "Question", "Jay",
                time, false );

    }

    @Test
    void createThreadTest() {
        when(threadService.createThread(thread)).thenReturn(2L);

        try {

            MvcResult result = mockMvc.perform(post("/thread/create")
                    .contentType(APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();

            String json = result.getResponse().getContentAsString();
            System.out.println("here" + json);

            //var ans = new ObjectMapper().readValue(json, ResponseEntity.class);
            //Assertions.assertEquals(1, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void getThreadTest() {

       List<BoardThread> list = Collections.singletonList(thread);

    }

}
