package nl.tudelft.sem.group20.contentserver.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.services.PostService;
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
@RequestMapping("/post")
public class PostController {

    @Autowired
    private transient PostService postService;

    /**
     * Create post request.
     *
     * @param post - new post to be created.
     * @return JSON file containing the ID of a new post.
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public ResponseEntity<String> createPost(@RequestBody Post post) {

        long newId = postService.createPost(post);
        if (newId == -1) {

            return new ResponseEntity<>("This post could not be created, it may already exist",
                HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("A new post with ID: " + newId + " has been created",
            HttpStatus.CREATED);
    }

    /**
     * Get post request.
     *
     * @return JSON containing list of all posts.
     */
    @GetMapping("/get")
    @ResponseBody
    public List<Post> getPosts() {

        return postService.getPosts();
    }

    /**
     * Edit post request.
     *
     * @param post - Post to be edited. With the old ID and new parameters to be set.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> editPost(@RequestBody Post post) {

        if (postService.updatePost(post)) {

            return new ResponseEntity<>("The post with ID: " + post.getId() + " has been updated",
                HttpStatus.OK);
        }
        return new ResponseEntity<>("Post with ID: " + post.getId() + "could not be updated",
            HttpStatus.BAD_REQUEST);
    }


}
