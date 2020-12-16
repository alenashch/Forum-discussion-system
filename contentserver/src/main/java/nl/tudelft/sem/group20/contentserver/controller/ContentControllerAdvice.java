package nl.tudelft.sem.group20.contentserver.controller;

import exceptions.AuthorizationFailedException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContentControllerAdvice {

    @ExceptionHandler({AuthorizationFailedException.class})
    ResponseEntity<String> handleAuthorizationFailedException(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({BoardThreadNotFoundException.class})
    ResponseEntity<String> handleBoardThreadNotFoundException(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*
    @ExceptionHandler({PostAlreadyExistsException.class})
    ResponseEntity<String> handlePostAlreadyExistsException(RuntimeException exception) {

        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_GATEWAY;
    }

     */

    @ExceptionHandler({PostNotFoundException.class})
    ResponseEntity<String> handlePostNotFoundException(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
