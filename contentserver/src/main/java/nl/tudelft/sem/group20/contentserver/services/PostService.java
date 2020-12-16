package nl.tudelft.sem.group20.contentserver.services;

import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final transient PostRepository postRepository;

    private final transient ThreadRepository threadRepository;

    public PostService(PostRepository postRepository, ThreadRepository threadRepository) {
        this.postRepository = postRepository;
        this.threadRepository = threadRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * Creates a Post in the database.
     *
     * @param request Request with information needed to create a new post.
     * @return -1 if the Post already exists in the database, or the id of the newly created post
     *         if creation was successful.
     */
    public long createPost(CreatePostRequest request) {

        BoardThread boardThread = threadRepository.getById(request.getBoardThreadId()).orElse(null);
        if (boardThread == null) {

            return -1;
        }

        int nextPostNumber = boardThread.getPosts().size();
        Post toCreate = new Post(nextPostNumber, request.getCreator(),
            request.getBody(), boardThread, LocalDateTime.now());

        if (postRepository.getById(toCreate.getId()).isPresent()) {
            return -1;
        }

        postRepository.saveAndFlush(toCreate);
        boardThread.addPost(toCreate);
        threadRepository.saveAndFlush(boardThread);
        return toCreate.getId();
    }

    /**
     * Updates a Post in the database.
     *
     * @param request Request with information needed to create a new post
     * @return false if the Post does not exist in the database, and true otherwise.
     */
    public boolean updatePost(CreatePostRequest request) {

        Post toUpdate = postRepository.getById(request.getBoardThreadId()).orElse(null);

        if (toUpdate == null) {

            return false;
        }

        toUpdate.setBody(request.getBody());
        toUpdate.setEdited(LocalDateTime.now());
        if (postRepository.getById(request.getBoardThreadId()).isEmpty()) {
            return false;
        }

        if (request.getBoardThreadId() != toUpdate.getBoardThread().getId()) {

            BoardThread boardThread = toUpdate.getBoardThread();
            boardThread.removePost(toUpdate);

            BoardThread newThread =
                threadRepository.getById(request.getBoardThreadId()).orElse(null);

            if (newThread == null) {

                return false;
            }

            toUpdate.setBoardThread(newThread);
            newThread.addPost(toUpdate);
            threadRepository.saveAndFlush(newThread);
        }

        postRepository.saveAndFlush(toUpdate);
        return true;
    }
}
