package net.cfrost.web.core.security.jwt;

public class JwtAuthenticatorInitException extends RuntimeException {
    private static final long serialVersionUID = -1958998784276102353L;

    public JwtAuthenticatorInitException() {
        super();
    }

    public JwtAuthenticatorInitException(String message) {
        super(message);
    }

    
    public JwtAuthenticatorInitException(String message, Throwable cause) {
        super(message, cause);
    }

  
    public JwtAuthenticatorInitException(Throwable cause) {
        super(cause);
    }
}
