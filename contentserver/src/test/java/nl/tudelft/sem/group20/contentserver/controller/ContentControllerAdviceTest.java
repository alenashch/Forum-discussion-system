package nl.tudelft.sem.group20.contentserver.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ContentControllerAdviceTest {

    private transient String message;
    private transient RuntimeException exception;
    private transient ContentControllerAdvice controllerAdvice;

    @BeforeEach
    void setUp() {

        message = "error";
        exception = new RuntimeException(message);
        controllerAdvice = new ContentControllerAdvice();
    }

    @Test
    void handleAuthorizationFailedExceptionTest() {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(message,
            HttpStatus.UNAUTHORIZED);
        Assertions.assertEquals(responseEntity,
            controllerAdvice.handleAuthorizationFailedException(exception));
    }

    @Test
    void handleBoardThreadNotFoundExceptionTest() {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(message,
            HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(responseEntity,
            controllerAdvice.handleBoardThreadNotFoundException(exception));
    }

    @Test
    void handlePostNotFoundExceptionTest() {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(message,
            HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(responseEntity,
            controllerAdvice.handlePostNotFoundException(exception));
    }
}