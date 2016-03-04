package net.cfrost.web.log.service.impl;

import org.springframework.transaction.annotation.Transactional;

import net.cfrost.web.base.service.impl.BaseService;
import net.cfrost.web.log.dao.IAccessLogDao;
import net.cfrost.web.log.domain.AccessLog;
import net.cfrost.web.log.service.ILogService;

public class LogService extends BaseService implements ILogService {

    private IAccessLogDao accessLogDao;
    
    public void setAccessLogDao(IAccessLogDao accessLogDao) {
        this.accessLogDao = accessLogDao;
    }

    @Override
    @Transactional
    public void saveAccessLog(AccessLog accessLog) {
        this.accessLogDao.saveOrUpdate(accessLog);
    }

}
