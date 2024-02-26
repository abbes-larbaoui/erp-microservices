package dz.kyrios.notificationservice.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RuntimeException {

    public MethodNotAllowedException(Long id, String message) {
        super(message + " " + id);
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
