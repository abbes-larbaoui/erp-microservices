package dz.kyrios.notificationservice.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id, String message) {
        super(message + " " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
