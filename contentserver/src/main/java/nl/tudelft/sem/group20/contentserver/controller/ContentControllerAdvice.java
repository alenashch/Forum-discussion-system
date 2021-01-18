package nl.tudelft.sem.group20.contentserver.controller;

import exceptions.AuthorizationFailedException;
import exceptions.BoardIsLockedException;
import exceptions.BoardNotFoundException;
import exceptions.BoardThreadNotFoundException;
import exceptions.PermissionException;
import exceptions.PostNotFoundException;
import exceptions.ThreadIsLockedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContentControllerAdvice {

    @ExceptionHandler({AuthorizationFailedException.class})
    public ResponseEntity<String> handleAuthorizationFailedException(Exception exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({BoardThreadNotFoundException.class})
    public ResponseEntity<String> handleBoardThreadNotFoundException(Exception exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<String> handlePostNotFoundException(Exception exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PermissionException.class})
    ResponseEntity<String> handlePermissionException(Exception exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BoardIsLockedException.class})
    ResponseEntity<String> boardIsLockedException(Exception exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BoardNotFoundException.class})
    ResponseEntity<String> boardNotFoundException(Exception exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ThreadIsLockedException.class})
    ResponseEntity<String> threadIsLockedException(Exception exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
