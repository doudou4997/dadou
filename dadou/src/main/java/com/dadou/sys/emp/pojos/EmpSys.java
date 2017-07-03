package com.dadou.sys.emp.pojos;

public class EmpSys {
	/**
     * 主键Id
     */
    private Integer id;
    /**
     * 员工Id
     */
    private Integer empId;
    /**
     * 子系统Id
     */
    private Integer sysId;
    ///////////////////////////////////////
	/////getter/setter方法
	///////////////////////////////////////
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public Integer getSysId() {
		return sysId;
	}
	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}
    

}
