package nl.tudelft.sem.group20.contentserver.controller;

import java.util.List;

import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param thread the new thread to be added
     * @return string
     */
    @PostMapping(path = "/create")
    public @ResponseBody
    ResponseEntity<String> createThread(BoardThread thread) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        long newId = threadService.createThread(thread);
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
     * @param thread thread to be edited. With the old ID and new parameters to be set.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> editThread(@RequestBody BoardThread thread) {

        if (threadService.updateThread(thread)) {

            return new ResponseEntity<>("The thread with ID: " + thread.getId() + " has been "
                + "updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Thread with ID: " + thread.getId() + " could not be updated",
            HttpStatus.BAD_REQUEST);
    }

}
