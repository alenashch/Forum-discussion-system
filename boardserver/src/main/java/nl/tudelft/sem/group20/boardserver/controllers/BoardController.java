package nl.tudelft.sem.group20.boardserver.controllers;

import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.requests.CreateBoardRequest;
import nl.tudelft.sem.group20.boardserver.requests.EditBoardRequest;
import nl.tudelft.sem.group20.boardserver.services.BoardService;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import nl.tudelft.sem.group20.exceptions.UserNotFoundException;
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

    private transient String boardNotInDb = "This board does not exist.";

    /**
     * Create request request.
     *
     * @param request - new request to be created.
     * @return JSON file containing the ID of a new post.
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createBoard (@RequestBody CreateBoardRequest request, @RequestHeader("user-token") String token) throws UserNotFoundException, AccessDeniedException {
        long assignedId = boardService.createBoard(request, token);

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
     * Edit request request.
     *
     * @param request - Board to be edited. With the old ID and new parameters to be set.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editBoard(@RequestBody EditBoardRequest request, @RequestHeader("user-token") String token) throws AccessDeniedException, UserNotFoundException {
        boolean updatedSucceeded = boardService.updateBoard(request, token);

        if (!updatedSucceeded) {
            return new ResponseEntity<>(boardNotInDb, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("The board was successfully updated.", HttpStatus.OK);
        }
    }

    /**
     *
     * @param id - id of a board.
     * @return JSON containing a board.
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<?> getBoardById(@PathVariable long id) {
         Board board = boardService.getById(id);
         if(board == null){
             return new ResponseEntity<>(boardNotInDb, HttpStatus.BAD_REQUEST);
         }
            return new ResponseEntity<>(board, HttpStatus.OK);
    }

    /**
     *
     * @param id - id of a board for which we want to know
     *           whether it is locked or not.
     * @return JSON containing true if locked, false otherwise.
     */
    @GetMapping("/checklocked/{id}")
    @ResponseBody
    public IsLockedResponse isBoardLocked(@PathVariable long id) {
        Board board = boardService.getById(id);
        if(board == null){
            return new IsLockedResponse();
        }
        return new IsLockedResponse(board.isLocked());
    }

}
