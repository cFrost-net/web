package net.cfrost.web.core.security.jwt;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JwtListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String jwtConfigLocation = context.getInitParameter("jwtConfigLocation");

        if(jwtConfigLocation == null) {
            JwtAuthenticator.init();
        } else {
            JwtAuthenticator.init(jwtConfigLocation);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        JwtAuthenticator.destroy();
    }
}
