package nl.tudelft.sem.group20.contentserver.controller;

import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditPostRequest;
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
     * @param request CreatePostRequest with information needed to create a new Post.
     * @return JSON file containing the ID of a new post.
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest request) {

        long newId = postService.createPost(request);
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
     * @param request CreatePostRequest with information needed to edit am existing Post.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> editPost(@RequestBody EditPostRequest request) {


        if (postService.updatePost(request)) {

            return new ResponseEntity<>("The post with ID: " + request.getPostId() + " has been "
                    + "updated",
                HttpStatus.OK);
        }
        return new ResponseEntity<>(
            "Post with ID: " + request.getPostId() + " could not be updated",
            HttpStatus.BAD_REQUEST);
    }


}
