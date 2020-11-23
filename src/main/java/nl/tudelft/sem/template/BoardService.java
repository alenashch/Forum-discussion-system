package nl.tudelft.sem.template;

import nl.tudelft.sem.template.model.Board;
import nl.tudelft.sem.template.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final transient BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoards(){
        return boardRepository.findAll();
    }


}
