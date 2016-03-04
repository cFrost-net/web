package net.cfrost.web.module.root.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.cfrost.web.core.action.BaseAction;
import net.cfrost.web.module.root.domain.DevProcess;
import net.cfrost.web.module.root.service.IIndexService;

@SuppressWarnings("serial")
public class IndexAction extends BaseAction {

    private IIndexService indexService;
    
    public void setIndexService(IIndexService indexService) {
        this.indexService = indexService;
    }
    
    List<DevProcess> devProcesses;

    public List<DevProcess> getDevProcesses() {
        return devProcesses;
    }

    public void setDevProcesses(List<DevProcess> devProcesses) {
        this.devProcesses = devProcesses;
    }

    public String loadProcess(){
        this.devProcesses = this.indexService.findAllDevProcesses();
        @SuppressWarnings("unchecked")
        Map<String,Object> request = (Map<String, Object>) this.getActionContext().get("request");  
        HttpServletRequest httpServletRequest = this.getRequest();
        httpServletRequest.setAttribute("count", devProcesses.size());
        
        for(int i = 0 ; i < devProcesses.size(); i++){
            request.put("process"+i, devProcesses.get(i).getProcess());
        }
        return SUCCESS;
    }
}
