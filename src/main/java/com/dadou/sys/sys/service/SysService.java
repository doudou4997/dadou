package com.dadou.sys.sys.service;

import com.dadou.sys.sys.dao.SysDao;
import com.dadou.sys.sys.pojos.Sys;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 子系统Service类
 * @author gaof
 *
 */
@Service("sys_sysService")
public class SysService {
    @Resource(name="sys_sysDao")
	private SysDao sysDao;
    /**
     * 获取所有子系统列表
     * @return
     */
	public List<Sys> findAll(){
		return sysDao.findAll();
	}
	/**
	 * 根据Id获取所有子系统列表
	 */
	/**
	 * 获取所有子系统列表
	 */
	public List<Sys> findAllSys(Set<String> sysIds){
		Map<String, Object> params = new HashMap<>();
		params.put("params", sysIds);
		return sysDao.findList("findAllByIds", params);
	}
}
