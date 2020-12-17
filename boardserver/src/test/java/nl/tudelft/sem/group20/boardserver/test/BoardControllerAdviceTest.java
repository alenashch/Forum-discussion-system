package nl.tudelft.sem.group20.boardserver.test;

import nl.tudelft.sem.group20.boardserver.controllers.BoardControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardControllerAdviceTest {
    transient ResponseEntity<String> userNotFound;
    transient ResponseEntity<String> accessDenied;

    transient BoardControllerAdvice controllerAdvice;

    @BeforeEach
    void initialize() {
        userNotFound = new ResponseEntity<>("This user does not exist.",
                HttpStatus.NOT_FOUND);
        accessDenied = new ResponseEntity<>("Unauthorized action.",
                HttpStatus.UNAUTHORIZED);

        controllerAdvice = new BoardControllerAdvice();
    }

    @Test
    void testUserNotFound() {
        assertEquals(userNotFound, controllerAdvice.handleUserNotFoundException());
    }

    @Test
    void testAccessDenied() {
        assertEquals(accessDenied, controllerAdvice.handleAccessDeniedException());
    }
}
