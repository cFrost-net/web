package net.cfrost.web.log.service;

import net.cfrost.web.base.service.IBaseService;
import net.cfrost.web.log.domain.AccessLog;

public interface ILogService extends IBaseService {

    public void saveAccessLog(AccessLog accessLog);
}
