package nl.tudelft.sem.template;

import static org.assertj.core.api.Assertions.assertThat;
import nl.tudelft.sem.template.model.Board;
import nl.tudelft.sem.template.repository.BoardRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {
    transient Board board1;
    transient long id1;
    transient String name1;
    transient String description1;
    transient boolean locked1;

    transient Board board2;
    transient long id2;
    transient String name2;
    transient String description2;
    transient boolean locked2;

    transient List<Board> boardsList;

    transient BoardService boardService;

    @MockBean
    transient BoardRepository boardRepository;

    @BeforeEach
    void initialize() {
        id1 = 1;
        name1 = "name1";
        description1 = "First description";
        locked1 = false;

        id2 = 2;
        name2 = "name2";
        description2 = "Second description";
        locked2 = false;

        board1 = new Board(id1, name1, description1, locked1);
        board2 = new Board(id2, name2, description2, locked2);


        boardsList = new ArrayList<>();
        boardsList.add(board1);
        boardsList.add(board2);

        boardRepository = Mockito.mock(BoardRepository.class);
        Mockito.when(boardRepository.findAll())
                .thenReturn(boardsList);
        /*Mockito.when(postRepository.saveAndFlush(any(Post.class)))
                .then(returnsFirstArg());
        Mockito.when(postRepository.getById(2))
                .thenReturn(Optional.of(demoPost2));*/

        boardService = new BoardService(boardRepository);
    }

    @Test
    void testGetPosts() {
        assertThat(boardService.getBoards()).hasSize(boardsList.size()).hasSameElementsAs(boardsList);
    }




}
