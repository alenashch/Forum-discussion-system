package nl.tudelft.sem.template;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.sem.template.BoardService;
import nl.tudelft.sem.template.model.Board;
import nl.tudelft.sem.template.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    transient Board board3;
    transient long id3;
    transient String name3;
    transient String description3;
    transient boolean locked3;

    transient List<Board> boardsList;

    transient BoardService boardService;

    @MockBean
    transient BoardRepository boardRepository;

    @BeforeEach
    void initialize() {
        id1 = 1;
        name1 = "name 1";
        description1 = "description 1";
        locked1 = false;

        id2 = 2;
        name2 = "name 2";
        description2 = "description 2";
        locked2 = false;

        id3 = 3;
        name3 = "name 3";
        description3 = "description 3";
        locked3 = false;

        board1 = new Board(id1, name1, description1, locked1);
        board2 = new Board(id2, name2, description2, locked2);
        board3 = new Board(id3, name3, description3, locked3);


        boardsList = new ArrayList<>();
        boardsList.add(board1);
        boardsList.add(board2);

        boardRepository = Mockito.mock(BoardRepository.class);
        Mockito.when(boardRepository.findAll())
                .thenReturn(boardsList);


        boardService = new BoardService(boardRepository);
    }

    @Test
    public void testGetBoards() {
        assertThat(boardService.getBoards()).hasSize(boardsList.size()).hasSameElementsAs(boardsList);
    }

    @Test
    public void testCreateBoard() {
        assertEquals(boardService.createBoard(board3), board3.getId());
        verify(boardRepository, times(1)).saveAndFlush(board3);
    }

}
