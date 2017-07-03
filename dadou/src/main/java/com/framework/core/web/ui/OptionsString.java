package com.framework.core.web.ui;
/**
 * 封装option的键和值,可用于单选按钮和复选按钮
 * @author gaofeng
 *
 */
public class OptionsString implements Comparable<OptionsString>{
    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;
    /**
     * 排序
     */
    private int sortNum;
    
    public OptionsString() {

    }

    public OptionsString(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public OptionsString(String key, String value,int sortNum) {
        this.key = key;
        this.value = value;
        this.sortNum = sortNum;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public int compareTo(OptionsString os) {
		int v1 = this.sortNum;
		int v2 = os.sortNum;
		if(v1 > v2){
			return 1;
		}else if(v1 == v2) {
			return 0;
		}else{
			return -1;
		}
	}
    
}
