import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@AutoConfigureMockMvc
@WebMvcTest(ThreadService.class)
@ContextConfiguration(classes = ContentServer.class)
public class ThreadServiceTest {

    @MockBean
    transient ThreadRepository threadRepository;

    transient ThreadService threadService;

    transient BoardThread demoThread1;
    transient BoardThread demoThread2;
    transient BoardThread demoThread3;
    transient List<BoardThread> threads;

    @BeforeEach
    void initialize() {
        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;

        String title1 = "title1";
        String title2 = "title2";
        String title3 = "title3";

        String ques1 = "question1";
        String ques2 = "question2";
        String ques3 = "question3";

        String creator1 = "person1";
        String creator2 = "person2";
        String creator3 = "person3";

        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.now();
        LocalDateTime time3 = LocalDateTime.now();

        boolean locked1 = false;
        boolean locked2 = true;
        boolean locked3 = false;

        demoThread1 = new BoardThread(id1, title1, ques1, creator1, time1, locked1);
        demoThread2 = new BoardThread(id2, title2, ques2, creator2, time2, locked2);
        demoThread3 = new BoardThread(id3, title3, ques3, creator3, time3, locked3);

        threads = new ArrayList<>();
        threads.add(demoThread1);
        threads.add(demoThread2);
        threads.add(demoThread3);

        threadRepository = Mockito.mock(ThreadRepository.class);
        Mockito.when(threadRepository.findAll())
                .thenReturn(threads);
        Mockito.when(threadRepository.saveAndFlush(any(BoardThread.class)))
                .then(returnsFirstArg());
        Mockito.when(threadRepository.getById(2))
                .thenReturn(Optional.of(demoThread2));

        threadService = new ThreadService(threadRepository);
    }

    @Test
    void testGetThreads() {
        assertThat(threadService.getThreads())
                .hasSize(threads.size())
                .hasSameElementsAs(threads);
    }

    @Test
    void testCreateThread() {
        assertEquals(threadService.createThread(demoThread3), demoThread3.getId());

        verify(threadRepository, times(1)).saveAndFlush(demoThread3);
    }

    @Test
    void testFailedCreateThread() {
        assertEquals(-1, threadService.createThread(demoThread2));
        verify(threadRepository, times(0)).saveAndFlush(any(BoardThread.class));

    }

    @Test
    void updateThread() {
        demoThread2.setThreadTitle("Footy");
        assertTrue(threadService.updateThread(demoThread2));
        verify(threadRepository, times(1)).saveAndFlush(demoThread2);
    }

    @Test
    void failedUpdateThread() {
        when(threadRepository.getById(3))
                .thenReturn(Optional.empty());

        assertFalse(threadService.updateThread(demoThread3));
        verify(threadRepository, times(0)).saveAndFlush(any(BoardThread.class));
    }


}
















