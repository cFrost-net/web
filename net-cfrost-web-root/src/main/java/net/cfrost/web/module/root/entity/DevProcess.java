package net.cfrost.web.module.root.entity;

import net.cfrost.web.core.base.entity.BaseInfoEntity;

@SuppressWarnings("serial")
public class DevProcess extends BaseInfoEntity<DevProcess> {

    private String process;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
    
    
}
