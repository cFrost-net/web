package net.cfrost.config.bootstrap;

import net.cfrost.web.core.security.filter.PostLoggingFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Order(3)
public class LoggingBootstrap implements WebApplicationInitializer
{
    private static final Logger log = LogManager.getLogger();

    @Override
    public void onStartup(ServletContext container) throws ServletException
    {
        log.info("Executing logging bootstrap.");

        FilterRegistration.Dynamic registration = container.addFilter(
                "postLoggingFilter", new PostLoggingFilter()
        );
        registration.addMappingForUrlPatterns(null, false, "/*");
    }
}
