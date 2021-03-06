package nl.tudelft.sem.group20.contentserver.controller;

import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditPostRequest;
import nl.tudelft.sem.group20.contentserver.services.PostService;
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
    public ResponseEntity<String> createPost(@RequestHeader String token,
                                             @RequestBody CreatePostRequest request)
        throws Exception {

        Post newPost = postService.createPost(token, request);

        return new ResponseEntity<>("A new post with ID: " + newPost.getId() + " has been created",
            HttpStatus.CREATED);
    }

    /**
     * Get post request.
     *
     * @return JSON responseEntity containing list of all posts.
     */
    @GetMapping("/get")
    @ResponseBody
    public ResponseEntity<?> getPosts() {

        return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
    }

    /**
     * Edit post request.
     *
     * @param request CreatePostRequest with information needed to edit am existing Post.
     * @return JSON containing a boolean signifying success.
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> editPost(@RequestHeader String token,
                                           @RequestBody EditPostRequest request) throws Exception {


        postService.updatePost(token, request);

        return new ResponseEntity<>("The post with ID: " + request.getPostId() + " has been "
            + "updated",
            HttpStatus.OK);
    }

    /**
     * Gets a post using its Id.
     *
     * @param id long representing id of the post.
     * @return ResponseEntity containing the post.
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<?> getPost(@PathVariable long id) throws Exception {

        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    /**
     * Gets posts from a thread.
     *
     * @param id long representing id of the thread,
     * @return Set containing posts from the thread.
     */
    @GetMapping("/get/fromthread/{id}")
    @ResponseBody
    public ResponseEntity<?> getPostsFromThread(@PathVariable long id) throws Exception {

        return new ResponseEntity<>(postService.getPostsFromThread(id), HttpStatus.OK);
    }

    /**
     * Checks if a post was edited.
     *
     * @param id id of the post to be checked.
     * @return ResponseEntity containing true if it was edited or else false.
     */
    @GetMapping("/checkedited/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> isEdited(@PathVariable long id) throws Exception {

        return new ResponseEntity<>(postService.isEdited(id), HttpStatus.OK);
    }
}
