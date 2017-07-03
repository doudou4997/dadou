package com.dadou.sys.tree.service;

import com.dadou.sys.CmsConst;
import com.dadou.sys.emp.pojos.UserMenu;
import com.dadou.sys.role.pojos.RoleMenu;
import com.dadou.sys.tree.pojos.MenuItem;
import com.dadou.sys.tree.pojos.TreeNode;
import com.framework.core.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * 树形结构实用类
 * @author gaof
 *
 */
public final class  TreeUtils{
    /**
     * 根节点json的键值
     */
    public static final String ROOT_NODE = "root_node";
    /**
     * 日志记录器
     */
    private static Log logger = LogFactory.getLog(TreeUtils.class);
    
    // ////////////////////////////////////////////////////////
    // // 用于菜单与权限的各种操作,包括分配权限
    // // 管理树形结构
    // ///////////////////////////////////////////////////////
    public String buildManageTree(List<MenuItem> list, boolean recursive) {
        
        return "";
    }

    // /////////////////////////////////////////////
    // /创建导航树结构,动态生成左侧导航
    // /////////////////////////////////////////////
    /**
     * 创建树形结构
     * 登录员工
     * 打开目标
     * @return
     */
    public static  TreeNode buildNavTree(Set<RoleMenu> sysMenu, Set<UserMenu> userMenu) {
        /**
         * 找到根节点,easyUI tree默认的json第一个节点为树节点,所以需要首先确定根节点
         * TODO 待优化
         */
        TreeNode rootNode = new TreeNode();
        for(RoleMenu rm : sysMenu){
        	String pId = rm.getParentId();
            if(CmsConst.MENU_ROOT_ID.equals(pId)){
                if (logger.isDebugEnabled()) {
                    logger.debug("菜单Id" + rm.getMenuId() + "的节点设置为root");
                }
                rootNode.setId(rm.getMenuId());
                rootNode.setText(rm.getMenuName());
                rootNode.setIconCls(rm.getIcon());
                rootNode.setSortNum(rm.getSortNum());
                rootNode.setLevel(rm.getLevel());
                //添加URL
                rootNode.getAttributes().put("url", rm.getUrl());
                break;
            }
        }
       
        /**
         * 保存所有的菜单，过滤重复的
         */
        Set<TreeNode> allNodes = new HashSet<TreeNode>();
        for (RoleMenu rm : sysMenu) {
            TreeNode node = new TreeNode();
            node.setId(rm.getMenuId());
            node.setParentId(rm.getParentId());
            node.setText(rm.getMenuName());
            node.setSortNum(rm.getSortNum());
            node.setIconCls(rm.getIcon());
            node.setLevel(rm.getLevel());
            node.getAttributes().put("url", rm.getUrl());
            allNodes.add(node);
        }
        /**
         * 保存所有的菜单，过滤重复的
         */
        for (UserMenu um : userMenu) {
            TreeNode node = new TreeNode();
            node.setId(um.getMenuId());
            node.setParentId(um.getParentId());
            node.setText(um.getName());
            node.setIconCls(um.getIconOpen());
            node.setSortNum(um.getSortNum());
            //node.setLevel(rm.getLevel());
            node.getAttributes().put("url", um.getUrl());
            allNodes.add(node);
        }
        
        //按sortNum排序
        ArrayList<TreeNode> allNodesList = new ArrayList<TreeNode>();
        allNodesList.addAll(allNodes);
        Collections.sort(allNodesList);
        
        /**
         * 构造递归树
         */
        buildTree(rootNode, allNodesList);
        return rootNode;
    }
    
    /**
     * 递归构造树 为角色分配权限
     * parent
     * menuItemList
     */
    private static void buildTree(TreeNode root, ArrayList<TreeNode> allNodes) {
        //循环取得parent的子节点
        Iterator<TreeNode> iterator = allNodes.iterator();
        while(iterator.hasNext()) {
            TreeNode node = iterator.next();
            if(node.getParentId().equals(root.getId())) {
                root.getChildren().add(node);
                //删除集合中节点
                iterator.remove();
            } 
        }
        //全部循环完毕
        if(allNodes.size() == 0) {
            return;
        }
        //递归所有子节点
        if(root.getChildren().size()>0) {
            List<TreeNode> children = root.getChildren();
            for(TreeNode subNode : children) {
                buildTree(subNode, allNodes);
            }
        }
    }
   
    
    /**
	 * 创建树形结构
	 * @param menuItemList 要组装的MenuItem
	 * @param rootId  根节点Id
	 * @return
	 */
	public static TreeNode createTree(List<MenuItem> menuItemList, String rootId) {
		//知道根节点，构造树
		TreeNode root = new TreeNode();
		Iterator<MenuItem> iterator = menuItemList.iterator();
		while(iterator.hasNext()) {
			MenuItem item = iterator.next();
			String parentId = StringUtils.valueOf(item.getParentId());
			if(parentId.equals(rootId)) {
				root.setId(item.getId());
				root.setText(item.getName()); 
				root.getAttributes().put("type", item.getType());
				root.setIconCls(item.getIcon());
				
				iterator.remove();
				buildTree(root, menuItemList);
				break;
			}
		}
		
		return root;
	}
	
	/**
	 * 递归构造树
	 * @param parent
	 * @param menuItemList
	 */
	public static void buildTree(TreeNode parent, List<MenuItem> menuItemList) {
		//循环取得parent的子节点
		Iterator<MenuItem> iterator = menuItemList.iterator();
		while(iterator.hasNext()) {
			MenuItem item = iterator.next();
			//避免Oracle的NULL
			String parentId = StringUtils.valueOf(item.getParentId());
			if(parentId.equals(parent.getId())) {
				if(parent.getChildren() == null) {
					parent.setChildren(new ArrayList<TreeNode>());
				}
				
				TreeNode node = new TreeNode();
				node.setId(item.getId());
				node.setText(item.getName());
				node.setIconCls(item.getIcon());
				node.getAttributes().put("type", item.getType());
				parent.getChildren().add(node);
				
				iterator.remove();
			} 
		}
		
		//全部循环完毕
		if(menuItemList.size() == 0) {
			return;
		}
		
		if(parent.getChildren()!=null && parent.getChildren().size()>0) {
			List<TreeNode> children = parent.getChildren();
			Iterator<TreeNode> iterator2 = children.iterator();
			while(iterator2.hasNext()) {
				TreeNode tn = iterator2.next();
				buildTree(tn, menuItemList);
			}
		}
	}
}
