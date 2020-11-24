package nl.tudelft.sem.template.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.model.Post;
import nl.tudelft.sem.template.service.PostService;
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

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Long> createPost(@RequestBody Post post) {

        return Collections.singletonMap("ID", postService.createPost(post));
    }

    @GetMapping("/get")
    @ResponseBody
    public List<Post> getPosts() {

        return postService.getPosts();
    }

    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Boolean> editPost(@RequestBody Post post) {

        return Collections.singletonMap("success", postService.updatePost(post));
    }


}
