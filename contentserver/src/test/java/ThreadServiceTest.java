import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;


import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.services.PostService;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(PostService.class)
@ContextConfiguration(classes = ContentServer.class)
public class ThreadServiceTest {

    @MockBean
    transient ThreadRepository threadRepository;

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
        threads.add(demoThread1); threads.add(demoThread2); threads.add(demoThread3);

        threadRepository = Mockito.mock(ThreadRepository.class);
        Mockito.when(threadRepository.findAll())
                .thenReturn(threads);
        Mockito.when(threadRepository.saveAndFlush(any(BoardThread.class)))
                .then(returnsFirstArg());

    }
}
















