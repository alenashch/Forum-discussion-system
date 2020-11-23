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

    public long createBoard(Board newBoard) {
        if (boardRepository.getById(newBoard.getId()).isPresent()) {
            return -1;
        }

        boardRepository.saveAndFlush(newBoard);
        return newBoard.getId();
    }

    public boolean updateBoard(Board updatedBoard) {
        if (boardRepository.getById(updatedBoard.getId()).isEmpty()) {
            return false;
        }

        boardRepository.saveAndFlush(updatedBoard);
        return true;
    }
}
