package net.cfrost.web.module.root.domain;

import net.cfrost.web.core.domain.BaseEntity;

@SuppressWarnings("serial")
public class DevProcess extends BaseEntity<DevProcess> {

    private String process;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
    
    
}
