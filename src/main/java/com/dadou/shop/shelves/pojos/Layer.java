package com.dadou.shop.shelves.pojos;

/**
 * Created by dadou on 2017/7/5.
 */
public class Layer{
    /**
     * 货架id
     */
    private String id;
    /**
     * 货架id
     */
    private String id_shelves;
    /**
     * 货架序号
     */
    private String layerIndex;
    /**
     * 货架别名
     */
    private String layerName;
    /**
     * 删除标识
     */
    private String deleteflag;
    private String inserttime;
    private String insertemp;
    private String updatetime;
    private String updateemp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_shelves() {
        return id_shelves;
    }

    public void setId_shelves(String id_shelves) {
        this.id_shelves = id_shelves;
    }

    public String getLayerIndex() {
        return layerIndex;
    }

    public void setLayerIndex(String layerIndex) {
        this.layerIndex = layerIndex;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getInsertemp() {
        return insertemp;
    }

    public void setInsertemp(String insertemp) {
        this.insertemp = insertemp;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateemp() {
        return updateemp;
    }

    public void setUpdateemp(String updateemp) {
        this.updateemp = updateemp;
    }
}
