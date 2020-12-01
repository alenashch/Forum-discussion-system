package nl.tudelft.sem.group20.boardserver.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.tudelft.sem.group20.boardserver.services.BoardService;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private transient BoardService boardService;

    /**
     * Create board request.
     *
     * @param board - new board to be created.
     * @return JSON file containing the ID of a new post.
     */
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Long> createBoard(@RequestBody Board board) {

        return Collections.singletonMap("ID", boardService.createBoard(board));
    }

    /**
     * Get board request.
     *
     * @return JSON containing list of all posts.
     */
    @GetMapping("/get")
    @ResponseBody
    public List<Board> getBoards() {

        return boardService.getBoards();
    }

    /**
     * Edit board request.
     *
     * @param board - Board to be edited. With the old ID and new parameters to be set.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Boolean> editBoard(@RequestBody Board board) {

        return Collections.singletonMap("success", boardService.updateBoard(board));
    }

    /**
     *
     * @param id - id of a board to be retrieved.
     * @return JSON containing a board.
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<?> getBoardById(@PathVariable long id) {
         Board board = boardService.getById(id);
         if(board == null){
             return new ResponseEntity<>("This board does not exist.", HttpStatus.BAD_REQUEST);
         }
            return new ResponseEntity<>(board, HttpStatus.OK);
    }

}