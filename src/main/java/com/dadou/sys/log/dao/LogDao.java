package com.dadou.sys.log.dao;

import com.dadou.sys.log.pojos.SysLog;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import org.springframework.stereotype.Repository;

/**
 * 日志记录Dao
 */
@Repository("sys_logDao")
public class LogDao extends GenericMybatisDao<SysLog, String> {
}
