package agg.samples.commands.util;

public class CustomBusinessException extends RuntimeException {
    
    public CustomBusinessException(String message) {
        super(message);
    }
    public CustomBusinessException(Throwable root) {
        super(root);
    }
}
