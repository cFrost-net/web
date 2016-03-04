package net.cfrost.web.util;

import net.cfrost.web.log.domain.AccessLog;
import net.cfrost.web.log.service.ILogService;

public class Log {

    private static ILogService logService;

    public void setLogService(ILogService logService) {
        Log.logService = logService;
    }
    
    public static void saveAccessLog(AccessLog accessLog){
        logService.saveAccessLog(accessLog);
    }
}
