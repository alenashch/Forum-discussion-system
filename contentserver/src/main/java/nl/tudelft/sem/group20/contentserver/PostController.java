package nl.tudelft.sem.group20.contentserver;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/create")
    @ResponseBody
    public Map<String, Long> createPost(@RequestBody Post post) {

        return Collections.singletonMap("ID", postService.createPost(post));
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
    public Map<String, Boolean> editPost(@RequestBody Post post) {

        return Collections.singletonMap("success", postService.updatePost(post));
    }


}
