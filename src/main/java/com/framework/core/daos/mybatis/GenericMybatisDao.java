package com.framework.core.daos.mybatis;

import com.framework.core.daos.IGenericDao;
import com.framework.core.page.Pagination;
import com.framework.core.paginator.domain.PageBounds;
import com.framework.core.paginator.domain.PageList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层DAO的通用实现类。<br>
 * 数据访问层的所有自定义DAO实现在创建时都要继承该类，并实现自定义的DAO接口。
 * <p>
 * 
 * Examples:<br>
 * <P>
 * <b>以创建IFooDao接口的实现类FooDaoImpl为例讲解使用方法:</b> <br>
 * 
 * <pre>
 * public class FooDaoImpl extends GenericMyIbatisDao&lt;Foo, String&gt;
 * 	implements IGenericDao {
 * 
 * 构造方法
 * public FooDaoImpl() {
 *    //该方法用于初始化FooDaoImpl中的T类型,该处代码必不可少。
 *    super(Foo.class);
 *    //如果需要设置查询缓存
 *    this.setCacheQueries(true);
 * }
 * 
 * //实现接口的其他方法
 * public void test() {	
 *    //处理具体业务方法
 * }
 * </pre>
 * 
 * 上述代码中，FooDaoImpl的T用实际的类型替代，ID使用该类的标识符属性替代<br>
 * FooDaoImpl继承的方法中的T都会改变为具体的类型Foo，ID都会改变为String
 * <p>
 * 
 * @author gaof
 * @version 1.0
 * @since 2014-01-20
 */
@SuppressWarnings("unchecked")
public abstract class GenericMybatisDao<T, ID extends Serializable> extends
        SqlSessionDaoSupport implements IGenericDao<T, ID> {
	static Log logger = LogFactory.getLog(GenericMybatisDao.class);
	/**
	 * 保持实体对象类的类型
	 */
	private Class<T> entityClass;

	/**
	 * 通过父类获取子类的类型。
	 */
	public GenericMybatisDao() {
		Class<?> c = getClass();
		Type t = c.getGenericSuperclass();
		if (!(t instanceof ParameterizedType)) {
			throw new IllegalArgumentException(c + " 没有指定具体的泛型类型");
		}
		this.entityClass = (Class<T>) ((ParameterizedType) t)
				.getActualTypeArguments()[0];
	}
	@Autowired
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * 获取对象类型。
	 * 
	 * 对象类型
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 获取默认命名空间
	 * 
	 *  
	 */
	public String getSqlMapNameSpace(String method) {
		String m  = method;
		//调用类不包含.的话，则用默认的,包含.的话,则说明传递的全称类名
		if(!method.contains(".")){
			m = this.getClass().getName() + "."+method;
		}
		return m;
	}

	/**
	 * 将对象进行持久化
	 * 
	 * entity
	 *            需要进行持久化操作的实体对象
	 * 持久化的实体对象
	 * 要求插入的属性设置useGeneratedKeys="true" keyProperty指具体的id名称
	 */
	public int save(T entity) {
		if (entity == null) {
			throw new RuntimeException("保存/更新的实体对象为null,请确认！");
		}
		// 插入对象
		return this.getSqlSession().insert(getSqlMapNameSpace(SQLID_SAVE), entity);
	}
	/**
	 * 将对象进行持久化
	 * 
	 * entity
	 *            需要进行持久化操作的实体对象
	 * 持久化的实体对象
	 */
	public void save(String sqlId,Object obj) {
		// 插入对象
		this.getSqlSession().insert(getSqlMapNameSpace(sqlId), obj);
	}
    /**
	 * 批量保存对象
	 */
	public void batchSave(List list) {
		this.getSqlSession().insert(getSqlMapNameSpace(SQLID_BATCHINSERT), list);
	}
	/**
	 * 根据SQLID批量保存对象
	 */
	public void batchSave(String sqlId,List list) {
		this.getSqlSession().insert(getSqlMapNameSpace(sqlId), list);
	}
	/**
	 * 更新对象<br/>
	 * 
	 * entity
	 *            需要进行更新操作的实体对象
	 * 持久化的实体对象
	 */
	public void update(T entity) {
		if (entity == null) {
			throw new RuntimeException("保存/更新的实体对象为null,请确认！");
		}
		// 插入对象
		this.getSqlSession().update(getSqlMapNameSpace(SQLID_UPDATE), entity);
	}

	/**
	 * 根据条件更新对象
	 * 
	 * entity
	 *            需要进行更新操作的实体对象
	 * 持久化的实体对象
	 */
	public void update(Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			throw new RuntimeException("params不能为null或size不能为0");
		}
		// 插入对象
		this.getSqlSession().update(getSqlMapNameSpace(SQLID_UPDATE), params);
	}
	/**
	 * 根据实体对象更新 
	 */
	public void update(String statementId,Object obj){
		if (obj == null) {
			throw new RuntimeException("obj不能为null");
		}
		// 插入对象
		this.getSqlSession().update(getSqlMapNameSpace(statementId), obj);
	}
	/**
	 * 更新
	 */
	public void update(String statementId, Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			throw new RuntimeException("params不能为null或size不能为0");
		}
		this.getSqlSession().update(getSqlMapNameSpace(statementId), params);
	}

	/**
	 * 根据一组条件删除特定对象
	 */
	public void remove(Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			throw new RuntimeException("params不能为null或size不能为0");
		}
		// 插入对象
		this.getSqlSession().delete(getSqlMapNameSpace(SQLID_REMOVE), params);
	}

	/**
	 * 删除
	 */
	public void remove(String statementId, Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			throw new RuntimeException("params不能为null或size不能为0");
		}
		this.getSqlSession().delete(getSqlMapNameSpace(statementId), params);
	}
	/**
	 * 根据特定条件删除 
	 * 条件实体对象
	 */
	public void remove(String statementId,Object obj){
		if (obj == null) {
			throw new RuntimeException("obj不能为null");
		}
		// 插入对象
		this.getSqlSession().delete(getSqlMapNameSpace(statementId), obj);
	}

	/**
	 * 根据ID删除对象
	 *
	 * 实体对象的标识符
	 */
	public void removeById(ID id) {
		if (id == null) {
			throw new RuntimeException("id不能为null");
		}
		// 插入对象
		this.getSqlSession().delete(getSqlMapNameSpace(SQLID_REMOVEBYID), id);
	}

	/**
	 * 通过ID来得到实体对象<br/>
	 * <ul>
	 * <li>ID为实体对象的标识符属性。</li>
	 * <li>T为泛型格式</li>
	 * <ul>
	 * 该主键值对应的实体对象
	 */
	public T findById(ID id) {
		return (T) this.getSqlSession().selectOne(
				getSqlMapNameSpace(SQLID_FINDBYID), id);
	}

	/**
	 * 通过一组ID来得到实体对象<br/>
	 * <ul>
	 * <li>ID为实体对象的标识符属性。</li>
	 * <li>T为泛型格式</li>
	 * <ul>
	 * 
	 * id实体对象的标识符
	 * 该主键值对应的实体对象
	 */
	public List<T> findByIds(List<ID> ids) {
		if (ids == null || ids.size() == 0) {
			throw new RuntimeException("ids数组不能为空或size为0！");
		}
		return (List<T>) this.getSqlSession().selectList(
				getSqlMapNameSpace(SQLID_FINDBYIDS), ids);
	}

	/**
	 * 查询得到所有的实体对象
	 * 
	 * 符合条件的List泛型对象
	 */
	public List<T> findAll() {
		return (List<T>) this.getSqlSession().selectList(
				getSqlMapNameSpace(SQLID_FINDALL));
	}

	/**
	 * 查询得到所有的实体对象
	 * 
	 * 条件集合
	 * 符合条件的List泛型对象
	 */
	public List<T> findAll(Map<String, Object> params) {
//		if (params == null || params.size() == 0) {
//			throw new RuntimeException("params不能为null或size不能为0");
//		}
		return (List<T>) this.getSqlSession().selectList(
				getSqlMapNameSpace(SQLID_FINDALL), params);
	}

	/**
	 * 按照条件查询某条记录总数
	 * 
	 * params
	 *            条件数组
	 * 返回符合条件的记录总数
	 */
	public int count(Map<String, Object> params) {
		return count(SQLID_COUNT,params);
	}
	/**
	 * 按照条件查询某条记录总数
	 * 
	 * params
	 *            条件数组
	 * 返回符合条件的记录总数
	 */
	public int count(String sqlId,Map<String, Object> params) {
		List list = this.getSqlSession().selectList(
				getSqlMapNameSpace(sqlId), params);
		if (list == null ||  list.size() == 0) {
			throw new RuntimeException("数量不能为空!");
		}

		return NumberUtils.toInt(list.get(0).toString());
	}
	/**
	 * 进行动态分页查询
	 * 
	 * SQL语句
	 * 
	 * params
	 *            [] 动态参数数组
	 * 符合条件的Pagination分页对象
	 */
	public Pagination<T> findByPage(Map<String, Object> params) {
        return findByPage(SQLID_FINDBYPAGE, params);
	}
	/**
	 * 通过PageBound进行分页查询
	 */
	@SuppressWarnings("rawtypes")
	public Pagination<T> findByPage(int pageNo,int pageSize,Map<String, Object> params,String... sqlId) {
		String tmpSqlId = SQLID_FINDBYPAGE;
		if(sqlId.length > 0){
			tmpSqlId = sqlId[0];
		}
		//设置分分页属性
		PageBounds pageBounds = new PageBounds(pageNo, pageSize);  
		List<T> list = this.getSqlSession().selectList(
				getSqlMapNameSpace(tmpSqlId), params,pageBounds);
		//获得结果集条总数  
		PageList pageList = (PageList)list;
		Pagination<T> pagination = new Pagination<T>();
		pagination.setPageNumber(pageNo< 0 ? 0:pageNo);
		pagination.setPageSize(pageSize<=0?1:pageSize);
		pagination.setMaxElements(pageList.getPaginator().getTotalCount());
		pagination.getList().addAll(list);
		return pagination;
		
	}
	
	/**
	 * 进行动态分页查询
	 * 
	 * SQL语句
	 * 
	 * params
	 *            [] 动态参数数组
	 * 符合条件的Pagination分页对象
	 */
	public Pagination<T> findByPage(String sqlId,Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			throw new RuntimeException("params不能为null或size不能为0");
		}
		if (params.get("pageNo") == null || params.get("pageSize") == null) {
			throw new RuntimeException("pageNo或pageSize不能为null");
		}
		if(StringUtils.isEmpty(sqlId)){
			throw new RuntimeException("sqlId不能为null");
		}
		Pagination<T> pagination = new Pagination<T>();
		// 设置页码
		int pageNo = NumberUtils.toInt(params.get("pageNo").toString(),0);
		// 设置页的大小
		int pageSize = NumberUtils.toInt(params.get("pageSize").toString(),0);

		int begin = (pageNo - 1) * pageSize;
		if (begin >= 0) {
			params.put("pageNo", begin);
		} else {
			params.put("pageNo", 0);
		}

		pagination.setPageNumber(pageNo<0?0:pageNo);
		pagination.setPageSize(pageSize<=0?1:pageSize);
		int totalRowsNum = 0;
		// 获取总量
		if(sqlId.trim().equals(SQLID_FINDBYPAGE)){
			totalRowsNum = count(params);
		}else{
			//对于自定义的分页查询,count的获取名称必须单独指定默认的后缀_count
			totalRowsNum = count(sqlId+"_count",params);
		}
		pagination.setMaxElements(totalRowsNum);
		List<T> list = this.getSqlSession().selectList(
				getSqlMapNameSpace(sqlId), params);
        if(logger.isDebugEnabled()){
        	String sql = MyBatisSqlHelper.getNamespaceSql(getSqlSession(), getSqlMapNameSpace(sqlId),params);
            logger.debug("SqlHelper : " +sql);
        }
		pagination.getList().addAll(list);
		return pagination;
	}
	
	
	
	
	
	/**
	 * 根据给定Id,返回Map结果集
	 */
	@SuppressWarnings("rawtypes")
	public Map findMap(String sqlId, String mapKey) {
		return this.getSqlSession()
				.selectMap(getSqlMapNameSpace(sqlId), mapKey);
	}
	/**
	 * 根据sqlId返回Map 
	 * @param sqlId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> findMapList(String sqlId){
		List list  = this.findList(getSqlMapNameSpace(sqlId));
	    // 结果列表
	    List<Map<String, Object>> mapList = list;
	    return mapList;
	}
    
	/**
	 * 根据sqlId返回Map 
	 * @param sqlId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> findMapList(String sqlId,Map<String, Object> params){
		List list  = this.findList(getSqlMapNameSpace(sqlId),params);
	    // 结果列表
	    List<Map<String, Object>> mapList = list;
	    return mapList;
	}
	/**
	 * 根据sqlId返回String
	 * @param sqlId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<String> findStringList(String sqlId,Object params){
		List list  = this.findList(getSqlMapNameSpace(sqlId),params);
	    // 结果列表
	    List<String> stringList = list;
	    return stringList;
	}
	/**
	 * 根据条件返回唯一的对象
	 * statementId
	 * params
	 */
	public Object findOne(String sqlId, Object params) {
		return this.getSqlSession()
				.selectOne(getSqlMapNameSpace(sqlId), params);
	}

	/**
	 * 根据给定的StatementId返回特定的一组对象
	 * 
	 * sqlId
	 * params
	 *  
	 */
	public List<T> findList(String sqlId, Object params) {
		return this.getSqlSession().selectList(getSqlMapNameSpace(sqlId),
				params);
	}
	/**
	 * 根据给定的StatementId返回特定的一组对象
	 * 
	 * sqlId
	 * params
	 */
	public List<T> findList(String sqlId) {
		return this.getSqlSession().selectList(getSqlMapNameSpace(sqlId));
	}
	/**
	 * 根据给定的StatementId返回特定的一组对象
	 * sqlId
	 * params
	 */
	public List<T> findList(Object params) {
		return this.getSqlSession().selectList(getSqlMapNameSpace(SQLID_FINDLIST),
				params);
	}
	/**
	 * 多个参数
	 * params
	 *  
	 */
	public List<T> findList(Map<String,Object> params) {
		return (List<T>) this.getSqlSession().selectList(
				getSqlMapNameSpace(SQLID_FINDLIST), params);
	}
	
	public void find(String sqlId, Object params, ResultHandler handler) {
		this.getSqlSession().select(getSqlMapNameSpace(sqlId), params, handler);
	}

	/**
	 * 通过执行JDBC操作，执行存储过程。
	 * params 占位参数数组
	 */
	public void callProcedure(Map<String, Object> params) {
	}

	/**
	 * 带inout或out参数的存储过程调用
	 * 
	 * outMap
	 *            inout 或out参数
	 * params
	 *            in参数
	 * 
	 */
	public void callProcedure(Map<Integer, Integer> outMap, Object... params) {
	}

}
