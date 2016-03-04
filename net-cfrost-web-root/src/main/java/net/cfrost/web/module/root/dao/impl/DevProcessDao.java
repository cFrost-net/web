package net.cfrost.web.module.root.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.cfrost.web.core.dao.hibernate5.impl.BaseDao;
import net.cfrost.web.module.root.dao.IDevProcessDao;
import net.cfrost.web.module.root.domain.DevProcess;

public class DevProcessDao extends BaseDao<DevProcess> implements
        IDevProcessDao {
    
    @Override
    public List<DevProcess> findAll() {
        List<DevProcess> returnList = new ArrayList<DevProcess>();
        for(int i = 0; i < 10; i++){
            DevProcess data = new DevProcess();
            data.setProcess("进度"+i);
            returnList.add(data);
        }
        return returnList;
    }
}
