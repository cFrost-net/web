package net.cfrost.web.security.interceptor;

import javax.servlet.http.HttpServletRequest;

import net.cfrost.web.util.UrlTool;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class PermissionControlInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation paramActionInvocation) throws Exception {
        HttpServletRequest request = (HttpServletRequest) paramActionInvocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
        System.out.println("intercept-sessionid:"+request.getRequestedSessionId());
        System.out.println("intercept-sessionid:"+request.getSession().getId());
        System.out.println("intercept-requestURI:"+UrlTool.findRealURI(request));
        System.out.println("intercept-namespace:"+paramActionInvocation.getProxy().getNamespace());
        System.out.println("intercept-action:"+paramActionInvocation.getProxy().getActionName());
        String result = paramActionInvocation.invoke();
        return result;
    }

}
