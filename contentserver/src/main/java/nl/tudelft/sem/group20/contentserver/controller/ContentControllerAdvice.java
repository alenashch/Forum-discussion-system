package nl.tudelft.sem.group20.contentserver.controller;

import exceptions.AuthorizationFailedException;
import exceptions.BoardIsLockedException;
import exceptions.BoardNotFoundException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PermissionException;
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

    @ExceptionHandler({PostNotFoundException.class})
    ResponseEntity<String> handlePostNotFoundException(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PermissionException.class})
    ResponseEntity<String> handlePermissionException(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BoardIsLockedException.class})
    ResponseEntity<String> boardIsLockedException(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BoardNotFoundException.class})
    ResponseEntity<String> boardNotFoundException(RuntimeException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
