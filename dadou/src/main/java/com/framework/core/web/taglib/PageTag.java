package com.framework.core.web.taglib;

import com.framework.core.page.Pagination;
import com.framework.core.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * 分页标签
 * 
 * @since 2013/1/31
 */
@SuppressWarnings("serial")
public class PageTag extends TagSupport {

    /**
     * 默认显示在表格下面的页码数量
     */
    public static final int DISPLAY_PAGE_NUM = 7;
    /**
     * 取中
     */
    public static final int MIDDLE_NUM = (DISPLAY_PAGE_NUM%2 == 0)? (DISPLAY_PAGE_NUM/2) : DISPLAY_PAGE_NUM / 2 + 1;
    /**
     * <li>表示页码的参数名,默认情况下为p</li> 
     * <li>用户可以自行设置,可以在一个界面中实现多个分页查询,如果使用不同的Action时,需要设置不同的参数名称</li>
     */
    public String pageNumberParam = "p";
    /**
     * 默认查询Form的id 在多查询界面时,可以采用多个form的形式
     */
    private String queryForm = "queryForm";
    /**
     * 传递url的情况,url与form不能同时使用
     */
    private String url = "";
    /**
     * 列表页面Id,可以自己设定名称
     */
    private String listDiv = "listDiv";
    /**
     * Pagination对象的Attribute名
     */
    private String name = "pagination";
    /**
     * 是否启用Ajax查询方式 默认采用ajax查询的方式 如果为false,则同步查询
     */
    private boolean isAjax = true;
    /**
     * 开始标签
     */
    @SuppressWarnings("unchecked")
    @Override
    public int doStartTag() throws JspException {
        StringBuilder output = new StringBuilder();
        // 获取分页对象
        Pagination pagination = (Pagination) pageContext.findAttribute(name);
        // 开始部分
        output.append("<div class='pagination'>")
              .append("<span style='padding-right:10px;line-height:35px;'><font class='green'>")
              .append(pagination.getMaxElements())
              .append("</font>条&nbsp;/&nbsp;")
              .append("<font class='green'>")
              .append(pagination.getMaxPages())
              .append("</font>页</span>");
        // 页码开始部分
        output.append("<span>").append("<ul>");

        // 内容处理
        output.append(handlePage(pagination));

        // 页码结束部分
        output.append("</span>").append("</ul>");

        // 结束标签
        output.append("</div>");

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
    private String handlePage(Pagination pagination) {
        StringBuilder sb = new StringBuilder(1000);
        if (pagination.getMaxElements() == 0)
            return "<span style='line-height:35px;'>没有符合条件的记录</span>";

        // 获取最大页码
        int maxPageNo = pagination.getMaxPages();
        // 获取当前页码
        int pageNo = pagination.getPageNumber();
        if (isAjax) {
            // 1.页数少于默认显示页的情况,没有上一页、下一页,只有两个箭头
            if (maxPageNo < DISPLAY_PAGE_NUM) {
                for (int i = 1; i <= maxPageNo; i++) {
                    // 判断当前选中状态
                    String classStr = pageNo == i ? " class='active'" : "";
                        sb.append("<li" + classStr + ">");
                        if(pageNo != i){
                        	sb.append("<a href=\"javascript:{ExamBase.getPage('" + i + "','"+ (StringUtils.isEmpty(url)?queryForm:url) + "','"+listDiv+"')}\">");// 传递页码及查询表单
                        }else{
                        	sb.append("<a href=\"javascript:void(0)\">");// 传递页码及查询表单
                        }
                        sb.append(i)
                          .append("</a>")
                          .append("</li>");

                }
            } else {
                // 开始页码
                int fromPageNo = pageNo - MIDDLE_NUM + 1 <= 0 ? 1 : pageNo
                        - MIDDLE_NUM + 1;
                if ((fromPageNo + DISPLAY_PAGE_NUM - 1) >= maxPageNo) {
                    fromPageNo = maxPageNo - DISPLAY_PAGE_NUM + 1;
                }
                // 结束页码
                int toPageNo = fromPageNo + DISPLAY_PAGE_NUM - 1;
                if ((toPageNo - MIDDLE_NUM + 1) > maxPageNo) {
                    toPageNo = maxPageNo;
                }
                if (pageNo > 1) {
                    // 首页、
                        sb.append("<li><a href=\"javascript:{ExamBase.getPage('" + 1 + "','"+ (StringUtils.isEmpty(url)?queryForm:url) + "','"+listDiv+"')}\">首页")
                          .append("</a></li>");
                    //上一页
                        sb.append("<li><a href=\"javascript:{ExamBase.getPage('" + (pageNo-1) + "','"+ (StringUtils.isEmpty(url)?queryForm:url) + "','"+listDiv+"')}\">«上一页</a></li>")
                          .append("</a></li>");
                }
                for (int i = fromPageNo; i <= toPageNo; i++) {
                    // 判断当前选中状态
                    String classStr = pageNo == i ? " class='active'" : "";
                        sb.append("<li" + classStr + ">");
                        	/* style=\"color: black;\" */
                          if(pageNo != i){
                          	sb.append("<a style=\"color: black;\" href=\"javascript:{ExamBase.getPage('" + i + "','"+ (StringUtils.isEmpty(url)?queryForm:url) + "','"+listDiv+"')}\">");// 传递页码及查询表单
                          }else{
                          	sb.append("<a href=\"javascript:void(0)\">");// 传递页码及查询表单
                          }
                          sb.append(i)
                          .append("</a>")
                          .append("</li>");

                }
                if (pageNo < maxPageNo&& maxPageNo>1) {
                    //下一页
                        sb.append("<li><a href=\"javascript:{ExamBase.getPage('" + (pageNo+1) + "','"+ (StringUtils.isEmpty(url)?queryForm:url) + "','"+listDiv+"')}\">下一页»</a></li>")
                          .append("</a></li>");
                    //尾页
                        sb.append("<li><a href=\"javascript:{ExamBase.getPage('" + maxPageNo + "','"+ (StringUtils.isEmpty(url)?queryForm:url) + "','"+listDiv+"')}\">尾页")
                          .append("</a></li>");
                }
            }
        } else {
           //处理非Ajax的情况
        	url = url + "&p=";
            // 1.页数少于默认显示页的情况,没有上一页、下一页,只有两个箭头
            if (maxPageNo < DISPLAY_PAGE_NUM) {
                for (int i = 1; i <= maxPageNo; i++) {
                    // 判断当前选中状态
                    String classStr = pageNo == i ? " class='active'" : "";
                        sb.append("<li" + classStr + ">");
                        if(pageNo != i){
                        	sb.append("<a href=\"" + url + i + "\">");// 传递页码及查询表单
                        }else{
                        	sb.append("<a href=\"javascript:void(0)\">");// 传递页码及查询表单
                        }
                        sb.append(i)
                          .append("</a>")
                          .append("</li>");

                }
            } else {
                // 开始页码
                int fromPageNo = pageNo - MIDDLE_NUM + 1 <= 0 ? 1 : pageNo
                        - MIDDLE_NUM + 1;
                if ((fromPageNo + DISPLAY_PAGE_NUM - 1) >= maxPageNo) {
                    fromPageNo = maxPageNo - DISPLAY_PAGE_NUM + 1;
                }
                // 结束页码
                int toPageNo = fromPageNo + DISPLAY_PAGE_NUM - 1;
                if ((toPageNo - MIDDLE_NUM + 1) > maxPageNo) {
                    toPageNo = maxPageNo;
                }
                if (pageNo > 1) {
                    // 首页、
                        sb.append("<li><a href=\"" + url + 1 + "\">首页")
                          .append("</a></li>");
                    //上一页
                        sb.append("<li><a href=\"" + url + (pageNo-1) + "\">«上一页</a></li>")
                          .append("</a></li>");
                }
                for (int i = fromPageNo; i <= toPageNo; i++) {
                    // 判断当前选中状态
                    String classStr = pageNo == i ? " class='active'" : "";
                        sb.append("<li" + classStr + ">");
                        	/* style=\"color: black;\" */
                          if(pageNo != i){
                          	sb.append("<a style=\"color: black;\" href=\"" + url + i + "\">");// 传递页码及查询表单
                          }else{
                          	sb.append("<a href=\"javascript:void(0)\">");// 传递页码及查询表单
                          }
                          sb.append(i)
                          .append("</a>")
                          .append("</li>");

                }
                if (pageNo < maxPageNo&& maxPageNo>1) {
                    //下一页
                        sb.append("<li><a href=\"" + url + (pageNo+1) + "\">下一页»</a></li>")
                          .append("</a></li>");
                    //尾页
                        sb.append("<li><a href=\"" + url + maxPageNo + ">尾页")
                          .append("</a></li>");
                }
            }
        
        }
        return sb.toString();
    }

    public String getPageNumberParam() {
        return pageNumberParam;
    }

    public void setPageNumberParam(String pageNumberParam) {
        this.pageNumberParam = pageNumberParam;
    }

    public String getQueryForm() {
        return queryForm;
    }

    public void setQueryForm(String queryForm) {
        this.queryForm = queryForm;
    }

    public String getListDiv() {
        return listDiv;
    }

    public void setListDiv(String listDiv) {
        this.listDiv = listDiv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAjax() {
        return isAjax;
    }

    public void setIsAjax(boolean isAjax) {
        this.isAjax = isAjax;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}