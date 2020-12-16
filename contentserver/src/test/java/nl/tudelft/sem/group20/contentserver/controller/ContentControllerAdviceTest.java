package nl.tudelft.sem.group20.contentserver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ContentControllerAdviceTest {

    private transient String message;

    @BeforeEach
    void setUp() {

        message = "error";
    }

    @Test
    void handleAuthorizationFailedException() {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(message,
            HttpStatus.UNAUTHORIZED);
    }

    @Test
    void handleBoardThreadNotFoundException() {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(message,
            HttpStatus.BAD_REQUEST);
    }

    @Test
    void handlePostNotFoundException() {

        ResponseEntity<String> responseEntity = new ResponseEntity<>(message,
            HttpStatus.BAD_REQUEST);
    }
}