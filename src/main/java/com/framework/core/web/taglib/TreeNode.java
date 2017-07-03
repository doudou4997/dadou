package com.framework.core.web.taglib;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 属性节点(菜单、权限)
 * 
 * @author Administrator
 * 
 */
public class TreeNode implements Serializable, Comparable<TreeNode> {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Integer id;
    /**
     * 父节点Id
     */
    private Integer parentId;
    /**
     * 节点文本
     */
    private String text;
    /**
     * 是否具有复选框
     */
    private boolean checked;
    /**
     * 图标
     */
    private String iconCls;
    /**
     * 开或闭状态
     */
    private String state;
    /**
     * 子节点
     */
    private List<TreeNode> children = new ArrayList<TreeNode>(0);
    /**
     * 属性集合
     */
    private Map<String, Object> attributes = new HashMap<String, Object>();
    /**
     * 排序字段
     */
    private Integer sortNum;
    /**
     * 级别
     */
    private Integer level;

    // ////////////////////
    // get/set方法
    // /////////////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if (!(other instanceof TreeNode))
            return false;
        TreeNode castOther = (TreeNode) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public int compareTo(TreeNode o) {
        if (sortNum == null) {
            if (o.sortNum == null)
                return 0;
            return -1;
        }
        if (o.sortNum == null) {
            if (sortNum == null)
                return 0;
            return 1;
        }
        return sortNum.compareTo(o.sortNum);
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getIconCls() {
        return iconCls;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
