package nl.tudelft.sem.group20.contentserver.services;

import exceptions.AuthorizationFailedException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PostNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.group20.contentserver.entities.BoardThread;
import nl.tudelft.sem.group20.contentserver.entities.Post;
import nl.tudelft.sem.group20.contentserver.repositories.PostRepository;
import nl.tudelft.sem.group20.contentserver.repositories.ThreadRepository;
import nl.tudelft.sem.group20.contentserver.requests.CreatePostRequest;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {

    private final transient PostRepository postRepository;

    private final transient ThreadRepository threadRepository;

    private transient RestTemplate restTemplate;

    /**
     * Constructor of PostService.
     *
     * @param postRepository   - PostRepository to use.
     * @param threadRepository - ThreadRepository to use.
     * @param restTemplate     - restTemplate to use.
     */
    public PostService(PostRepository postRepository, ThreadRepository threadRepository,
                       RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.threadRepository = threadRepository;
        this.restTemplate = restTemplate;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    /**
     * Authenticates user.
     *
     * @param token String with an authentication token in it.
     * @return String with a username.
     * @throws RuntimeException if the authentication fails.
     */
    public String authenticateUser(String token) throws RuntimeException {

        AuthResponse authResponse = restTemplate.postForObject("http://authentication-server/user"
                + "/authenticate",
            new AuthRequest(token), AuthResponse.class);

        if (authResponse == null || authResponse.getStatus() == StatusResponse.Status.fail) {

            throw new AuthorizationFailedException();
        }

        return authResponse.getUsername();
    }

    /**
     * Creates a Post in the database.
     *
     * @param request Request with information needed to create a new post.
     * @return -1 if the Post already exists in the database, or the id of the newly created post
     *      if creation was successful.
     * @throws RuntimeException One of the custom exception if something goes wrong.
     */
    public long createPost(String token, CreatePostRequest request) throws RuntimeException {

        BoardThread boardThread = threadRepository.getById(request.getBoardThreadId()).orElse(null);
        if (boardThread == null) {

            throw new BoardThreadNotFoundException();
        }

        int nextPostNumber = boardThread.getPosts().size();
        Post toCreate = new Post(nextPostNumber, authenticateUser(token),
            request.getBody(), boardThread, LocalDateTime.now());

        /*
        if (postRepository.getById(toCreate.getId()).isPresent()) {

            throw new PostAlreadyExistsException();
        }
         */

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
    public void updatePost(String token, CreatePostRequest request) throws RuntimeException {

        Post toUpdate = postRepository.getById(request.getBoardThreadId()).orElse(null);

        if (toUpdate == null) {

            throw new PostNotFoundException();
        }

        if (!toUpdate.getCreatorName().equals(authenticateUser(token))) {

            throw new AuthorizationFailedException("This post does not belong to the given user");
        }
        toUpdate.setBody(request.getBody());
        toUpdate.setEdited(LocalDateTime.now());


        if (request.getBoardThreadId() != toUpdate.getBoardThread().getId()) {

            BoardThread boardThread = toUpdate.getBoardThread();
            boardThread.removePost(toUpdate);

            BoardThread newThread =
                threadRepository.getById(request.getBoardThreadId()).orElse(null);

            if (newThread == null) {

                throw new BoardThreadNotFoundException("Given new thread does not exist");
            }

            toUpdate.setBoardThread(newThread);
            newThread.addPost(toUpdate);
            threadRepository.saveAndFlush(newThread);
        }

        postRepository.saveAndFlush(toUpdate);
    }
}
