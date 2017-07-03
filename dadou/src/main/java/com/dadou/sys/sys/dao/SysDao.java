package com.dadou.sys.sys.dao;

import com.dadou.sys.sys.pojos.Sys;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import org.springframework.stereotype.Repository;

@Repository("sys_sysDao")
public class SysDao extends GenericMybatisDao<Sys, String> {
}
