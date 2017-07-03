package com.dadou.sys.dept.service;

import com.dadou.sys.dept.dao.DepartmentDao;
import com.dadou.sys.dept.pojos.Department;
import com.dadou.sys.emp.dao.EmployeeDao;
import com.dadou.sys.emp.pojos.Employee;
import com.dadou.sys.tree.pojos.MenuItem;
import com.framework.core.page.Pagination;
import com.framework.core.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("departmentService")
public class DepartmentService {
	@Resource(name="departmentDao")
	private DepartmentDao departmentDao;
	@Resource(name="sys_employeeDao")
	private EmployeeDao employeeDao;
	
	/**
	 * 所有的部门
	 */
	private List<Department> departmentAll;
	/**
	 * 所有用户
	 */
	private List<Employee> employeeAll;
    /**
     * 构造树型结构的部门、用户列表，已按照节点顺序排好
     * 
     * epartmentId
     *            树型结构的根部门ID
     * @return 节点列表
     */
	public List<MenuItem> findDepartmentsAndUsers(String deptId) {
		//初始化信息
		List<Department> deptList = departmentDao.findAll();
		departmentAll = deptList;
		List<Employee> empList = employeeDao.findAll();
		employeeAll = empList;
		List<MenuItem> result = new ArrayList<MenuItem>();
		//默认根节点的值为100，数据库中也需要对应起来
		Department dept = this.findById(deptId);
		MenuItem mi = generateMenuItem(dept, 0, dept.getParentId());
		result.add(mi);
		result.addAll(buildDepts(deptId, 0));
		//销毁 由于单例模式，必须销毁
		this.departmentAll = null;
		this.employeeAll = null;
        return result;
	}
	
	/**
	 * 根据Department构造MenuItem
	 * @param d
	 * @param level
	 * @return
	 */
	private MenuItem generateMenuItem(Department d, int level, String parentId) {
		MenuItem mi = new MenuItem();
		mi.setId(d.getId());
		mi.setLeaf(false);
		mi.setLevel(level);
		mi.setName(d.getDeptName());
		mi.setParentId(parentId);
		mi.setType(1);
		return mi;
	}
	/**
	 * 递归
	 * 使用：先初始化成员变量deptUserAll、departmentAll
	 * @return
	 */
	private List<MenuItem> buildDepts(String departmentId, int level) {
		level++;
		List<MenuItem> result = new ArrayList<MenuItem>();
		//查询直接子节点
		if(this.departmentAll!=null) {
			for(Department d : departmentAll) {
				//Oracle 空字符串是Null
				String parentId = StringUtils.valueOf(d.getParentId());
				if(parentId.equals(departmentId)) {
					MenuItem temp = this.generateMenuItem(d, level, departmentId);
					result.add(temp);
					
					if(this.hasChildren(d.getId())) {
						result.addAll(this.buildDepts(d.getId().toString(), level));
					} else {
						List<MenuItem> users = this.findEmployees(d.getId(), level);
						if(users!=null && users.size()>0) {
							result.addAll(users);
						} else {
							temp.setLeaf(true);
						}
					}
				}
			}
		}
		
		return result;
	}
	/**
	 * 判断menuId是否有直接子节点
	 * menuId
	 * listAll
	 * @return
	 */
	private boolean hasChildren(String departmentId) {
		boolean has = false;
		for(Department d : departmentAll) {
			if(d.getParentId().equals(departmentId)) {
				has = true;
				break;
			}
		}
		
		return has;
	}
	/**
	 * 查询departmentId直接DeptUser
	 * id
	 * level
	 * @return
	 */
	private List<MenuItem> findEmployees(String departmentId, int level) {
		
		List<MenuItem> result = new ArrayList<MenuItem>();
		if(this.employeeAll!=null) {
			for(Employee e : this.employeeAll) {
				if(StringUtils.isNotEmpty(e.getDeptId())&& e.getDeptId().equals(departmentId)) {
					MenuItem mi = new MenuItem();
					mi.setId(e.getId()+"");
					mi.setLeaf(true);
					mi.setLevel(level+1);
					mi.setName(e.getUserName());
					mi.setParentId(departmentId.toString());
					mi.setType(2);
					result.add(mi);
				}
			}
		}
		return result;
	}
    public Department findById(String id) {
        return departmentDao.findById(id);
    }
    public void update(Department dept) {
        departmentDao.update(dept);
    }
    public void save(Department dept) {
        departmentDao.save(dept);
        
    }
    public void remove(String id) {
        departmentDao.remove(id);
    }
    /**
     * 分页查询部门
     * @param pageNo
     * pageSize4E
     * @param queryMap
     * @return
     */
	public Pagination<Department> findByPage(int pageNo, Integer pageSize,
											 Map<String, Object> queryMap) {
		return departmentDao.findByPage(pageNo,pageSize,queryMap);
	}
	/**
	 * 返回部门数量
	 * @return
	 */
	public int findDeptNum() {
		return this.departmentDao.findDeptNum();
	}
}
