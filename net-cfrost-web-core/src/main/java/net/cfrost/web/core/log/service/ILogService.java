package net.cfrost.web.core.log.service;

import net.cfrost.web.core.base.service.IBaseService;
import net.cfrost.web.core.log.domain.AccessLog;

public interface ILogService extends IBaseService {

    public void saveAccessLog(AccessLog accessLog);
}
