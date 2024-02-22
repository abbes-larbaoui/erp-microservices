package dz.kyrios.notificationservice.config.filter.execptions;

public class OperationNotFoundExecption extends Exception{
    public OperationNotFoundExecption(String message) {
        super(message);
    }
}
