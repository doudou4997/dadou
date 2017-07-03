package com.dadou.sys.dept.dao;

import com.dadou.sys.dept.pojos.Department;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import com.framework.core.page.Pagination;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
@Repository("departmentDao")
public class DepartmentDao extends GenericMybatisDao<Department, String> {
    /**
     * 保存部门
     */
    public int save(Department dept){
		// 获取排序号
		Integer sortNum = getNextSortNumByParentId(dept.getParentId() + "");
		// 保存排序号
		dept.setSortNum(sortNum);
		// 设置删除标识为0,表示未删除
		dept.setDeleteFlag(0);
		
		// 保存实体对象
		return super.save(dept);
    }
    /**
     * 删除部门列表
     */
    public void remove(String id){    	
      this.removeById(id);
    }
	/**
	 * 获取排序号码
	 */
	private Integer getNextSortNumByParentId(String parentId) {

		List list = this.findList("getNextSortNumByParentId",parentId);
		 if(list!=null && list.size()>0){
		 	return NumberUtils.toInt(list.get(0).toString());
		 }
		 return 0;
	}
	/**
	 * 分页查询部门
	 * @param pageNo
	 * @param pageSize
	 * @param queryMap
	 * @return
	 */
	public Pagination<Department> findByPage(int pageNo, Integer pageSize,
                                             Map<String, Object> queryMap) {
		queryMap.put("pageNo", pageNo);
		queryMap.put("pageSize", pageSize);
        return super.findByPage(queryMap);
	}
	/**
	 * 返回部门数量
	 * @return
	 */
	public int findDeptNum() {
		int count = 0;
		List list = this.findList("findDeptNum");
		List<Map<String,Object>> mapList = list;
		if(mapList !=null && mapList.isEmpty()){
			Map<String,Object> map = mapList.get(0);
			count = NumberUtils.toInt(map.get("COUNTNUM").toString());
		}
		return count;
	}

	/**
	 * 根据ID查找部门名称
	 * @param id
	 * @return
	 */
	public String findDeptName(Integer id) {
		String deptName = null;
		List list = this.findList("findDeptName",id);
		List<Map<String,Object>> mapList = list;
		if(mapList !=null && mapList.isEmpty()){
			Map<String,Object> map = mapList.get(0);
			deptName = map.get("DEPTNAME").toString();
		}
		return deptName;
	}
}
