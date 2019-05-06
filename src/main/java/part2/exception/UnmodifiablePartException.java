package part2.exception;

public class UnmodifiablePartException extends RuntimeException {

    public UnmodifiablePartException() {
        super();
    }

    public UnmodifiablePartException(String message) {
        super(message);
    }

    public UnmodifiablePartException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmodifiablePartException(Throwable cause) {
        super(cause);
    }

    protected UnmodifiablePartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
