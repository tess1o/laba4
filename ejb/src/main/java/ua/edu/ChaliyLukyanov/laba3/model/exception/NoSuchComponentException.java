package ua.edu.ChaliyLukyanov.laba3.model.exception;

public class NoSuchComponentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoSuchComponentException() {
        super();
    }

    public NoSuchComponentException(String message) {
        super(message);
    }

    public NoSuchComponentException(Throwable cause) {
        super(cause);
    }

    public NoSuchComponentException(String message, Throwable cause) {
        super(message, cause);
    }
}
