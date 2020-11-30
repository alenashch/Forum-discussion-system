package nl.tudelft.sem.group20.boardserver.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.tudelft.sem.group20.boardserver.services.BoardService;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
     * Test Request.
     *
     * @return response
     */
    //@GetMapping(path = "/all")
    @RequestMapping("/hello")
    public @ResponseBody String getAllThreads() {
        return "hello";
    }

}