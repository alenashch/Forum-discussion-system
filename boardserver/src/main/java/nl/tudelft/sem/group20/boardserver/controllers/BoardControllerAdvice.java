package nl.tudelft.sem.group20.boardserver.controllers;

import java.nio.file.AccessDeniedException;
import nl.tudelft.sem.group20.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class BoardControllerAdvice {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException() {
        return new ResponseEntity<>("This user does not exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<String> handleAccessDeniedException() {
        return new ResponseEntity<>("Unauthorized action.", HttpStatus.UNAUTHORIZED);
    }
}
