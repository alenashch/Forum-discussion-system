package nl.tudelft.sem.group20.contentserver.services;

import exceptions.AuthorizationFailedException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PostNotFoundException;
import exceptions.ThreadIsLockedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.contentserver.requests.EditPostRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService extends ContentService {

    private final transient PostRepository postRepository;

    /**
     * Constructor of PostService.
     *
     * @param postRepository   - PostRepository to use.
     * @param threadRepository - ThreadRepository to use.
     * @param restTemplate     - restTemplate to use.
     */
    public PostService(PostRepository postRepository, ThreadRepository threadRepository,
                       RestTemplate restTemplate) {
        super(threadRepository, restTemplate);
        this.postRepository = postRepository;
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
     * @throws RuntimeException One of the custom exception if something goes wrong.
     */
    public long createPost(String token, CreatePostRequest request) throws Exception {

        BoardThread boardThread = retrieveThread(request.getBoardThreadId());

        isBoardLocked(boardThread.getBoardId());

        int nextPostNumber = boardThread.getPosts().size();
        Post toCreate = new Post(nextPostNumber, authenticateUser(token, false),
            request.getBody(), boardThread, LocalDateTime.now());

        postRepository.saveAndFlush(toCreate);
        boardThread.addPost(toCreate);
        threadRepository.saveAndFlush(boardThread);
        return toCreate.getId();
    }

    /**
     * Updates a Post in the database.
     *
     * @param request Request with information needed to create a new post
     */
    public void updatePost(String token, EditPostRequest request) throws Exception {

        Post toUpdate = postRepository.getById(request.getPostId())
            .orElseThrow(PostNotFoundException::new);

        if (!toUpdate.getCreatorName().equals(authenticateUser(token, false))) {

            throw new AuthorizationFailedException("This post does not belong to the given user");
        }
        toUpdate.setBody(request.getBody());
        toUpdate.setEditedTime(LocalDateTime.now());

        updateThread(toUpdate, request.getBoardThreadId());

        postRepository.saveAndFlush(toUpdate);
    }

    /**
     * Returns post with a given id.
     *
     * @param id - id of the to be retrieved.
     * @return the retrieved post.
     * @throws PostNotFoundException when the post could not be found.
     */
    public Post getPostById(long id) throws Exception {

        Post post = postRepository.getById(id).orElseThrow(PostNotFoundException::new);

        if (post == null) {

            throw new PostNotFoundException();
        }

        return post;
    }

    /**
     * Returns all posts in a thread.
     *
     * @param id id of a thread.
     * @return set containing all posts.
     * @throws BoardThreadNotFoundException if the thread could not be found.
     */
    public Set<Post> getPostsFromThread(long id) throws Exception {

        BoardThread boardThread =
            threadRepository.getById(id).orElseThrow(BoardThreadNotFoundException::new);

        return boardThread.getPosts();
    }

    /**
     * Checks if a post was edited.
     *
     * @param id Id of the post to check.
     * @return true if it was edites or false otherwise.
     */
    public Boolean isEdited(long id) throws Exception {

        Post post = postRepository.getById(id).orElseThrow(PostNotFoundException::new);

        return !post.getCreatedTime().equals(post.getEditedTime());
    }

    /**
     * Retrieves thread with a given id and checks whether it exists.
     *
     * @param threadId the ID of the thread.
     * @return The retrieved BoardThread.
     * @throws Exception When the thread is not found.
     */
    public BoardThread retrieveThread(long threadId) throws Exception {

        BoardThread boardThread =
            threadRepository.getById(threadId)
                .orElseThrow(BoardThreadNotFoundException::new);


        if (boardThread.isLocked()) {

            throw new ThreadIsLockedException();
        }

        return boardThread;
    }

    /**
     * Checks if the thread exists is in the given
     * thread and update the thread.
     *
     * @param toUpdate the post in the thread.
     * @param threadId the id of the thread.
     * @throws Exception if the given thread does not exist.
     */
    public void updateThread(Post toUpdate, long threadId) throws Exception {

        if (threadId != toUpdate.getBoardThread().getId()) {

            BoardThread boardThread = toUpdate.getBoardThread();
            boardThread.removePost(toUpdate);

            BoardThread newThread =
                threadRepository.getById(threadId).orElseThrow(
                    () -> new BoardThreadNotFoundException("Given new thread does not exist"));

            toUpdate.setBoardThread(newThread);
            newThread.addPost(toUpdate);
            threadRepository.saveAndFlush(newThread);
        }
    }
}
