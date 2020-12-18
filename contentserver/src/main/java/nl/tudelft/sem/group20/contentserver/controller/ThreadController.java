package nl.tudelft.sem.group20.contentserver.controller;

import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(path = "/thread")
public class ThreadController {

    @Autowired
    private transient ThreadService threadService;

    /**
     * Create thread request.
     *
     * @param token   - String containing the authentication token.
     * @param request - CreateEditBoardThreadRequest with information necessary to create new
     *                thread.
     * @return string
     */
    @PostMapping(path = "/create")
    public @ResponseBody
    ResponseEntity<String> createThread(@RequestHeader String token,
                                        @RequestBody CreateBoardThreadRequest request) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        long newId = threadService.createThread(token, request);

        return new ResponseEntity<>("A new thread with ID: " + newId + " has been created",
            HttpStatus.CREATED);
    }

    /**
     * Get thread request.
     *
     * @return JSON containing list of all threads.
     */
    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<?> getThreads() {
        return new ResponseEntity<>(threadService.getThreads(), HttpStatus.OK);
    }

    /**
     * Get thread request.
     *
     * @return JSON containing list of all threads.
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<?> getThread(@PathVariable long id) {
        return new ResponseEntity<>(threadService.getSingleThread(id), HttpStatus.OK);
    }

    /**
     * Edit thread request.
     *
     * @param token - String containing the authentication token.
     * @param request - CreateEditBoardThreadRequest with information necessary to edit and existing
     *                thread.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> editThread(@RequestHeader String token,
                                             @RequestBody EditBoardThreadRequest request) {

        threadService.updateThread(token, request);

        return new ResponseEntity<>("The thread with ID: " + request.getBoardThreadId()
                            + " has been " + "updated", HttpStatus.OK);

    }

    /**
     * Locks a thread. Only available for teachers.
     *
     * @param token - authentication token
     * @param id    - id of the thread to be locked.
     * @return ResponseEntity containing information about the result.
     */
    @PostMapping("/lock")
    @ResponseBody
    public ResponseEntity<String> lockThread(@RequestHeader String token,
                                             @RequestBody long id) {

        return new ResponseEntity<>(threadService.lockThread(token, id), HttpStatus.OK);
    }

    /**
     * Unlocks a thread. Only available for teachers.
     *
     * @param token - authentication token
     * @param id    - id of the thread to be locked.
     * @return ResponseEntity containing information about the result.
     */
    @PostMapping("/unlock")
    @ResponseBody
    public ResponseEntity<String> unlockThread(@RequestHeader String token,
                                               @RequestBody long id) {

        return new ResponseEntity<>(threadService.unlockThread(token, id), HttpStatus.OK);
    }

    /**
     * Get all threads for a given board id.
     *
     * @return JSON containing list of all threads that belong to a given board.
     */
    @GetMapping("/get/allthreads/{id}")
    @ResponseBody
    public ResponseEntity<?> getThreadsPerBoard(@PathVariable long id) {
        List<BoardThread> threadsPerBoard = threadService.getThreadsPerBoard(id);
        if(threadsPerBoard == null){
            return new ResponseEntity<>("There is no board with given Id", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(threadsPerBoard, HttpStatus.OK);
    }
}
