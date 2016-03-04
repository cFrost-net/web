package net.cfrost.web.module.root.service;

import java.util.List;

import net.cfrost.web.core.service.IBaseService;
import net.cfrost.web.module.root.domain.DevProcess;

public interface IIndexService extends IBaseService {
    public DevProcess findDevProcessById(Long id);
    public List<DevProcess> findAllDevProcesses();
}
