package nl.tudelft.sem.template.service;

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

    /**
     * Creates a Board in the database.
     *
     * @param newBoard - a new board to add.
     * @return -1 if the Board already exists in the database, or the id of the newly created board
     *      if creation was successful.
     */
    public long createBoard(Board newBoard) {
        if (boardRepository.getById(newBoard.getId()).isPresent()) {
            return -1;
        }
        boardRepository.saveAndFlush(newBoard);
        return newBoard.getId();
    }

    /**
     * Updates a Board in the database.
     *
     * @param updatedBoard - a board to be updated.
     * @return false if the Board does not exist in the database, true otherwise.
     */
    public boolean updateBoard(Board updatedBoard) {
        if (boardRepository.getById(updatedBoard.getId()).isEmpty()) {
            return false;
        }

        boardRepository.saveAndFlush(updatedBoard);
        return true;
    }
}