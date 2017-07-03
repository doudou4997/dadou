package com.dadou.sys.dic.service;

import com.dadou.sys.dic.dao.DictionaryDao;
import com.dadou.sys.dic.pojos.Dictionary;
import com.framework.core.cache.CacheConst;
import com.framework.core.cache.CacheUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;

/**
 * 数据字典业务层
 * @author wangs
 *
 */
@Service(value = "im_dictionaryService")
public class DictionaryService {
	//缓存所有数据字典
	public static Map<String, Map<String, String>> DICTINARY_MAP = new HashMap<>();
	
	
	@Resource(name = "im_dictionaryDao")
	private DictionaryDao dictionaryDao;

	/**
	 * 查询数据字典（模糊查询）
	 */
	public List<Dictionary> findDictList(String typeName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeName", typeName);
        return dictionaryDao.findList("findGroupByTypeName",map);
	}
	
	/**
	 * 通过typeName查找所有的孩子信息，并且和父信息关联起来
	 * @param typeNameList
	 * @return
	 */
	public List<Dictionary> findByTypeName(List<Dictionary> typeNameList){
		List<Dictionary> allList = new ArrayList<Dictionary>();
		for (Dictionary dic : typeNameList) {
			//把typeName难进去查找相对应的内容，给他附上孩子节点的表示_parentid
			String typeName = dic.getTypeName();
			List<Dictionary> chilList = dictionaryDao.findList("findByTypeName",typeName);
			for (Dictionary d : chilList) {
				d.set_parentId(dic.getId());
			}
			allList.addAll(chilList);
		}
		return allList;
	}
	
	/**
	 * 通过实际值和值类型查找，校验数据是否存在
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Dictionary findByKeyAndType(String key,String type){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("key", key);
		map.put("type", type);
		Dictionary re = (Dictionary) dictionaryDao.findOne("findByKeyAndType", map);
		return re;
	}
	public int save(Dictionary dict) throws Exception{
		int result = dictionaryDao.save(dict);
		//更新缓存
		doInitOrRefreshCache();
		return result;
	}
	
	public void removeById(String id) throws Exception{
		dictionaryDao.removeById(id);
		//更新缓存
		doInitOrRefreshCache();
	}
	
	public Dictionary findById(String id){
		return dictionaryDao.findById(id);
	}
	
	public void update(Dictionary dict) throws Exception{
		dictionaryDao.update(dict);
		//更新缓存
		doInitOrRefreshCache();
	}
	/**
	 * 获得所有数据字典数据，返回键值对应的MAP数据格式
	 * @return
	 * @author liuzhaofeng
	 * Create DateTime: 2016年11月8日 下午5:17:53
	 * @deprecated 采用缓存,解决Session问题
	 */
	public Map<String, Map<String, String>> getAllMap() {
		Map<String,Map<String,String>>  rMap=new HashMap<String,Map<String,String>>();
		//获得所有数据－－按照类型、键排序
		List<Dictionary> list= dictionaryDao.findAll();
		for(Dictionary d:list){
			Map<String,String> map;
			map = rMap.get(d.getType());
			if(map==null){
				map=new HashMap<String,String>();
				rMap.put(d.getType(), map);
			}
			map.put(d.getKey(), d.getValue());
		}
		return rMap;
	}

	/**
	 * 根据类型查询
	 * @param type
	 * @author 赵春晖
	 * Create DateTime: 2016年12月2日 下午9:29:13
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> findByType(String type) {
		TreeSet<Dictionary> dictionarySet = (TreeSet<Dictionary>) CacheUtils.get(CacheConst.DICTIONARY_TYPE+type, CacheConst.DICTIONARY_CACHE);
		List<Dictionary> list = new ArrayList<>();
		list.addAll(dictionarySet);
		return list;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getDictionaryMap(String type){
		Map<String, String> resultMap = new HashMap<>();
    	TreeSet<Dictionary> dictionarySet = (TreeSet<Dictionary>) CacheUtils.get(CacheConst.DICTIONARY_TYPE+type, CacheConst.DICTIONARY_CACHE);
    	for(Dictionary dict : dictionarySet){
    		resultMap.put(dict.getKey(), dict.getValue());
    	}
    	return resultMap;
	}
	/**
	 * 初始化缓存或刷新缓存
	 * 如果有更新都要重新刷新缓存
	 */
	public void doInitOrRefreshCache() {
		//清空原来缓存
        CacheUtils.clean( CacheConst.DICTIONARY_CACHE);
		// 存放type和对应的集合
		Map<String,TreeSet<Dictionary>> resultMap = new HashMap<>();
		// 获取所有的Dictionary
		List<Dictionary> list = dictionaryDao.findAll();
		if (list != null && list.size() != 0) {
			for (Dictionary dict : list) {
				TreeSet<Dictionary> treeSet = resultMap.get(CacheConst.DICTIONARY_TYPE+dict.getType());
				if(treeSet == null){
					treeSet = new TreeSet<>();
					resultMap.put(CacheConst.DICTIONARY_TYPE+dict.getType(), treeSet);
				}
				treeSet.add(dict);
			}
		}
		// 依次保存到缓存中
		for (Entry<String, TreeSet<Dictionary>> entry : resultMap.entrySet()) {
			 CacheUtils.add(entry.getKey(), entry.getValue(),
					 CacheConst.DICTIONARY_CACHE);
		}

	}
}
