package nl.tudelft.sem.group20.boardserver.controllers;

import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        long assignedId = boardService.createBoard(board);

        //board with this id already exists, no board was created
        if (assignedId == -1) {
            return new ResponseEntity<>("A board with this id already exists.",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("A new board with ID: " + assignedId + " has been created",
                HttpStatus.CREATED
        );
    }

    /**
     * Get board request.
     *
     * @return JSON containing list of all posts.
     */
    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<?> getBoards() {
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }

    /**
     * Edit board request.
     *
     * @param board - Board to be edited. With the old ID and new parameters to be set.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editBoard(@RequestBody Board board) {
        boolean updatedSucceeded = boardService.updateBoard(board);

        if (!updatedSucceeded) {
            return new ResponseEntity<>("This board does not exist.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("The board was successfully updated.", HttpStatus.OK);
        }
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