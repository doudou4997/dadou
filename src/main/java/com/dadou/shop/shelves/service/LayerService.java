package com.dadou.shop.shelves.service;

import com.dadou.shop.shelves.dao.LayerDao;
import com.dadou.shop.shelves.pojos.Layer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dadou on 2017/7/5.
 * 货架管理
 */

@Service("layerService")
public class LayerService {
    @Resource(name = "layerDao")
    private LayerDao layerDao;

    /**
     * 根据货柜信息获取货架信息
     *
     * @param shelvesId 货柜ID
     * @return
     */
    public List<Layer> findByPid(String shelvesId) {
        List<Layer> returnList = layerDao.findList("findByPId", shelvesId);
        return returnList;
    }


}
