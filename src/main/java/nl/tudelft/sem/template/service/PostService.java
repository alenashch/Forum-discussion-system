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

    /**
     * Creates a Post and adds it to the database.
     *
     * @param newPost - the Post to be added.
     * @return -1 if the Post already exists in the database, and the id of the newly created post
     *      if creation was successful.
     */
    public long createPost(Post newPost) {
        if (postRepository.getById(newPost.getId()).isPresent())
            return -1;

        postRepository.saveAndFlush(newPost);
        return newPost.getId();
    }
}
