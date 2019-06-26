package text.naspay.client.exception;

public class AuthentificationException extends RuntimeException {

    public AuthentificationException() {
    }

    public AuthentificationException(String message) {
        super(message);
    }

    public AuthentificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthentificationException(Throwable cause) {
        super(cause);
    }

    public AuthentificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
