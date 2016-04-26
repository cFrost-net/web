package net.cfrost.config.bootstrap;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import net.cfrost.web.core.security.filter.PreLoggingFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Order(1)
public class FrameworkBootstrap implements WebApplicationInitializer {


    @Value("${enableLdap}")
    private boolean enableLdap;
    
    private static final Logger log = LogManager.getLogger();
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        String[] urls = {"/resources/*","/test.html"};
        container.getServletRegistration("default").addMapping(urls);
        
//        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
//        rootContext.setConfigLocation("classpath*:beans.xml");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(net.cfrost.config.RootContextConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));
        

        AnnotationConfigWebApplicationContext springWebDispatcherServletContext = new AnnotationConfigWebApplicationContext();
        springWebDispatcherServletContext.register(net.cfrost.config.SpringWebDispatcherServletContextConfiguration.class);
        DispatcherServlet springWebDispatcher = new DispatcherServlet(springWebDispatcherServletContext);
        ServletRegistration.Dynamic dispatcher = container.addServlet("springWebDispatcher", springWebDispatcher);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
        AnnotationConfigWebApplicationContext springRestDispatcherServletContext = new AnnotationConfigWebApplicationContext();
        springRestDispatcherServletContext.register(net.cfrost.config.SpringRestDispatcherServletContextConfiguration.class);
        DispatcherServlet springRestDispatcher = new DispatcherServlet(springRestDispatcherServletContext);
        springRestDispatcher.setDispatchOptionsRequest(true);
        dispatcher = container.addServlet(
                "springRestDispatcher", springRestDispatcher
        );
        dispatcher.setLoadOnStartup(2);
        dispatcher.addMapping("/webapi/*");
        
        FilterRegistration.Dynamic registration = container.addFilter(
                "preLoggingFilter", new PreLoggingFilter()
        );
        registration.addMappingForUrlPatterns(null, false, "/*");
        
        log.info("Web Config Loaded!");
    }
}
