package nl.tudelft.sem.group20.boardserver.services;

import java.util.List;
import java.util.Optional;

import nl.tudelft.sem.group20.boardserver.repos.BoardRepository;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final transient BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoards() {
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
        Board.checkCreationTime(newBoard);
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


    public Board getById(long id){
        if (boardRepository.getById(id).isEmpty()) {
            return null;
        }
        return boardRepository.getOne(id);
    }
    
    
    
}
