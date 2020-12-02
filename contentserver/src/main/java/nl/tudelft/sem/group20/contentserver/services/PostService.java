package nl.tudelft.sem.group20.contentserver.services;

import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final transient PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * Creates a Post in the database.
     *
     * @param toCreate - the Post to be added.
     * @return -1 if the Post already exists in the database, or the id of the newly created post
     *      if creation was successful.
     */
    public long createPost(Post toCreate) {
        if (postRepository.getById(toCreate.getId()).isPresent()) {
            return -1;
        }

        postRepository.saveAndFlush(toCreate);
        return toCreate.getId();
    }

    /**
     * Updates a Post in the database.
     *
     * @param toUpdate - the post to be updated.
     * @return false if the Post does not exist in the database, and true otherwise.
     */
    public boolean updatePost(Post toUpdate) {
        if (postRepository.getById(toUpdate.getId()).isEmpty()) {
            return false;
        }

        postRepository.saveAndFlush(toUpdate);
        return true;
    }
}
