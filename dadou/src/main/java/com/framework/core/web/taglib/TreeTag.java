package com.framework.core.web.taglib;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * 动态生成树形结构
 */
@SuppressWarnings("serial")
public class TreeTag extends TagSupport {
    /**
     * RootNode对象的Attribute名
     */
    private String name = "rootNode";
    /****
     * 回车换行
     */
    private String line;
    /**
     * 上下文路径
     */
    private String ctx;
    /**
     * 开始标签
     */
    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        StringBuilder output = new StringBuilder();
    	ctx =  pageContext.getServletContext().getContextPath();
        // 获取分页对象
        TreeNode rootNode = (TreeNode) pageContext.findAttribute(name);
        line =  System.getProperty("line.separator");
        // 内容处理
        output.append(handlePage(rootNode));
        try {
            pageContext.getOut().println(output.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

    /**
     * 处理分页部分
     * 
     * @param pageNo
     * @param pageSize
     * @param count
     * @param url
     * @return
     */
    @SuppressWarnings("unchecked")
    private String handlePage(TreeNode rootNode) {
    
        StringBuilder sb = new StringBuilder(1000);
        if(rootNode == null) return "";
        //默认添加头部
        sb.append("<li class=\"nav-header\">"+line)
          .append(" <div class=\"dropdown profile-element\">"+line)
          .append("  <span><img alt=\"image\" class=\"img-circle\" src=\""+ctx+"/styles/img/profile_small.jpg\" /></span>"+line)
          .append("  <span class=\"clear\">"+line)
          .append("    <span class=\"block m-t-xs\"><strong class=\"font-bold\">Admin</strong></span>"+line)
          .append("  </span>"+line)
          .append(" </div>"+line)
          .append(" <div class=\"logo-element\">ST </div>"+line)
          .append("</li>"+line);
        
        //
        for(TreeNode node : rootNode.getChildren()){
        	String url = node.getAttributes().get("url").toString();
        	boolean newWindow = false;
        	if (url.startsWith("newWindow:")) {
        	    newWindow = true;
        	    url = url.substring("newWindow:".length());
        	}
        	
        	
        	if(!"#".equals(url)){
        		url = ctx + url;
        	}else{
        		url = "";
        	}
        	
        	
        	
        	if(node.getChildren().size()>0){
        	   //递归处理子菜单
        	   recursion(node, sb);
        	}else{
        	  sb.append("<li title=\""+node.getText()+"\">"+line);
        	  if (newWindow)
        	      sb.append("  <a target=\"_blank\" href=\""+url+"\"><i class=\""+node.getIconCls()+"\"></i> <span class=\"nav-label\">"+node.getText()+"</span></a>"+line);
              else
        	  sb.append("  <a class=\"J_menuItem\" href=\""+url+"\"><i class=\""+node.getIconCls()+"\"></i> <span class=\"nav-label\">"+node.getText()+"</span></a>"+line);
        	  sb.append("</li>"+line);
        	}
        }
         //递归处理生成树形菜单
        return sb.toString();
    }
    /**
     * 递归生成树形结构
     * @return
     */
    private void recursion(TreeNode node, StringBuilder sb){
    	 if(node.getChildren().size()>0){
    		/*
             <li>
             <a href="#">
                  <i class="fa fa-eye"></i> 
                  <span class="nav-label">生产许可</span>
                  <span class="fa arrow"></span>
             </a>
             <ul class="nav nav-second-level">
                 <li>
                     <a href="#">许可证审批 <span class="fa arrow"></span></a>
                     <ul class="nav nav-third-level">
                         <li><a class="J_menuItem" href="${ctx }/cert/list/1">食品生产</a></li>
                         <li><a class="J_menuItem" href="${ctx }/cert/list/2">食品流通</a></li>
                         <li><a class="J_menuItem" href="${ctx }/cert/list/3">餐饮服务</a></li>
                     </ul>
                 </li>
                 <li><a class="J_menuItem" href="${ctx}/warning/list">许可证预警</a>
                 </li>
                 <li><a class="J_menuItem" href="${ctx}/cert/search">许可证查询</a>
                 </li>
             </ul>
         </li>*/
    		 //当前节点
			sb.append("<li>"+line);
			//第一层
			if(node.getLevel() == 1){
				sb.append("<a href=\"#\">"+line);
				String icon = StringUtils.isEmpty(node.getIconCls())?"fa fa-eye":node.getIconCls();
				sb.append("   <i class='"+icon+"'></i>"+line);
				sb.append("   <span class='nav-label'>"+node.getText()+"</span> "+line);
				sb.append("   <span class='fa arrow'></span>"+line);
				sb.append("</a>");
				sb.append("<ul class=\"nav nav-second-level\">");
			}
			//第二层
			if(node.getLevel() == 2){
				sb.append("<a href=\"#\">"+node.getText()+"<span class=\"fa arrow\"></span></a>");
				sb.append("<ul class=\"nav nav-third-level\">");
			}
			   
			for(TreeNode subNode : node.getChildren()){
				  
				  if(subNode.getChildren().size() == 0){
	    				String url = subNode.getAttributes().get("url").toString();            
	    				boolean newWindow = false;
	    	            if (url.startsWith("newWindow:")) {
	    	                newWindow = true;
	    	                url = url.substring("newWindow:".length());
	    	            }
	    				if("#".equals(url)){
	    					url = "";
	    				}else{
	    					url = ctx+url;
	    				}
	    			//叶子节点
	    	             if (newWindow)
	    	                  sb.append("<li><a target=\"_blank\" href="+url+">"+subNode.getText()+"</a></li>");
	    	              else
	    	                  sb.append("<li><a class=\"J_menuItem\" href="+url+">"+subNode.getText()+"</a></li>");
				  }else{
					 //继续递归
					  recursion(subNode, sb);
				  }
			  }
			sb.append("</ul>");
			sb.append("</li>");
    	}
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}