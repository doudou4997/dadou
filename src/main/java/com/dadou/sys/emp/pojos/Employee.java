package com.dadou.sys.emp.pojos;

import com.dadou.sys.role.pojos.Role;
import com.dadou.sys.role.pojos.RoleMenu;
import com.dadou.sys.role.pojos.RolePrivilege;
import com.dadou.sys.sys.pojos.Sys;
import com.dadou.sys.tree.pojos.TreeNode;

import java.util.*;

/**
 * 员工信息实体类
 * 
 * @author gaofeng
 */
public class Employee {
    /**主键Id**/
    private String id;
    /** 职位名称**/
    private String jobName;
    /** 用户名 **/
    private String userName;
    /** 密码 **/
    private String password;
    /** 姓名 **/
    private String name;
    /** 员工编号 **/
    private String userCode;
    /** 性别 **/
    private String gender;
    /** 手机 **/
    private String mobile;
    /**  电子邮件 **/
    private String email;
    /** 备注 **/
    private String remark;
    /**
     * 是否冻结：1 冻结 0 解冻
     */
    private String freeze = "0";
    /**
     * 删除标识：0 未删除 1已删除
     */
    private Integer deleteFlag;
    /**
     * 最后登录时间
     */
    private String lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /** 部门Id **/
    private String deptId;
    /** 修改时，原来部门id **/
    private String oldDeptId;
    /** 所在部门 **/
    private String deptName = "";
    /** 角色集合 **/
    private Set<Role> roleList = new HashSet<Role>();
    /** 权限集合 **/
    private Set<RolePrivilege> privilegeList = new HashSet<RolePrivilege>(0);
    /** 权限Map **/
    private Map<String, Map<String, String>> userPermission = new HashMap<>();
    //权限标记
    public static final String AUTH_COMMON ="common";
    public static final String AUTH_REG_COMMON = "reg_common";
    /**子系统集合**/
    private List<Sys> sysList = new ArrayList<>();
    /**
     * 左侧导航菜单集合 子系统<-->子系统菜单
     */
    private Map<String,Set<RoleMenu>> menuList = new HashMap<>();
    
    /***
     * 左侧导航菜单 子系统-- 存储的是TreeNode节点
     */
    private Map<String,TreeNode> leftMenuNode = new HashMap<String,TreeNode>();
    /**
	 * 基于用户权限的左侧导航菜单集合
	 */
	private Set<UserMenu> userMenuList= new TreeSet<UserMenu>();

    // ///////////////////////////////////////
    // /所有属性的gettet/setter方法
    // ///////////////////////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFreeze() {
        return freeze;
    }

    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
      return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Set<RolePrivilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(Set<RolePrivilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public String getOldDeptId() {
        return oldDeptId;
    }

    public void setOldDeptId(String oldDeptId) {
        this.oldDeptId = oldDeptId;
    }

    public Map<String, Set<RoleMenu>> getMenuList() {
        return menuList;
    }

    public void setMenuList(Map<String, Set<RoleMenu>> menuList) {
        this.menuList = menuList;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Map<String, Map<String, String>> getUserPermission() {
        return userPermission;
    }

    // 设置操作权限和菜单权限列表
    public void setUserPermission(List<String> authUrls) {
        //两类权限
    	//普通权限:COMMON
    	//正则权限:REG_COMMON
    	HashMap<String, String> COMMON_MAP = new HashMap<>();
    	HashMap<String, String> REG_COMMON_MAP = new HashMap<>();

    	for (String url : authUrls) {
             if(url.contains(".*")){
            	 REG_COMMON_MAP.put(url, url);
             }else{
            	 COMMON_MAP.put(url, url);
             }
         }
    	userPermission.put(AUTH_COMMON,COMMON_MAP);
    	userPermission.put(AUTH_REG_COMMON, REG_COMMON_MAP);
    }
     
	public Set<UserMenu> getUserMenuList() {
		return userMenuList;
	}

	public void setUserMenuList(Set<UserMenu> userMenuList) {
		this.userMenuList = userMenuList;
	}

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

	public Map<String, TreeNode> getLeftMenuNode() {
		return leftMenuNode;
	}

	public void setLeftMenuNode(Map<String, TreeNode> leftMenuNode) {
		this.leftMenuNode = leftMenuNode;
	}

	public List<Sys> getSysList() {
		return sysList;
	}

	public void setSysList(List<Sys> sysList) {
		this.sysList = sysList;
	}

}
