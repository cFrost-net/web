package net.cfrost.util.exception;

public class BaseUtilException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 3348007771945253268L;

    public BaseUtilException() {
        super();
    }

    public BaseUtilException(String message) {
        super(message);
    }
}
