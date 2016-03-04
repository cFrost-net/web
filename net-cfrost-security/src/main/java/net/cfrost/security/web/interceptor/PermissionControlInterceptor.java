package net.cfrost.security.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class PermissionControlInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation paramActionInvocation) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("intercept-namespace:"+paramActionInvocation.getProxy().getNamespace());
        String result = paramActionInvocation.invoke();
        return result;
    }

}
