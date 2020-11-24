package nl.tudelft.sem.group20.userserver.controller;

import nl.tudelft.sem.group20.userserver.model.BoardThread;
import nl.tudelft.sem.group20.userserver.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/thread")
public class ThreadController {
    @Autowired
    private transient ThreadRepository threadRepository;

    /**
     * Post request.
     *
     * @param creator the creator of thread
     * @return string
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody String addNewThread(@RequestParam String creator) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        BoardThread n = new BoardThread();
        n.setLocked(false);
        n.setThreadTitle("Creation 101");
        n.setStatement("Am I alive");
        n.setLocked(false);
        n.setThreadCreator(creator);
        threadRepository.save(n);
        return "Saved";
    }

    /**
     * Get request.
     *
     * @return response
     */
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<BoardThread> getAllThreads() {
        // This returns a JSON or XML with the users
        return threadRepository.findAll();
    }

}
