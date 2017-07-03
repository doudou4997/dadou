package com.framework.core.daos.jdbc;

import com.framework.core.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.Map.Entry;

/**
 * 数据库操作工具类
 * 
 * <p>説明: 执行查询SQL文，更新SQL和删除SQL等。</p>
 * 
 * @author gaof
 * @version 1.0
 * @since 2011-04-06
 */

public final class JdbcUtils {
    /**
     * 生成日志
     */
    private static Log log = LogFactory.getLog(JdbcUtils.class);
    /**
     * 空日期
     */
    public static final Date NULL_DATE = new Date(0L);
    /**
     * 空数值
     */
    public static final Integer NULL_INTEGER = new Integer("0");
    /**
     * 空BigDecimal对象
     */
    public static final BigDecimal NULL_NUMBER = new BigDecimal("0");
    /**
     * 空字符串
     */
    public static final String NULL_STRING = new String("NULL");
    /**
     * Is {@link ParameterMetaData#getParameterType(int)} broken (have we tried
     * it yet)?
     */
    private static volatile boolean pmdKnownBroken = false;

    /**
     * 构造方法
     */
    private JdbcUtils() {
    }

    /**
     * 根据预编译的SQL语句、占位参数和别名列表，把数据进行封装然后返回。
     * 
     * @param conn
     *          数据库链接
     * @param sql
     *          预编译的SQL语句
     * @param params
     *          占位参数
     * @param fieldNames
     *          字段别名列表
     * 
     * @return 封装后的数据
     * 
     * @throws SQLException
     */
    public static List<Map<String, Object>> executeQuery(Connection conn,
            String sql, Object[] params, String... fieldNames)
            throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.executeQuery(conn,sql,params,fieldNames)-----in");
        }
        // 自动提交为false
        //conn.setAutoCommit(false);
        // 判断SQL是否为空
        if (StringUtils.isEmpty(sql)) {
            throw new IllegalArgumentException("the sql is null.");
        }
        // 参数为空判断
        if (params == null) {
            throw new IllegalArgumentException("params数组 is null");
        }
        // 字段名称为空判断
        if (isNull(fieldNames)) {
            throw new IllegalArgumentException("fieldNames[] is null");
        }
        // 字段名称内为空判断
        for (int i = 0; i < fieldNames.length; i++) {
            // 当前字段
            if (isNull(fieldNames[i])) {
                throw new IllegalArgumentException("fieldNames[]数组 is null ");
            }
        }
        // PreparedStatement的声明
        PreparedStatement ps = null;
        // 查询结果存放到记录集
        ResultSet rs = null;
        // 返回结果
        List<Map<String, Object>> resultList = null;
        if (log.isDebugEnabled()) {
            log.debug("查询SQL = [" + sql + "]");
        }
        try {
            // prepareStatement的生成
            ps = conn.prepareStatement(sql);
            // 填充占位参数
            fillStatement(ps, params);
            // 查询结果存放到记录集
            rs = ps.executeQuery();
            // 查询结果的存放
            resultList = putResultSetIntoMap(rs, fieldNames);
            // 异常的捕获
        } catch (SQLException ex) {
            log.error(ex);
            // 抛出异常
            rethrow(ex, sql);
        }finally{
            try{
                //关闭游标
                if(ps != null){
                    ps.close();
                    ps = null;
                }
            }catch (SQLException e) {
                rethrow(e, sql);
                log.error(e);
            }
        }
        // 返回结果记录集
        return resultList;
    }

    /**
     * 根据SQL语句进行分页查询。
     * 
     * @param conn
     *          数据库链接
     * @param sql
     *          预编译的SQL语句
     * @param pageNo
     *          页码
     * @param pageSize
     *          记录数量
     * @param dbName
     *          数据库名称
     * @param params
     *          占位参数
     * 
     * @return 查询结果
     * 
     * @throws SQLException
     */
    public static List<Map<String, Object>> queryByPage(Connection conn,
            String sql, int pageNo, int pageSize, String dbName,
            Object... params) throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.queryByPage(con,sql,params,pageNo,pageSize)-------in");
        }
        // 自动提交为false
        //conn.setAutoCommit(false);
        // 判断sql是否为空
        if (StringUtils.isEmpty(sql)) {
            throw new IllegalArgumentException("sql is null.");
        }
        // 参数为空判断
        if (params == null) {
            throw new IllegalArgumentException("params[]数组 is null");
        }
        // 声明PreparedStatement
        PreparedStatement ps = null;
        // 查询结果存放到记录集
        ResultSet rs = null;
        // 返回结果
        List<Map<String, Object>> resultList = null;
        if (log.isDebugEnabled()) {
            log.debug("查询SQL = [" + sql + "]");
        }
        try {
            String finalSql = Dialect.getLimitString(sql, pageNo, pageSize,
                    dbName);
            // prepareStatement的生成
            ps = conn.prepareStatement(finalSql);
            // 参数的设置
            fillStatement(ps, params);
            // 查询结果存放到记录集
            rs = ps.executeQuery();
            // 查询结果的存放
            resultList = putResultSetIntoMap(rs);
            // 异常的捕获
        } catch (SQLException ex) {
            log.error(ex);
            // 抛出异常
            rethrow(ex, sql);
        }finally{
            try{
                //关闭游标
                if(ps != null){
                    ps.close();
                    ps = null;
                }
            }catch (SQLException e) {
                rethrow(e, sql);
                log.error(e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.queryByPage(con,sql,params)--------out");
        }
        // 返回结果记录集
        return resultList;
    }

    /**
     * 将查询结果存储到列表中，其中每个Map对应一条记录。
     * 
     * <p>说明：</p>
     * <ul>
     * <li>键：字段的别名(别名有可能与字段名称相同)，不区分大小写。</li>
     * <li>值：字段的实际值。</li>
     * </ul>
     * 
     * @param rs
     *          记录集对象
     * @param fieldNames
     *          别名列表
     * 
     * @return 查询结果列表，其中每个Map对应一条记录。
     * 
     * @throws SQLException
     */
    public static List<Map<String, Object>> putResultSetIntoMap(ResultSet rs,
            String... fieldNames) throws SQLException {
        // 记录集Null的判断
        if (rs == null) {
            throw new IllegalArgumentException("rs is null");
        }
        // 字段名称为NULL
        if (fieldNames == null) {
            throw new IllegalArgumentException("fieldNames is null");
        }
        // 传递参数fieldNames的判断
        for (int i = 0; i < fieldNames.length; i++) {
            // 参数中是否有空值得判断
            if (isNull(fieldNames[i])) {
                throw new IllegalArgumentException("fieldNames[] is null");
            }
        }
        // 列数
        int size = rs.getMetaData().getColumnCount();
        if (fieldNames.length != 0 && fieldNames.length != size) {
            throw new IllegalArgumentException("别名与列个数不对应!");
        }
        // 返回list
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        // 记录集存储map
        Map<String, Object> element = null;
        // 封装数据
        while (rs.next()) {
            element = new HashMap<String, Object>();

            // 每个字段对应的值
            for (int col = 0; col < size; col++) {
                // 取得记录集中的值
                Object obj = rs.getObject(col + 1);
                if (fieldNames.length != 0) {
                    element.put(fieldNames[col], obj);
                } else {
                    String columnName = rs.getMetaData().getColumnLabel(col + 1);
                    if (columnName.contains("row_")) {
                        element.put(columnName.substring("row_".length())
                                .toUpperCase(), obj);
                    } else {
                        // 放入到map // 列的名称
                        element.put(columnName.toUpperCase(), obj);
                    }
                }
            }
            returnList.add(element);
        }
        return returnList;
    }

    /**
     * 使用Statement执行更新操作。
     * 
     * @param conn
     *          获取的链接
     * @param sql
     *          执行的SQL语句
     * 
     * @return 1或0
     * <ul>
     * <li>1:成功</li>
     * <li>0:失败</li>
     * </ul>
     * 
     * @throws SQLException
     */
    public static int execUpdate(Connection conn, String sql)
            throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.executeUpdate(conn,sql)--------in");
        }
        // 返回结果
        int result = 0;
        // 自动提交为false
        //conn.setAutoCommit(false);
        // 判断sql是否为空
        if (StringUtils.isEmpty(sql)) {
            throw new IllegalArgumentException("sql is null.");
        }
        // Statement的声明
        Statement stmt = null;
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.executeUpdate(conn,sql),begin  sql = " + sql);
        }
        try {
            // Statement的创建
            stmt = conn.createStatement();
            // 执行sql
            result = stmt.executeUpdate(sql);
            // 异常的捕获
        } catch (SQLException ex) {
            // 抛出异常
            rethrow(ex, sql);
            // 记录异常
            log.error(ex);

        }finally{
            try{
                //关闭游标
                if(stmt != null){
                    stmt.close();
                    stmt = null;
                }
            }catch (SQLException e) {
                rethrow(e, sql);
                log.error(e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.executeUpdate(conn,sql)--------out");
        }
        return result;
    }

    /**
     * 根据预编译SQL语句执行更新操作。
     * 
     * @param conn
     *          获取的链接
     * @param sql
     *          执行的SQL语句
     * @param params
     *          占位参数数组
     * 
     * @return 1或0
     * <ul>
     * <li>1:成功</li>
     * <li>0:失败</li>
     * </ul>
     * 
     * @throws SQLException
     */
    public static int executeUpdate(Connection conn, String sql,
            Object... params) throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.executeUpdate(conn,sql,params)--------in");
        }
        // 自动提交为false
        //conn.setAutoCommit(false);
        // 判断sql是否为空
        if (StringUtils.isEmpty(sql)) {
            throw new IllegalArgumentException("sql is null.");
        }
        // 返回结果
        int result = 0;
        // SQL的打印
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.executeUpdate(conn,sql,params) begin sql = " + sql);
        }
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            // 参数的设置
            fillStatement(ps, params);
            // 更新结果的取得
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            // 抛出异常
            rethrow(ex, sql, params);
            // 记录异常
            log.error(ex);
        }finally{
            try{
                //关闭游标
                if(ps != null){
                    ps.close();
                    ps = null;
                }
            }catch (SQLException e) {
                rethrow(e, sql, (Object[]) params);
                log.error(e);
            }
        }
        return result;
    }

    /**
     * 执行批处理更新操作。
     * 
     * @param conn
     *          传入的连接对象
     * @param sql
     *          待执行的SQL语句
     * @param params
     *          参数数组，每行对应一维数组。
     * 
     * @return 更新的每个statement的数量
     * 
     * @throws SQLException
     */
    public static int[] batch(Connection conn, String sql, Object[][] params)
            throws SQLException {
        // 获取PreparedStatement对象
        PreparedStatement stmt = null;
        int[] rows = null;
        if (log.isDebugEnabled()) {
            log.debug("JdbcUtils.batch(conn,sql,params),begin  sql = " + sql);
        }
        try {
            stmt = conn.prepareStatement(sql);
            //批量插入语句
            boolean isInsert = false;
            if(sql.toLowerCase().trim().startsWith("insert")){
                isInsert = true;
            }
            //如果是Insert语句,需要集中返回自动增长的id数组
            if(isInsert){
                stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            }
            // 填充多个占位参数数组
            for (int i = 0; i < params.length; i++) {
                fillStatement(stmt, params[i]);
                // 批处理
                stmt.addBatch();
            }
            // 批处理
            rows = stmt.executeBatch();
            if(isInsert){
                //获取生成的键
                ResultSet rs = stmt.getGeneratedKeys();
                int i = 0;
                while(rs.next()){
                    int id = rs.getInt(1);
                    rows[i] = id;
                    i++;
                }
            }
            
        } catch (SQLException e) {
            rethrow(e, sql, (Object[]) params);
            log.error(e);
        }finally{
            try{
                //关闭游标
                if(stmt != null){
                   stmt.close();
                   stmt = null;
                }
            }catch (SQLException e) {
                rethrow(e, sql, (Object[]) params);
                log.error(e);
            }
        }
        return rows;
    }

    /**
     * 抛出一个异常信息。
     * 
     * @param cause
     *          原始的异常信息
     * @param sql
     *          抛出异常时，正在执行的SQL语句。
     * @param params
     *          查询时的占位参数； <code>null</code> 允许null作为条件
     * 
     * @throws SQLException
     *          数据库访问出错时抛出的异常
     */
    protected static void rethrow(SQLException cause, String sql,
            Object... params) throws SQLException {
        // 获取基础异常信息
        String causeMessage = cause.getMessage();
        if (causeMessage == null) {
            causeMessage = "";
        }
        StringBuffer msg = new StringBuffer(causeMessage);
        // 构造异常信息
        msg.append(" Query: ");
        msg.append(sql);
        msg.append(" Parameters: ");
        if (params == null) {
            msg.append("[]");
        } else {
            // 附加占位参数数组
            msg.append(Arrays.deepToString(params));
        }
        // 封装成异常信息
        SQLException e = new SQLException(msg.toString(), cause.getSQLState(),
                cause.getErrorCode());
        e.setNextException(cause);
        // 进一步抛出信息
        throw e;
    }

    /**
     * 用给定的数组填充占位符 <code>PreparedStatement</code>。
     * 
     * @param stmt
     *          待填充的PreparedStatement对象
     * @param params
     *          查询时的占位参数数组； <code>null</code> null也是合法的占位参数。
     * 
     * @throws SQLException
     *          访问错误时抛出的异常
     */
    private static void fillStatement(PreparedStatement stmt, Object... params)
            throws SQLException {
        ParameterMetaData pmd = null;
        if (!pmdKnownBroken) {
            pmd = stmt.getParameterMetaData();
            if (pmd.getParameterCount() < params.length) {
                throw new SQLException("太多的参数: 期待的参数长度："
                        + pmd.getParameterCount() + ", 给定的参数长度： "
                        + params.length);
            }
        }
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                stmt.setObject(i + 1, params[i]);
            } else {
                // VARCHAR
                // 是在许多drivers使用时比较通用的类型,对于Oracle比较特殊,NULL没法与Oracle驱动正常工作.
                int sqlType = Types.VARCHAR;
                if (!pmdKnownBroken) {
                    try {
                        sqlType = pmd.getParameterType(i + 1);
                    } catch (SQLException e) {
                        pmdKnownBroken = true;
                    }
                }
                stmt.setNull(i + 1, sqlType);
            }
        }
    }

    /**
     * 调用不带参数的存储过程。
     * 
     * @param conn
     *          数据库链接
     * @param sql
     *          预编译的SQL语句
     * 
     * @throws SQLException
     */
    public static void callProcedure(Connection conn,String sql) throws SQLException {
        callProcedure(conn,sql, null, (Object[]) null);
    }

    /**
     * 调用带参数的存储过程。
     * 
     * @param conn
     *          数据库链接
     * @param sql
     *          预编译的SQL语句
     * @param params
     *          动态参数
     * 
     * @throws SQLException
     */
    public static void callProcedure(Connection conn, String sql,
            Object... params) throws SQLException {
        CallableStatement cstmt = null;
        try{
            cstmt = conn.prepareCall(sql);
            fillStatement(cstmt, params);
            cstmt.execute();
        }catch (SQLException e) {
            log.error(e);
            throw e;
        }finally{
            try{
                //关闭游标
                if(cstmt != null){
                    cstmt.close();
                    cstmt = null;
                }
            }catch (SQLException e) {
                log.error(e);
            }
        }
    }

    /**
     * 调用带input或out参数的存储过程。
     * 
     * @param conn
     *          数据库链接
     * @param sql
     *          预编译的SQL语句
     * @param outMap
     *          input或out参数
     * @param params
     *          动态参数
     * 
     * @throws SQLException
     */
    public static void callProcedure(Connection conn,String sql,
            Map<Integer, Integer> outMap, Object... params) throws SQLException {
        CallableStatement cstmt = null;
        try{
            cstmt = conn.prepareCall(sql);
            fillStatement(cstmt, params);
        if (outMap != null) {
            for (Entry<Integer, Integer> e : outMap.entrySet()) {
                // 注册所有的输出参数
                cstmt.registerOutParameter(e.getKey(), e.getValue());
            }
        }
           //调用存储过程
           cstmt.execute();
        }catch (SQLException e) {
            log.error(e);
            throw e;
        }finally{
            try{
                //关闭游标
                if(cstmt != null){
                    cstmt.close();
                    cstmt = null;
                }
            }catch (SQLException e) {
                log.error(e);
            }
        }
    }

    /**
     * 判断对象是否为空。
     * 
     * @param obj
     *          判断对象
     * 
     * @return true或false
     * <ul>
     * <li>true:为空</li>
     * <li>false:非空</li>
     * </ul>
     */
    private static boolean isNull(Object obj) {
        return obj == null || obj == NULL_DATE || obj == NULL_NUMBER
                || obj == NULL_STRING || obj == NULL_INTEGER;
    }
}