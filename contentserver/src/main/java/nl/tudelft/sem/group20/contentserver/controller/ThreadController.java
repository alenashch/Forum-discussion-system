package nl.tudelft.sem.group20.contentserver.controller;

import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.requests.CreateBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditBoardThreadRequest;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/thread")
public class ThreadController {

    @Autowired
    private transient ThreadService threadService;


    /**
     * Create thread request.
     *
     * @param request - CreateEditBoardThreadRequest with information necessary to create new
     *                thread.
     * @return string
     */
    @PostMapping(path = "/create")
    public @ResponseBody
    ResponseEntity<String> createThread(CreateBoardThreadRequest request) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        long newId = threadService.createThread(request);
        if (newId == -1) {

            return new ResponseEntity<>("This thread could not be created, it may already exist",
                HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("A new thread with ID:" + newId + " has been created",
            HttpStatus.CREATED);
    }

    /**
     * Get thread request.
     *
     * @return JSON containing list of all threads.
     */
    @GetMapping("/get")
    @ResponseBody
    public List<BoardThread> getThreads() {

        return threadService.getThreads();
    }

    /**
     * Edit thread request.
     *
     * @param request - CreateEditBoardThreadRequest with information necessary to edit and existing
     *                thread.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> editThread(@RequestBody EditBoardThreadRequest request) {

        if (threadService.updateThread(request)) {

            return new ResponseEntity<>(
                "The thread with ID: " + request.getBoardThreadId() + " has been "
                    + "updated", HttpStatus.OK);
        }
        return new ResponseEntity<>(
            "Thread with ID: " + request.getBoardThreadId() + " could not be updated",
            HttpStatus.BAD_REQUEST);
    }

}
