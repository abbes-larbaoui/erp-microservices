package dz.kyrios.adminservice.config.exception;


public class NotMatchException extends RuntimeException {

    public NotMatchException(String object1, Long id1, String object2, Long id2) {
        super(object1 + " " + id1 + "dont match " + object2 + " " + id2);
    }
}
