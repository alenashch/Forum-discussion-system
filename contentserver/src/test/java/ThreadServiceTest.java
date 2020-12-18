import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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

    private transient TestThreadPostBuilder builder;

    @BeforeEach
    void initialize() {
        long id1 = 1L;
        long id2 = 2L;
        long id3 = 3L;

        String creatorName = "Bob";

        String title1 = "title1";
        String title2 = "title2";
        String title3 = "title3";

        String ques1 = "question1";
        String ques2 = "question2";
        String ques3 = "question3";

        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.now();
        LocalDateTime time3 = LocalDateTime.now();

        boolean locked1 = false;
        boolean locked2 = true;
        boolean locked3 = false;

        demoThread1 = new BoardThread(id1, title1, ques1, creatorName ,time1, locked1,
                1, false);

        demoThread2 = new BoardThread(id2, title2, ques2, creatorName ,time2, locked2,
                1, false);
        demoThread3 = new BoardThread(id3, title3, ques3, creatorName, time3, locked3,
                1, false);

        threads = new ArrayList<>();

        threads.add(demoThread1);
        threads.add(demoThread2);
        threads.add(demoThread3);


        builder = new TestThreadPostBuilder();
        threadRepository = Mockito.mock(ThreadRepository.class);
        Mockito.when(threadRepository.findAll())
            .thenReturn(threads);
        Mockito.when(threadRepository.saveAndFlush(any(BoardThread.class)))
            .then(returnsFirstArg());
        Mockito.when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.of(demoThread2));

        threadService = new ThreadService(threadRepository);


    }

    @Test
    void testGetThreads() {
        assertThat(threadService.getThreads())
            .hasSize(threads.size())
            .hasSameElementsAs(threads);
    }

    /*
    @Test //fix this test
    void testCreateThread() {
        //builder.setThreadId(3L);
        assertEquals(0, threadService.createThread("dwdwdw",
        builder.createTestCreateBoardThreadRequest())
        );

        verify(threadRepository, times(1)).saveAndFlush(any(BoardThread.class));
    }


    @Test
    void testFailedCreateThread() {
        //builder.setThreadId(3L);
        when(threadRepository.getById(anyLong())).thenReturn(Optional.empty());
        assertEquals(-1, threadService.createThread(builder.createTestCreateBoardThreadRequest
            ()));
        verify(threadRepository, times(0)).saveAndFlush(any(BoardThread.class));

    }
     */

    /*
    @Test
    void updateThread() {
        builder.setTitle("Footy");
        when(threadRepository.getById(anyLong()))
            .thenReturn(Optional.of(builder.createTestBoardThread()));
        assertTrue(threadService.updateThread(builder.createTestEditBoardThreadRequest()));
        verify(threadRepository, times(1)).saveAndFlush(any(BoardThread.class));
    }

    @Test
    void failedUpdateThread() {
        when(threadRepository.getById(builder.getThreadId()))
            .thenReturn(Optional.empty());

        assertFalse(threadService.updateThread(builder.createTestEditBoardThreadRequest()));
        verify(threadRepository, times(0)).saveAndFlush(any(BoardThread.class));
    }*/


}
















