package nl.tudelft.sem.template.service;

import java.util.List;
import nl.tudelft.sem.template.model.Post;
import nl.tudelft.sem.template.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    transient private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }
}
