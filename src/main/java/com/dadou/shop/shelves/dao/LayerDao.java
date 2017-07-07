package com.dadou.shop.shelves.dao;

import com.dadou.shop.shelves.pojos.Layer;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import org.springframework.stereotype.Repository;

/**
 * 货架管理
 */
@Repository("layerDao")
public class LayerDao extends GenericMybatisDao<Layer, String> {

}
