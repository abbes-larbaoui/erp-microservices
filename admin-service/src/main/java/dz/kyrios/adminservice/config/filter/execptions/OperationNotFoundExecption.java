package dz.kyrios.adminservice.config.filter.execptions;

public class OperationNotFoundExecption extends Exception{
    public OperationNotFoundExecption(String message) {
        super(message);
    }
}
