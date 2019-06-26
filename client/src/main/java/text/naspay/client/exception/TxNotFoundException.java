package text.naspay.client.exception;

public class TxNotFoundException extends RuntimeException {

    public TxNotFoundException() {
    }

    public TxNotFoundException(String message) {
        super(message);
    }

    public TxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TxNotFoundException(Throwable cause) {
        super(cause);
    }

    public TxNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
