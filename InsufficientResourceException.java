package f1;

public class InsufficientResourceException extends Exception {
    public InsufficientResourceException(String message) {
        super(message);
    }
}