package net.cfrost.web.core.util;

import net.cfrost.web.core.log.entity.AccessLog;
import net.cfrost.web.core.log.service.ILogService;

public class Log {

    private static ILogService logService;

    public void setLogService(ILogService logService) {
        Log.logService = logService;
    }
    
    public static void saveAccessLog(AccessLog accessLog){
        logService.saveAccessLog(accessLog);
    }
}
