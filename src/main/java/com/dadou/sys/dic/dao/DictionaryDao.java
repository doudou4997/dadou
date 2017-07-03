package com.dadou.sys.dic.dao;

import com.dadou.sys.dic.pojos.Dictionary;
import com.framework.core.daos.mybatis.GenericMybatisDao;
import org.springframework.stereotype.Repository;

/**
 * 数据字典
 * @author wangs
 *
 */
@Repository(value="im_dictionaryDao")
public class DictionaryDao extends GenericMybatisDao<Dictionary, String> {


}