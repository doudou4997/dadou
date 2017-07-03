package com.framework.core.page;

import java.util.ArrayList;
import java.util.List;

/**
 *分页类，进行分页。
 */
public class Pagination<T> {
    //页大小
    private int pageSize = 20;
    //查询的页码
    private int pageNumber = 1;
    //最大页数
    private int maxPages;
    //最大记录数
    private int maxElements;
    //列表
    private List<T> list = new ArrayList<T>(0);

    //构造方法
    public Pagination() {
    	
    }
    public Pagination(List<T> l,int pageNo,int pageSize){
    	this.list.addAll(l);
        setPageNumber(pageNo);
        // 设置最大页数
        setPageSize(pageSize);
        // 设置符合条件的最大记录数
        setMaxElements(this.list.size());
    }
    /**
     * 设置最大记录数。
     * 
     * @param maxElements
     * 			最大记录数
     */
    public void setMaxElements(int maxElements) {
        this.maxElements = maxElements;
        //设置最大页数
        setMaxPages();
    }
    
    /**
     * 获取最大记录数。
     * 
     * @return 最大记录数
     */
    public int getMaxElements() {
        return maxElements;
    }

    /**
     * 设置最大页数。
     */
    public void setMaxPages() {
        if (maxElements != 0 && (maxElements % pageSize == 0)) {
            maxPages = maxElements / pageSize;
        } else {
            maxPages = maxElements / pageSize + 1;
        }
        // 判断当前页码是否超出范围
        if (pageNumber > maxPages) {
            this.pageNumber = maxPages;
        }
    }

    /**
     * 获取最大页数。
     * 
     * @return 最大页数
     */
    public int getMaxPages() {
        return maxPages;
    }

    /**
     * 设置页码。
     * 
     * @param pageNumber
     * 			页码
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 获取当前页码。
     * 
     * @return 当前页码
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置页大小。
     * 
     * @param pageSize
     * 			页大小
     */
    public void setPageSize(int pageSize) {
        if(pageSize > 0){
            this.pageSize = pageSize;
        }       
    }

    /**
     * 获取页大小。
     * 
     * @return 页大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取记录列表。
     * 
     * @return 记录列表
     */
    public List<T> getList() {
        return list;
    }

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
