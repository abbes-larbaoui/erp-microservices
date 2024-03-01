package dz.kyrios.notificationservice.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "You do not have the required authority";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
