package com.dadou.sys.emp.pojos;

public class EmpRole {
    /**
     * 主键Id
     */
    private String id;
    /**
     * 员工Id
     */
    private String empId;
    /**
     * 角色Id
     */
    private String roleId;
    /**
     * 员工名称，为了读取
     */
    private   String empName; 
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
