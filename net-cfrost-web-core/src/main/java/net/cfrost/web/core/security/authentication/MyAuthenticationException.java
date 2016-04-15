package net.cfrost.web.core.security.authentication;

import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2385242105086215063L;

    public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyAuthenticationException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

}
