package nl.tudelft.sem.group20.contentserver.services;

import exceptions.AuthorizationFailedException;
import exceptions.BoardThreadNotFoundException;
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
     * Creates a Post in the database.
     *
     * @param request Request with information needed to create a new post.
     * @return -1 if the Post already exists in the database, or the id of the newly created post
     * if creation was successful.
     * @throws RuntimeException One of the custom exception if something goes wrong
     *         if creation was successful.
     */
    public long createPost(String token, CreatePostRequest request) throws RuntimeException {

        AuthResponse authResponse = restTemplate.postForObject("http://authentication-server/user" +
                "/authenticate",
            new AuthRequest(token), AuthResponse.class);

        if (authResponse == null || authResponse.getStatus() == StatusResponse.Status.fail) {

            throw new AuthorizationFailedException();
        }
        BoardThread boardThread = threadRepository.getById(request.getBoardThreadId()).orElse(null);
        if (boardThread == null) {

            throw new BoardThreadNotFoundException();
        }

        int nextPostNumber = boardThread.getPosts().size();
        Post toCreate = new Post(nextPostNumber, authResponse.getUsername(),
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
