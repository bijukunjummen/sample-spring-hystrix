package agg.samples.commands.util;

public class CustomSystemException extends RuntimeException {
    
    public CustomSystemException(Throwable root) {
        super(root);
    }
    
}
