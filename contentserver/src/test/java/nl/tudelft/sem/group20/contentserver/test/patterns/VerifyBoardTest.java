package nl.tudelft.sem.group20.contentserver.test.patterns;

import nl.tudelft.sem.group20.contentserver.ContentServer;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.CheckRequest;
import nl.tudelft.sem.group20.contentserver.architecturepatterns.VerifyBoard;
import nl.tudelft.sem.group20.contentserver.controller.ThreadController;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@WebMvcTest(VerifyBoard.class)
@ContextConfiguration(classes = ContentServer.class)
public class VerifyBoardTest {

    transient VerifyBoard vb;

    transient CheckRequest cr;

    @MockBean
    transient RestTemplate rt;

    transient IsLockedResponse isUnlocked;


    @BeforeEach
    void initialize() {

        rt = mock(RestTemplate.class);
        cr = new CheckRequest("token", 1, rt);

        isUnlocked = new IsLockedResponse(false);

        vb = new VerifyBoard();
    }

    @Test
    void firstTest() {
        when(cr.restTemplate.getForObject(anyString(),
                Mockito.eq(IsLockedResponse.class)))
                .thenReturn(isUnlocked);

        boolean ans;
        try {

            when(vb.handle(cr)).thenReturn(true);

            ans = vb.handle(cr);
            System.out.println(ans);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Test
    void secondTest() {
        when(cr.restTemplate.getForObject(anyString(),
                Mockito.eq(IsLockedResponse.class)))
                .thenReturn(isUnlocked);


        try {
            when(vb.handle(cr)).thenReturn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
