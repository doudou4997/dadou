package com.dadou.sys.log.service;

import com.dadou.sys.log.dao.LogDao;
import com.dadou.sys.log.pojos.SysLog;
import com.framework.core.page.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("sys_logService")
public class LogService  {
    /**
     * 日志记录
     */
	@Resource(name="sys_logDao")
    private LogDao logDao;
    public Pagination<SysLog> findByPage(int pageNo, int pageSize, Map<String, Object> queryMap) {
    	queryMap.put("pageNo", pageNo);
    	queryMap.put("pageSize", pageSize);
    	return logDao.findByPage(queryMap);
    }

    public void save(SysLog log) {
        logDao.save(log);
    }
}
