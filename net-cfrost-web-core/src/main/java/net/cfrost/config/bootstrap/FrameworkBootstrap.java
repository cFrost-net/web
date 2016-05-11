package net.cfrost.config.bootstrap;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import net.cfrost.web.core.security.filter.PreLoggingFilter;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

@Order(1)
public class FrameworkBootstrap implements WebApplicationInitializer {
    
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        
//        @Value("${springSecurity.ignoreUrls}")
//        private String ignoreUrls;
        
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(net.cfrost.config.RootContextConfiguration.class);
//        ConfigurableEnvironment configurableEnvironment = rootContext.getEnvironment();
//        configurableEnvironment.setActiveProfiles(arg0);
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

        FilterRegistration.Dynamic characterEncodingFilter = container.addFilter(
                "characterEncodingFilter", new CharacterEncodingFilter("UTF-8")
        );
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
        
        FilterRegistration.Dynamic preLoggingFilter = container.addFilter(
                "preLoggingFilter", new PreLoggingFilter()
        );
        preLoggingFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
