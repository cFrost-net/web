package net.cfrost.web.module.root.service.impl;

import java.util.List;

import net.cfrost.web.core.service.impl.BaseService;
import net.cfrost.web.module.root.service.IIndexService;
import net.cfrost.web.module.root.dao.IDevProcessDao;
import net.cfrost.web.module.root.domain.DevProcess;

public class IndexService extends BaseService implements IIndexService {

    private IDevProcessDao devProcessDao;
    
    public void setDevProcessDao(IDevProcessDao devProcessDao) {
        this.devProcessDao = devProcessDao;
    }

    @Override
    public DevProcess findDevProcessById(Long id) {
        return this.devProcessDao.get(id);
    }

    @Override
    public List<DevProcess> findAllDevProcesses() {
        return this.devProcessDao.findAll();
    }

}
