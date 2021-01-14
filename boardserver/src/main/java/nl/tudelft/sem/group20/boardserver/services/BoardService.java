package nl.tudelft.sem.group20.boardserver.services;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.group20.boardserver.entities.Board;
import nl.tudelft.sem.group20.boardserver.repos.BoardRepository;
import nl.tudelft.sem.group20.boardserver.requests.CreateBoardRequest;
import nl.tudelft.sem.group20.boardserver.requests.EditBoardRequest;
import nl.tudelft.sem.group20.exceptions.UserNotFoundException;
import nl.tudelft.sem.group20.shared.AuthRequest;
import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BoardService {

    private final transient BoardRepository boardRepository;
    private final transient RestTemplate restTemplate;

    private final static transient String authenticateUserUrl =
            "http://authentication-server/user/authenticate";
    private final static transient String contentGetThreads =
            "http://content-server/thread/get";

    public BoardService(BoardRepository boardRepository, RestTemplate restTemplate) {
        this.boardRepository = boardRepository;
        this.restTemplate = restTemplate;
    }

    public static String getAuthenticateUserUrl() {
        return authenticateUserUrl;
    }

    /**
     * Gets a response from Authentication Server.
     *
     * @param token - a user token.
     * @return response from Authentication server
     *          if the token is valid.
     *
     */
    public AuthResponse getAuthentication(String token) {

        AuthResponse response = restTemplate.postForObject(authenticateUserUrl,
                new AuthRequest(token), AuthResponse.class);

        assert response != null;

        if (response.getStatus() == StatusResponse.Status.fail) {
            return  null;
        }

        return response;
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    /**
     * Creates a Board in the database.
     *
     * @param request - a new board to add.
     * @return the id of the newly created board
     *      if creation was successful.
     *
     * @throws AccessDeniedException if the user does not have permissions to
     *      create a board.
     * @throws UserNotFoundException if the token is not valid.
     */
    public long createBoard(CreateBoardRequest request, String token)
            throws UserNotFoundException, AccessDeniedException {

        AuthResponse response = getAuthentication(token);

        if (response == null) {
            throw new UserNotFoundException(
                    "This token does not belong to a legitimate user. Board cannot be created");
        }

        if (!response.isType()) {
            throw new AccessDeniedException("This type of user cannot create a board.");
        }

        Board toCreate = new Board(request.getName(), request.getDescription(),
                false, response.getUsername());

        Board.checkCreationTime(toCreate);

        return boardRepository.saveAndFlush(toCreate).getId();
    }


    /**
     * Updates a Board in the database.
     *
     * @param request - request to update a board.
     * @return false if the Board does not exist in the database, true otherwise.
     *
     * @throws AccessDeniedException if the user does not have permissions to
     *      create a board.
     * @throws UserNotFoundException if the token is not valid.
     */
    public boolean updateBoard(EditBoardRequest request, String token)
            throws UserNotFoundException, AccessDeniedException {

        AuthResponse response = getAuthentication(token);


        if (response == null) {
            throw new UserNotFoundException(
                    "This token does not belong to a legitimate user. Board cannot be created");
        }

        if (boardRepository.getById(request.getId()).isEmpty()) {
            return false;
        }

        Board currentBoard = getById(request.getId());
        if (!currentBoard.getUsername().equals(response.getUsername())) {
            throw new AccessDeniedException(
                    "This user does not have the permission to edit this board.");
        }

        boardRepository.saveAndFlush(new Board(request.getId(),
                request.getName(), request.getDescription(),
                request.isLocked(), response.getUsername()));

        return true;
    }


    /**
     * Retrieves a Board from the database.
     *
     * @param id - an id of a board to be retrieved.
     * @return the Board if it is in database, null otherwise.
     */
    public Board getById(long id) {
        if (boardRepository.getById(id).isEmpty()) {
            return null;
        }
        return boardRepository.getOne(id);
    }



}
