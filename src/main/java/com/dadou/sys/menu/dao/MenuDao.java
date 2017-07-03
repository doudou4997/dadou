package com.dadou.sys.menu.dao;

import com.dadou.sys.menu.pojos.Menu;
import com.dadou.sys.tree.pojos.MenuItem;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import com.framework.core.utils.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 菜单Dao实现类
 *
 */
@Repository(value="sys_menuDao")
public class MenuDao extends GenericMybatisDao<Menu, String> {
	/**
     * 删除菜单同时先删除RoleMenu和UserMenu的级联数据
     * @param menuId
     * @return
     */
	public void remove(String menuId) {
		//删除t_menu及t_role_menu 和 t_user_menu 关联表(先删除关联表信息)
		// 删除RoleMenu表信息
		this.remove("removeRoleMenuByMenuId", menuId);
		// 删除UserMenu表信息
		this.remove("removeUserMenuByMenuId", menuId);
		this.removeById(menuId);
	}
	/**
	 * 更新菜单
	 */
	public void updateMenu(Menu menu) {
		this.update(menu);
	}
    /**
     * 适合所有数据库类型
     * @param menuId
     * @return
     */
    public List<MenuItem> getMenuAndPrivileges(String menuId, String sysId) {
        //构造Sql
        List list  = this.findList("getMenuAndPrivileges",sysId);
        // 结果列表
        List<Map<String, Object>> mapList = list;
        //构造MenuItem数组
        Set<MenuItem> menuItemList = new TreeSet<MenuItem>();
        //封装数据
        //Oracle都是大写情况
        for (Map<String, Object> map : mapList) {
            MenuItem item = new MenuItem();
            item.setId(StringUtils.valueOf(map.get("id")));
            item.setName(StringUtils.valueOf(map.get("name")));
            item.setParentId(StringUtils.valueOf(map.get("parent_id")));
            Object sortNum = map.get("sortnum");
            int sortNum_ = NumberUtils.toInt(StringUtils.valueOf(sortNum), 1);
            item.setSortNum(sortNum_);
            
            Object type = map.get("type");
            int _type = NumberUtils.toInt(StringUtils.valueOf(type),1);
            item.setType(_type);
            //icon
            String icon = StringUtils.valueOf(map.get("icon"));
            item.setIcon(icon);
            //为操作权限时,为叶子节点
            if(_type == 2){
                 item.setLeaf(true);
            }
                
            menuItemList.add(item);
        }
        //通过递归的方式设置是否叶子节点
        trace(menuItemList,0,menuId);
        //返回所有节点        
        List<MenuItem> result = new ArrayList<MenuItem>();
        result.addAll(menuItemList);
        return result;
    }
    
    /**
     * 递归查询所有子节点
     * @param menuId
     * @return
     * 
     * 使用：先初始化成员变量menuAll、privilegeAll
     */
    private void trace(Set<MenuItem> menuItemList, int level, String menuId) {
        // 默认层次为0
        level++;
        for (MenuItem mi : menuItemList) {

            if (mi.getParentId().equals(menuId)) {
                // 只对菜单进行操作
                if (mi.getType() == 1) {
                    // 设置当前展开层次
                    mi.setLevel(level);
                    String id = mi.getId();
                    if (hasChildren(id, menuItemList)) {
                        trace(menuItemList, level, mi.getId());
                    } else {
                        // 判断是否有权限节点
                        if(!hasPChildren(id,menuItemList,level)){
                            mi.setLeaf(true);
                        }
                        
                    }

                }

            }
        }

    }
    /**
     * 判断menuId是否有直接子节点
     * 
     * menuId
     * listAll
     */
    private boolean hasChildren(String menuId, Set<MenuItem> menuItemList) {
        boolean has = false;
        for(MenuItem mi : menuItemList){
            //只对菜单进行操作
            if(mi.getType() == 1){
                if(mi.getParentId().equals(menuId)) {
                    has = true;
                    break;
                }
            }
        }
        return has;
    }
    /**
     * 判断是否有权限节点
     * 
     * menuId
     * istAll
     */
    private boolean hasPChildren(String menuId, Set<MenuItem> menuItemList, int level) {
        boolean has = false;
        for(MenuItem mi : menuItemList){
            //只对菜单进行操作
            if(mi.getType() == 2){
                if(mi.getParentId().equals(menuId)) {
                    mi.setLevel(level+1);
                    has = true;
                    break;
                }
            }
            
        }
        return has;
    }
}
