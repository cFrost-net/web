package net.cfrost.web.core.log.service.impl;

import net.cfrost.web.core.base.service.impl.BaseService;
import net.cfrost.web.core.log.dao.IAccessLogDao;
import net.cfrost.web.core.log.entity.AccessLog;
import net.cfrost.web.core.log.service.ILogService;

public class LogService extends BaseService implements ILogService {

    private IAccessLogDao accessLogDao;
    
    public void setAccessLogDao(IAccessLogDao accessLogDao) {
        this.accessLogDao = accessLogDao;
    }

    @Override
    public void saveAccessLog(AccessLog accessLog) {
        this.accessLogDao.saveOrUpdate(accessLog);
    }

}
