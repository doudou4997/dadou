package com.framework.core.daos;

import com.framework.core.page.Pagination;
import org.apache.ibatis.session.ResultHandler;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层的通用接口<BR>
 * <ul>
 * <li>数据访问层的所有自定义Dao接口在创建时都要实现该接口。</li>
 * </ul>
 * <br>
 * <b>以创建IFooDao接口为例讲解使用方法:</b><br/>
 * 
 * <pre>
 * public interface IFooDao extends IGenericDao&lt;Foo, String&gt; {
 * 	// 此处定义特有的处理方法,如下面的test方法。
 * 	void testFoo();
 * }
 * </pre>
 * 
 * 上述代码中，IGenericDao的T用实际的类型替代，如实体类Foo;ID使用Foo类的标识符属性替代<br/>
 * IFooDao继承的方法中的T都会改变为具体的类型Foo，ID都会改变为String <br/>
 * 
 * @version 1.0
 * @since 2014-01-22
 */
public interface IGenericDao<T, ID extends Serializable> {
	/**
	 * 操作常量
	 */
	/*保存*/
	public static final String SQLID_SAVE = "save";
	/*更新*/
	public static final String SQLID_UPDATE = "update";
	/*根据特定条件删除*/
	public static final String SQLID_REMOVE = "remove";
	/*根据Id删除*/
	public static final String SQLID_REMOVEBYID = "removeById";
	/*批量插入*/
	public static final String SQLID_BATCHINSERT = "batchInsert";	
	/*根据Id查找单个对象*/
	public static final String SQLID_FINDBYID = "findById";
	/* 根据一组Id,查找多个对象*/
	public static final String SQLID_FINDBYIDS = "findByIds";
	/* 根据条件查询多个对象*/
	public static final String SQLID_FINDALL = "findAll";
	/* 根据条件查询多个对象*/
	public static final String SQLID_FINDLIST = "findList";
	/*统计数量*/
	public static final String SQLID_COUNT = "count";
	/*分页查询*/
	public static final String SQLID_FINDBYPAGE = "findByPage";
	/*调用存储过程*/
	public static final String SQLID_CALLPROCEDURE = "callProcedure";
	/**
	 * 将对象进行持久化
	 * 
	 * @param entity
	 *            需要进行持久化操作的实体对象
	 * @return 持久化的实体对象
	 */
	int save(T entity);
    /**
     * 批量插入
     */
	@SuppressWarnings("rawtypes")
	void batchSave(List list);
	/**
	 * 更新对象
	 * @param entity
	 *            需要进行更新操作的实体对象
	 * @return 持久化的实体对象
	 */
	void update(T entity);
	/**
	 * 根据实体对象更新 
	 */
	void update(String statementId, Object obj);
	/**
	 * 更新 
	 */
	void update(String statementId, Map<String, Object> params);
	/**
	 * 根据条件更新对象<br/>
	 * @param params
	 *            需要进行更新操作的条件
	 */
	void update(Map<String, Object> params);
	/**
	 * 根据特定条件删除对象
	 * @param params条件map
	 */
	void remove(Map<String, Object> params);

	/**
	 * 根据ID删除对象
	 * @param id实体对象的标识符
	 */
	void removeById(ID id);
	/**
	 * 根据特定条件删除 
	 * @param params条件map
	 */
	void remove(String statementId, Map<String, Object> params);
	/**
	 * 根据特定条件删除 
	 * @param params条件实体对象
	 */
	void remove(String statementId, Object obj);
	/**
	 * 通过JDBC批量进行delete,update,insert操作。
	 * 
	 * @param sql
	 *            待执行的SQL语句
	 * @param params
	 *            参数数组，每行对应一维数组。
	 * 
	 * @return 更新的每个statment的数量
	 */
	//int[] batchByJdbc(final String sql, final Object[][] params);
	///////////////////////////////////////////////////////
	///所有查询操作
	//////////////////////////////////////////////////////
	/**
	 * 通过ID来得到实体对象<br/>
	 * <ul>
	 * <li>ID为实体对象的标识符属性。</li>
	 * <li>T为泛型格式</li>
	 * <ul>
	 * 
	 * @param id实体对象的标识符
	 * @return 该主键值对应的实体对象
	 */
	T findById(ID id);
	/**
	 * 通过一组Id来得到实体对象<br/>
	 * <ul>
	 * <li>ID为实体对象的标识符属性。</li>
	 * <li>T为泛型格式</li>
	 * <ul>
	 * 
	 * @param id实体对象的标识符
	 * @return 该主键值对应的实体对象
	 */
	List<T> findByIds(List<ID> ids);
	/**
	 * 查询得到所有的实体对象
	 * 
	 * @return 符合条件的List泛型对象
	 */
	List<T> findAll();
	/**
	 * 查询得到所有的实体对象
	 * @param 条件集合
	 * @return 符合条件的List泛型对象
	 */
	List<T> findAll(Map<String, Object> params);
	/**
	 * 按照条件查询某条记录总数
	 * 
	 * @param params
	 *            条件数组
	 * @return 返回符合条件的记录总数
	 */
	public int count(Map<String, Object> params);
	/**
	 * 进行动态分页查询
	 * 
	 *            SQL语句
	 * @param params
	 *            [] 动态参数数组
	 * @return 符合条件的Pagination分页对象
	 */
	Pagination<T> findByPage(Map<String, Object> params);

	/**
	 * 通过执行jdbc操作,执行存储过程
	 * 
	 * @param sql
	 *            执行的预编译语句
	 * @param params
	 *            传入参数
	 */
	void callProcedure(Map<String, Object> params);

	/**
	 * 带inout或out参数的存储过程调用
	 * 
	 * @param sql
	 * @param outMap
	 *            inout 或out参数
	 * @param params
	 *            in参数
	 * 
	 */
	void callProcedure(Map<Integer, Integer> outMap,
                       Object... params);

    /**
     * 根据给定Id,返回Map结果集
     */
	@SuppressWarnings("rawtypes")
	public Map findMap(String sqlId, String mapKey);
	/**
	 * 根据条件返回唯一的对象
	 * @param statementId
	 * @param params
	 * @return
	 */
	public Object findOne(String sqlId, Object params) ;
	/**
	 * 根据给定的StatementId返回特定的一组对象
	 * @param sqlId
	 * @param params
	 * @return
	 */
	public List<T> findList(String sqlId, Object params);
	
	public void find(String sqlId, Object params, ResultHandler handler);

}
