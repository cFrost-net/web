package net.cfrost.web.core.security.authentication.service.impl;

import java.util.List;

import net.cfrost.web.core.base.service.impl.BaseService;
import net.cfrost.web.core.security.authentication.dao.IUrlMatcherDao;
import net.cfrost.web.core.security.authentication.entity.UrlMatcher;
import net.cfrost.web.core.security.authentication.service.IAuthorityService;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AuthorityService extends BaseService implements IAuthorityService {
    
    private IUrlMatcherDao urlMatcherDao;

    public void setUrlMatcherDao(IUrlMatcherDao urlMatcherDao) {
        this.urlMatcherDao = urlMatcherDao;
    }



    @Override
    public List<UrlMatcher> findUrlMatcherAuthorities() {
        List<UrlMatcher> returnList =  this.urlMatcherDao.findUrlMatcherAuthorities();        
        if(returnList == null|| returnList.isEmpty()) return null;
        
        for(UrlMatcher urlMatcher : returnList){
            urlMatcher.getAuthorities().size();
        }
        
        return returnList;
    }    
}
