package nl.tudelft.sem.group20.boardserver.controllers;

import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
import nl.tudelft.sem.group20.exceptions.UserNotFoundException;
import nl.tudelft.sem.group20.shared.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

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
    public ResponseEntity<?> createBoard (@RequestBody Board board, @RequestHeader("user-token") String token) throws UserNotFoundException, AccessDeniedException {
        long assignedId = boardService.createBoard(board, new AuthRequest(token));

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
    public ResponseEntity<?> editBoard(@RequestBody Board board, @RequestHeader("user-token") String token) throws AccessDeniedException, UserNotFoundException {
        boolean updatedSucceeded = boardService.updateBoard(board, new AuthRequest(token));

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