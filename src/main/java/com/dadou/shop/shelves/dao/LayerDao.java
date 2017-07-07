package com.dadou.shop.shelves.dao;

import com.dadou.shop.shelves.pojos.Layer;
import com.dadou.shop.shelves.pojos.LayerGoods;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 货架管理
 */
@Repository("layerDao")
public class LayerDao extends GenericMybatisDao<Layer, String> {


    /**
     * 删除之前的上架信息
     * @param id_layer
     */
    public void removeGoodsOfLayer(String id_layer) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id_layer", id_layer);
        this.remove("removeGoodsOfLayer", params);
    }
    /**
     * 上架
     * @param id_layer
     * @param goodsIds
     */
    public void doAddGoods(String id_layer, String[] goodsIds) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        List<LayerGoods> layerGoodsList = new ArrayList<>();
        for(String goodsId:goodsIds){
            LayerGoods layerGoods = new LayerGoods();
            layerGoods.setId_layer(id_layer);
            layerGoods.setId_goods(goodsId);
            layerGoodsList.add(layerGoods);
        }
        params.put("layerGoods", layerGoodsList);
        if(params.size() > 0){
            this.save("batchSaveLayerGoods", params);
        }
    }
}
