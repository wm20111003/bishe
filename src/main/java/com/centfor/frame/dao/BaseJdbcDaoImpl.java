package com.centfor.frame.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.centfor.frame.common.BaseLogger;
import com.centfor.frame.common.SessionUser;
import com.centfor.frame.dao.dialect.IDialect;
import com.centfor.frame.entity.IAuditLog;
import com.centfor.frame.util.ClassUtils;
import com.centfor.frame.util.EntityInfo;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.RegexValidateUtils;
import com.centfor.frame.util.SecUtils;
import com.centfor.frame.util.WhereSQLInfo;

/**
 * 基础的Dao父类,所有的Dao都必须继承此类,每个数据库都需要一个实现.</br>
 * 
 * 例如 demo数据的实现类是com.centfor.springrain.dao.BasedemoDaoImpl,demo2数据的实现类是org. springrain
 * demo2.dao.Basedemo2DaoImpl</br>
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see com.centfor.frame.dao.BaseJdbcDaoImpl
 */
public abstract class BaseJdbcDaoImpl extends BaseLogger implements IBaseJdbcDao {
	private String frame_jdbc_call_key = "frame_jdbc_call_key";

	/**
	 * 抽象方法.每个数据库的代理Dao都必须实现.在多库情况下,用于区分底层数据库的连接对象,对数据库进行增删改查.</br>
	 * 例如:demo数据库的代理Dao com.centfor.springrain.dao.BasedemoDaoImpll
	 * 实现返回的是spring的beanjdbc.</br> demo2 数据库的代理Dao
	 * com.centfor.demo2.dao.Basedemo2DaoImpl 实现返回的是spring的bean jdbc_demo2.</br>
	 * 
	 * @return
	 */
	public abstract NamedParameterJdbcTemplate getJdbc();

	/**
	 * 抽象方法.每个数据库的代理Dao都必须实现.在多库情况下,用于区分底层数据库的连接对象,调用数据库的函数和存储过程.</br> 例如:demo
	 * 数据库的代理Dao com.centfor.springrain.dao.BasedemoDaoImpl 实现返回的是spring的bean
	 * jdbcCall.</br> datalog 数据库的代理Dao com.centfor.demo2.dao.Basedemo2DaoImpl
	 * 实现返回的是spring的beanjdbcCall_demo2.</br>
	 * 
	 * @return
	 */
	public abstract SimpleJdbcCall getJdbcCall();

	/**
	 * 读写分离的读数据库
	 * 
	 * @return
	 */
	public NamedParameterJdbcTemplate getReadJdbc() {
		return getJdbc();
	}

	/**
	 * 读写分离的写数据库
	 * 
	 * @return
	 */
	public NamedParameterJdbcTemplate getWriteJdbc() {
		return getJdbc();
	}

	/**
	 * 获取数据库方言,Dao 中注入spring bean.</br> 例如mysql的实现是 mysqlDialect. oracle的实现是
	 * oracleDialect. 如果使用了sequence 在entity使用@PKSequence实现自增 详见
	 * com.centfor.frame.dao.dialect.IDialect的实现
	 * 
	 * @return
	 */
	public abstract IDialect getDialect();

	/**
	 * 默认(return null)不记录日志,在多库情况下,用于区分数据库实例的日志记录表,
	 * 主要是为了兼容日志表(auditlog)的主键生成方式,UUID和自增.</br> demo 数据库的auditlog 是自增,demo2
	 * 数据库的 auditlog 是UUID
	 * 
	 * @return
	 */

	public IAuditLog getAuditLog() {
		return null;
	}

	/**
	 * 是否打印sql语句,默认false
	 * 
	 * @return
	 */
	public boolean showsql() {
		return true;
	}

	public String getUserName() {
		return SessionUser.getUserName();
	}

	/**
	 * 打印sql
	 * 
	 * @param sql
	 */
	private void logInfoSql(String sql) {
		if (showsql()) {
			System.out.println(sql);
		}
	}

	public BaseJdbcDaoImpl() {
	}

	@Override
	public <T> List<T> queryForList(Finder finder, Class<T> clazz) throws Exception {
		return queryForList(finder, clazz, null);

	}

	@Override
	public <T> List<T> queryForListByProc(Finder finder, Class<T> clazz) throws Exception {
		String procName = finder.getProcName();
		String functionName = finder.getFunName();

		if (StringUtils.isBlank(procName) && StringUtils.isBlank(functionName)) {
			throw new NullPointerException("存储过程和函数不能同时为空!");
		}

		Map params = finder.getParams();
		Map<String, Object> m = new HashMap<String, Object>(0);
		SimpleJdbcCall simpleJdbcCall = null;

		if (StringUtils.isNotBlank(procName)) {
			simpleJdbcCall = getJdbcCall().withProcedureName(procName);
		} else {
			simpleJdbcCall = getJdbcCall().withFunctionName(functionName);
		}

		if (params != null) {
			if (ClassUtils.isBaseType(clazz)) {
				m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key,
						new RowNumberSingleColumnRowMapper(clazz)).execute(params);
			} else {
				m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key,
						BeanPropertyRowMapper.newInstance(clazz)).execute(params);
			}
		} else {
			if (ClassUtils.isBaseType(clazz)) {
				m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key,
						new RowNumberSingleColumnRowMapper(clazz)).execute();
			} else {
				m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key,
						BeanPropertyRowMapper.newInstance(clazz)).execute();
			}
		}
		return (List<T>) m.get(frame_jdbc_call_key);
	}

	/**
	 * 调用数据库存储过程 查询结果是 List
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForListByProc(Finder finder) throws Exception {
		String procName = finder.getProcName();
		String functionName = finder.getFunName();
		if (StringUtils.isBlank(procName) && StringUtils.isBlank(functionName)) {
			throw new NullPointerException("存储过程和函数不能同时为空!");
		}
		Map params = finder.getParams();
		Map<String, Object> m = new HashMap<String, Object>(0);
		SimpleJdbcCall simpleJdbcCall = null;

		if (StringUtils.isNotBlank(procName)) {
			simpleJdbcCall = getJdbcCall().withProcedureName(procName);
		} else {
			simpleJdbcCall = getJdbcCall().withFunctionName(functionName);
		}

		if (params != null) {
			m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key, new ColumnMapRowMapper())
					.execute(params);
		} else {
			m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key, new ColumnMapRowMapper())
					.execute();
		}
		return (List<Map<String, Object>>) m.get(frame_jdbc_call_key);

	}

	/**
	 * 调用数据库函数 查询结果是 List
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryForListByFunction(Finder finder) throws Exception {
		return queryForListByProc(finder);
	}

	@Override
	public List<Map<String, Object>> queryForList(Finder finder) {
		// 打印sql
		logInfoSql(finder.getSql());
		return getReadJdbc().queryForList(finder.getSql(), finder.getParams());
	}

	@Override
	public Map<String, Object> queryForObject(Finder finder) throws Exception {
		Map<String, Object> map = null;
		try {
			// 打印sql
			logInfoSql(finder.getSql());
			map = getReadJdbc().queryForMap(finder.getSql(), finder.getParams());
		} catch (EmptyResultDataAccessException e) {
			map = null;
		}

		return map;
	}

	@Override
	public List<Map<String, Object>> queryForList(Finder finder, Page page) throws Exception {
		String pageSql = getPageSql(page, finder);
		if (pageSql == null)
			return null;
		finder.setPageSql(pageSql);

		// 打印sql
		logInfoSql(pageSql);

		return getReadJdbc().queryForList(pageSql, finder.getParams());

	}

	@Override
	public Integer update(Finder finder) throws Exception {
		// 打印sql
		logInfoSql(finder.getSql());
		return getWriteJdbc().update(finder.getSql(), finder.getParams());
	}

	@Override
	public <T> List<T> queryForList(Finder finder, Class<T> clazz, Page page) throws Exception {
		String pageSql = getPageSql(page, finder);
		if (pageSql == null)
			return null;
		finder.setPageSql(pageSql);

		// 打印sql
		logInfoSql(pageSql);

		if (ClassUtils.isBaseType(clazz)) {
			if (getDialect().isRowNumber()) {
				return getReadJdbc().query(pageSql, finder.getParams(),
						new RowNumberSingleColumnRowMapper(clazz));
			} else {
				return getReadJdbc().queryForList(pageSql, finder.getParams(), clazz);
			}
		} else {
			return getReadJdbc().query(pageSql, finder.getParams(),
					BeanPropertyRowMapper.newInstance(clazz));
		}
	}

	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object queryBean) throws Exception {

		if (finder == null) {

			String tableName = ClassUtils.getTableName(queryBean);

			finder = new Finder("SELECT * FROM " + tableName);
			finder.append(" WHERE 1=1 ");

		}

		if (queryBean != null) {
			getFinderWhereByQueryBean(finder, queryBean);
		}

		int _index = RegexValidateUtils.getOrderByIndex(finder.getSql());
		if (_index > 0) {
			finder.setSql(finder.getSql().substring(0, _index));
		}

		if (page == null) {
			return this.queryForList(finder, clazz, page);
		}

		// 根据page的参数 添加 order by
		getFinderOrderBy(finder, page);

		List<T> datas = this.queryForList(finder, clazz, page);
		if (datas == null) {
			datas = new ArrayList<T>();
		}
		return datas;
	}

	@Override
	public Finder getFinderOrderBy(Finder finder, Page page) throws Exception {
		if (finder == null || page == null) {
			return finder;
		}
		String sort = page.getSort();
		String order = page.getOrder();
		if (StringUtils.isNotBlank(order)) {
			order = order.trim();
			if (order.indexOf(" ") > -1 || order.indexOf(";") > -1) {// 认为是异常的,主要是防止注入
				return null;
			}

			if (RegexValidateUtils.getOrderByIndex(finder.getSql()) < 0) {
				finder.append(" order by ").append(order);
			}
			if (StringUtils.isNotBlank(sort)) {
				if ("asc".equals(sort.toLowerCase())
						&& (finder.getSql().toLowerCase().contains(" asc ") == false)) {
					finder.append(" asc ");
				} else if ("desc".equals(sort.toLowerCase())
						&& (finder.getSql().toLowerCase().contains(" desc ") == false)) {
					finder.append(" desc ");
				}
			}

		}
		return finder;
	}

	@Override
	public Finder getFinderWhereByQueryBean(Finder finder, Object o) throws Exception {
		List<WhereSQLInfo> whereSQLInfo = ClassUtils.getWhereSQLInfo(o.getClass());

		Object alias_o = ClassUtils.getPropertieValue(GlobalStatic.frameTableAlias, o);
		String alias = null;
		if (alias_o != null) {
			alias = alias_o.toString();
		}

		if (CollectionUtils.isNotEmpty(whereSQLInfo)) {
			for (WhereSQLInfo whereinfo : whereSQLInfo) {
				String name = whereinfo.getName();
				Object value = ClassUtils.getPropertieValue(name, o);
				if (value == null || StringUtils.isBlank(value.toString())) {
					continue;
				}
				String wheresql = whereinfo.getWheresql();
				if (StringUtils.isNotBlank(alias)) {
					wheresql = alias + "." + wheresql;
				}
				String pname = wheresql.substring(wheresql.indexOf(":") + 1).trim();
				if (wheresql.toLowerCase().contains(" in ") && pname.endsWith(")")) {
					pname = pname.substring(0, pname.length() - 1).trim();
				}

				if (wheresql.toLowerCase().contains(" like ")) {
					boolean qian = pname.trim().startsWith("%");
					boolean hou = pname.trim().endsWith("%");
					wheresql = wheresql.replaceAll("%", "");
					pname = pname.replaceAll("%", "");
					if (qian) {
						value = "%" + value;
					}
					if (hou) {
						value = value + "%";
					}

					finder.append(" and ").append(wheresql).setParam(pname, value);
				} else {
					finder.append(" and ").append(wheresql).setParam(pname, value);
				}
			}
		}

		return finder;

	}
	

	/**
	 * 根据page 和finder对象,拼装返回分页查询的语句
	 * 
	 * @param page
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	private String getPageSql(Page page, Finder finder) throws Exception {
		String sql = finder.getSql();
		String orderSql = finder.getOrderSql();

		Map<String, Object> paramMap = finder.getParams();

		// 查询sql、统计sql不能为空
		if (StringUtils.isBlank(sql)) {
			return null;
		}
		if (RegexValidateUtils.getOrderByIndex(sql) > -1) {
			if (StringUtils.isBlank(orderSql)) {
				orderSql = sql.substring(RegexValidateUtils.getOrderByIndex(sql));
				sql = sql.substring(0, RegexValidateUtils.getOrderByIndex(sql));
			}
		} else {
			if(page!=null&&StringUtils.isNotBlank(page.getOrder())){//如果page中包含 排序属性
			  String 	_order = page.getOrder().trim();
				if (_order.indexOf(" ") > -1 || _order.indexOf(";") > -1) {// 认为是异常的,主要是防止注入
					orderSql = " order by id asc ";
				}else{
					String _sort=page.getSort();
					if(_sort==null){
						_sort="";
					}
					_sort=_sort.trim().toLowerCase();
					
					if((!"asc".equals(_sort))&&(!"desc".equals(_sort))){//如果 不是 asc 也不是 desc
						_sort="";
					}
					
					orderSql=" order by "+page.getOrder()+" "+_sort;
				}
				
			}else{
				orderSql = " order by id asc ";
			}
		}

		if (page == null) {
			if (StringUtils.isNotBlank(orderSql))
				return sql + " " + orderSql;
			else
				return sql;
		}

		Integer count = null;

		if (finder.getCountFinder() == null) {
			String countSql = new String(sql);
			int order_int = RegexValidateUtils.getOrderByIndex(countSql);
			if (order_int > 1) {
				countSql = countSql.substring(0, order_int);
			}

			/**
			 * 特殊关键字过滤, distinct ,union ,group by
			 */
			if (countSql.toLowerCase().indexOf(" distinct ") > -1
					|| countSql.toLowerCase().indexOf(" union ") > -1
					|| RegexValidateUtils.getGroupByIndex(countSql) > -1) {
				countSql = "SELECT count(*)  frame_row_count FROM (" + countSql
						+ ") temp_frame_noob_table_name WHERE 1=1 ";
			} else {
				int fromIndex = RegexValidateUtils.getFromIndex(countSql);
				if (fromIndex > -1) {
					countSql = "SELECT COUNT(*) " + countSql.substring(fromIndex);
				} else {
					countSql = "SELECT count(*)  frame_row_count FROM (" + countSql
							+ ") temp_frame_noob_table_name WHERE 1=1 ";
				}

			}

			// count = getReadJdbc().queryForInt(countSql, paramMap);
			count = getReadJdbc().queryForObject(countSql, paramMap, Integer.class);
		} else {
			count = queryForObject(finder.getCountFinder(), Integer.class);
		}
		// 记录总行数(区分是否使用占位符)
		if (count == 0) {
			return null;
		} else {
			page.setTotalCount(count);
		}
		return getDialect().getPageSql(sql, orderSql, page);
	}

	@Override
	public <T> T queryForObject(Finder finder, Class<T> clazz) throws Exception {
		// 打印sql
		logInfoSql(finder.getSql());
		T t = null;
		try {
			if (ClassUtils.isBaseType(clazz)) {
				t = (T) getReadJdbc().queryForObject(finder.getSql(), finder.getParams(), clazz);

			} else {
				t = (T) getReadJdbc().queryForObject(finder.getSql(), finder.getParams(),
						BeanPropertyRowMapper.newInstance(clazz));
			}
		} catch (EmptyResultDataAccessException e) {
			t = null;
		}

		return t;

	}

	private String warpsavesql(Object entity, Map paramMap, Boolean isSequence) throws Exception {
		Class clazz = entity.getClass();
		// entity信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		List<String> fdNames = ClassUtils.getAllDBFields(clazz);
		String id = SecUtils.getUUID();// 主键
		String tableName = entityInfo.getTableName();
		Class<?> returnType = entityInfo.getPkReturnType();
		String pkName = entityInfo.getPkName();
		// 获取 分表的扩展
		String tableExt = entityInfo.getTableExt();

		StringBuffer sql = new StringBuffer("INSERT INTO ").append(tableName).append(tableExt)
				.append("(");

		StringBuffer valueSql = new StringBuffer(" values(");

		for (int i = 0; i < fdNames.size(); i++) {
			String fdName = fdNames.get(i);// 字段名称
			// fd.setAccessible(true);

			if (fdName.equals(pkName)) {// 如果是ID,自动生成UUID
				Object _getId = ClassUtils.getPKValue(entity); // 主键
				if (_getId == null||"".equals(_getId)) {  
					if (returnType == String.class) {
						ClassUtils.setPropertieValue(pkName, entity, id);
					} else if (StringUtils.isNotBlank(entityInfo.getPksequence())) {// 如果包含主键序列注解
						String _sequence_value = entityInfo.getPksequence();
						if ((i + 1) == fdNames.size()) {
							sql.append(fdName).append(")");
							valueSql.append(_sequence_value).append(")");
							break;
						}
						sql.append(fdName).append(",");
						valueSql.append(_sequence_value).append(",");
						continue;
					} else {
						continue;
					}
				} else {
					id = _getId.toString();
				}
			}

			String mapKey = ":" + fdName;// 占位符
			Object fdValue = ClassUtils.getPropertieValue(fdName, entity);
			paramMap.put(fdName, fdValue);

			if ((i + 1) == fdNames.size()) {
				sql.append(fdName).append(")");
				valueSql.append(mapKey).append(")");
				break;
			}

			sql.append(fdName).append(",");
			valueSql.append(mapKey).append(",");

		}
		sql.append(valueSql);// sql语句
		return sql.toString();
	}

	/**
	 * 保存一个实体类,不记录日志
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Object saveNoLog(Object entity) throws Exception {
		// entity信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		Class<?> returnType = entityInfo.getPkReturnType();

		Map paramMap = new HashMap();
		Boolean isSequence = false;
		String sql = warpsavesql(entity, paramMap, isSequence);
		// 打印sql
		logInfoSql(sql);

		/**
		 * 增加如果表没有主键的判断
		 */
		if (returnType == null) {
			getWriteJdbc().update(sql, paramMap);
			return null;
		}

		else if (returnType == String.class) {
			getWriteJdbc().update(sql, paramMap);
			return ClassUtils.getPKValue(entity).toString();

		} else {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource ss = new MapSqlParameterSource(paramMap);
			String _pkName = entityInfo.getPkName();
			if (StringUtils.isNotBlank(_pkName) && isSequence) {
				getWriteJdbc().update(sql, ss, keyHolder, new String[] { _pkName });
			} else {
				getWriteJdbc().update(sql, ss, keyHolder);
			}
			return keyHolder.getKey().longValue();
		}
	}

	@Override
	public Object save(Object entity) throws Exception {
		Object id = saveNoLog(entity);
		IAuditLog auditLog = getAuditLog();
		if (auditLog == null) {
			return id;
		}

		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		if (entityInfo.isNotLog()) {
			return id;
		}

		String tableExt = entityInfo.getTableExt();
		if (StringUtils.isBlank(tableExt)) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			tableExt = GlobalStatic.tableExt + year;
		}
		auditLog.setOperationClass(entity.getClass().getName());
		auditLog.setOperationType(GlobalStatic.dataSave);
		auditLog.setOperatorName(getUserName());
		if (id!=null&&(!id.equals(""))) {// 如果Id有值  
			auditLog.setOperationClassId(id.toString());
		}

		auditLog.setOperationTime(new Date());
		auditLog.setCurValue(entity.toString());
		auditLog.setPreValue("无");
		auditLog.setExt(tableExt);
		saveNoLog(auditLog);// 保存日志

		return id;
	}

	@Override
	public List<Integer> update(List list) throws Exception {
		return update(list, false);
	}
	
	
	@Override
	public List<Integer> update(List list,boolean onlyupdatenotnull) throws Exception {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Integer> updateList = new ArrayList<Integer>();
		Map[] maps = new HashMap[list.size()];
		String sql = null;
		for (int i = 0; i < list.size(); i++) {
			Map paramMap = new HashMap();
			sql = warpupdatesql(list.get(i), paramMap,onlyupdatenotnull);
			maps[i] = paramMap;
		}
		int[] batchUpdate = getWriteJdbc().batchUpdate(sql,
				SqlParameterSourceUtils.createBatch(maps));

		if (batchUpdate.length < 1) {
			return updateList;
		}
		for (int i : batchUpdate) {
			updateList.add(i);
		}
		return updateList;
	}
	
	

	@Override
	public List<Integer> save(List list) throws Exception {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		List<Integer> updateList = new ArrayList<Integer>();
		Map[] maps = new HashMap[list.size()];
		String sql = null;
		for (int i = 0; i < list.size(); i++) {
			Map paramMap = new HashMap();
			sql = warpsavesql(list.get(i), paramMap, false);
			maps[i] = paramMap;
		}
		int[] batchUpdate = getWriteJdbc().batchUpdate(sql,
				SqlParameterSourceUtils.createBatch(maps));

		if (batchUpdate.length < 1) {
			return updateList;
		}
		for (int i : batchUpdate) {
			updateList.add(i);
		}
		return updateList;
	}

	private String warpupdatesql(Object entity, Map paramMap,boolean onlyupdatenotnull) throws Exception {
		Class clazz = entity.getClass();
		// entity的信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		List<String> fdNames = ClassUtils.getAllDBFields(clazz);
		String tableName = entityInfo.getTableName();

		String pkName = entityInfo.getPkName();

		// 获取 分表的扩展
		String tableExt = entityInfo.getTableExt();
		StringBuffer sql = new StringBuffer("UPDATE ").append(tableName).append(tableExt)
				.append("  SET  ");

		StringBuffer whereSQL = new StringBuffer(" WHERE ").append(pkName).append("=:").append(pkName);
		for (int i = 0; i < fdNames.size(); i++) {
			String fdName = fdNames.get(i);// 字段名称
			Object fdValue = ClassUtils.getPropertieValue(fdName, entity);
			
			//只更新不为null的字段
			if(onlyupdatenotnull&&fdValue==null){//如果只更新不为null的字段,字段值为null 不更新
				continue;
			}
			
			if (fdName.equals(pkName)) {// 如果是ID
                   if(fdValue!=null){//id有值
                	   paramMap.put(fdName, fdValue);
                   }
					continue;
			}
			//设置字段
			paramMap.put(fdName, fdValue);
           //添加需要更新的字段			
			sql.append(fdName).append("=:").append(fdName).append(",");
		}
		
	
		String str=sql.toString();
		if(str.endsWith(",")){
			str=str.substring(0, str.length()-1);
		}
		
		
/*
		sql.append(whereSQL);
		return sql.toString();
		*/
		
		return str+whereSQL;
	}

	@Override
	public Integer update(Object entity) throws Exception {
		return update(entity, false);
		
	}
	
	
	@Override
	public Integer update(Object entity,boolean onlyupdatenotnull) throws Exception {
		Class clazz = entity.getClass();
		// entity的信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		// 获取 分表的扩展
		String tableExt = entityInfo.getTableExt();
		Map paramMap = new HashMap();
		String sql = warpupdatesql(entity, paramMap,onlyupdatenotnull);
		Object id = ClassUtils.getPKValue(entity);
		// 打印sql
		logInfoSql(sql.toString());

		Object old_entity = null;
		IAuditLog auditLog = getAuditLog();
		if (auditLog != null) {
			old_entity = findByID(id, clazz, tableExt);
		}
		// 更新entity
		Integer hang = getWriteJdbc().update(sql.toString(), paramMap);
		if (auditLog == null) {
			return hang;
		}

		if (entityInfo.isNotLog()) {
			return hang;
		}

		auditLog.setOperationClass(entity.getClass().getName());
		auditLog.setOperationType(GlobalStatic.dataUpdate);
		auditLog.setOperatorName(getUserName());
		auditLog.setOperationClassId(id.toString());
		auditLog.setOperationTime(new Date());
		auditLog.setPreValue(old_entity.toString());
		auditLog.setCurValue(entity.toString());

		String audit_tableExt = tableExt;
		if (StringUtils.isBlank(tableExt)) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			audit_tableExt = GlobalStatic.tableExt + year;
		}
		auditLog.setExt(audit_tableExt);
		// 保存日志
		saveNoLog(auditLog);
		return hang;

	}
	
	
	

	@Override
	public <T> T findByID(Object id, Class<T> clazz) throws Exception {
		return findByID(id, clazz, null);
	}

	@Override
	public <T> T findByID(Object id, Class<T> clazz, String tableExt) throws Exception {
		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(clazz);
		String tableName = entityInfo.getTableName();
		String idName = entityInfo.getPkName();
		if (StringUtils.isNotBlank(tableExt)) {
			tableName += tableExt;
		}
		String sql = "SELECT * FROM " + tableName + " WHERE " + idName + "=:id";
		Finder finder = new Finder(sql);
		finder.setParam("id", id);
		// 打印sql
		logInfoSql(finder.getSql());
		return queryForObject(finder, clazz);
	}

	@Override
	public Object saveorupdate(Object entity) throws Exception {
		Object o = ClassUtils.getPKValue(entity);
		if (o == null){
			return save(entity);
		}
		else{
			return update(entity);
		}

	}

	@Override
	public void deleteById(Object id, Class clazz) throws Exception {
		if (id == null)
			return;
		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(clazz);
		String tableName = entityInfo.getTableName();
		String idName = entityInfo.getPkName();
		String sql = "Delete FROM " + tableName + " WHERE " + idName + "=:id";
		Finder finder = new Finder(sql);
		finder.setParam("id", id);

		IAuditLog auditLog = getAuditLog();
		Object findEntityByID = null;

		if (auditLog != null) {
			findEntityByID = findByID(id, clazz);
			if(findEntityByID==null){//数据库不存在记录,直接返回
				return;
			}
		}
		// 打印sql
		logInfoSql(finder.getSql());
		update(finder);

		if (auditLog == null) {
			return;
		}

		if (entityInfo.isNotLog()) {
			return;
		}
		/**
		 * 删除还有个 bug,就是删除分表的数据,日志记录有问题 没有分表
		 */

		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableExt = GlobalStatic.tableExt + year;

		auditLog.setOperationClass(clazz.getName());
		auditLog.setOperationType(GlobalStatic.dataDelete);
		auditLog.setOperatorName(getUserName());
		auditLog.setOperationClassId(id.toString());
		auditLog.setOperationTime(new Date());
		auditLog.setPreValue(findEntityByID.toString());
		auditLog.setExt(tableExt);
		auditLog.setCurValue("无");
		// 保存日志
		saveNoLog(auditLog);

	}

	@Override
	public void deleteByIds(List ids, Class clazz) throws Exception {
		if (CollectionUtils.isEmpty(ids))
			return;
		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(clazz);
		String tableName = entityInfo.getTableName();
		String idName = entityInfo.getPkName();
		String sql = "Delete FROM " + tableName + " WHERE " + idName + " in (:ids)";
		Finder finder = new Finder(sql);
		finder.setParam("ids", ids);
		update(finder);

	}

	@Override
	public Map<String, Object> queryObjectByFunction(Finder finder) throws Exception {
		return queryObjectByProc(finder);
	}

	@Override
	public Map<String, Object> queryObjectByProc(Finder finder) throws Exception {
		String procName = finder.getProcName();
		String functionName = finder.getFunName();
		if (StringUtils.isBlank(procName) && StringUtils.isBlank(functionName)) {
			throw new NullPointerException("存储过程和函数不能同时为空!");
		}
		Map<String, Object> params = finder.getParams();
		Map<String, Object> m = new HashMap<String, Object>(0);
		SimpleJdbcCall simpleJdbcCall = null;

		if (StringUtils.isNotBlank(procName)) {
			simpleJdbcCall = getJdbcCall().withProcedureName(procName);
		} else {
			simpleJdbcCall = getJdbcCall().withFunctionName(functionName);
		}

		try {
			if (params == null) {
				params = new HashMap<String, Object>(0);
			}
			m = simpleJdbcCall.execute(params);
		} catch (EmptyResultDataAccessException e) {
			m = null;
		}
		return m;
	}

	@Override
	public <T> List<T> queryForListByFunction(Finder finder, Class<T> clazz) throws Exception {
		return queryForListByProc(finder, clazz);
	}

	@Override
	public <T> T queryForObjectByProc(Finder finder, Class<T> clazz) throws Exception {
		T t = null;
		String procName = finder.getProcName();
		if (StringUtils.isEmpty(procName)) {
			return null;
		}
		Map<String, Object> params = finder.getParams();
		try {
			if (params == null) {
				params = new HashMap<String, Object>(0);
			}
			t = (T) getJdbcCall().withProcedureName(procName).executeObject(clazz, params);

		} catch (EmptyResultDataAccessException e) {
			t = null;
		}

		return t;
	}

	@Override
	public <T> T queryForObjectByByFunction(Finder finder, Class<T> clazz) throws Exception {
		String funName = finder.getFunName();
		T t = null;
		if (StringUtils.isEmpty(funName)) {
			return null;
		}
		Map<String, Object> params = finder.getParams();
		try {
			if (params == null) {
				params = new HashMap<String, Object>(0);
			}
			t = getJdbcCall().withFunctionName(funName).executeFunction(clazz, params);

		} catch (EmptyResultDataAccessException e) {
			t = null;
		}
		return t;
	}

	/**
	 * Entity作为查询的query bean,并返回Entity
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> T queryForObject(T entity) throws Exception {
		String tableName = ClassUtils.getTableName(entity);
		Finder finder = new Finder("SELECT * FROM ");
		finder.append(tableName).append("  WHERE 1=1 ");
		getFinderWhereByQueryBean(finder, entity);
		// 打印sql
		logInfoSql(finder.getSql());
		return (T) queryForObject(finder, entity.getClass());

	}

	/**
	 * Entity作为查询的query bean,并返回Entity
	 * 
	 * @param entity
	 * @param page
	 *            分页对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> queryForListByEntity(T entity, Page page) throws Exception {
		String tableName = ClassUtils.getTableName(entity);
		Finder finder = new Finder("SELECT * FROM ");
		finder.append(tableName).append("  WHERE 1=1 ");
		getFinderWhereByQueryBean(finder, entity);
		// 打印sql
		logInfoSql(finder.getSql());
		return (List<T>) queryForList(finder, entity.getClass(), page);

	}

	/**
	 * 执行 call 操作,执行存储过程,和数据库函数
	 * 
	 * @param callableStatementCreator
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Object executeCallBack(CallableStatementCreator callableStatementCreator,
			List<SqlParameter> parameter) throws Exception {
		return getWriteJdbc().getJdbcOperations().call(callableStatementCreator, parameter);

	}

	/*
	 * //private String dataBaseType = null; //private String dataBaseVersion =
	 * null; //private List<String> dataBaseAllTables;
	 * 
	 * @Override public String getDataBaseVersion() { if (dataBaseVersion !=
	 * null) { return dataBaseVersion; } getDataBaseType(); return
	 * dataBaseVersion; }
	 * 
	 * @Override public String getDataBaseType() { if (dataBaseType != null) {
	 * return dataBaseType; } String databaseProductName =
	 * getJdbc().getJdbcOperations().execute( new ConnectionCallback<String>() {
	 * public String doInConnection(Connection connection) throws SQLException,
	 * DataAccessException { String databaseProductName =
	 * connection.getMetaData() .getDatabaseProductName(); dataBaseVersion =
	 * connection.getMetaData() .getDatabaseProductVersion(); return
	 * databaseProductName; } }); if (databaseProductName == null) { throw new
	 * NullPointerException("dataBase DriverName is null"); }
	 * databaseProductName = databaseProductName.trim().toLowerCase(); if
	 * (databaseProductName.contains("mysql")) { this.dataBaseType = "mysql"; }
	 * else if (databaseProductName.contains("oracle")) { this.dataBaseType =
	 * "oracle"; } else if (databaseProductName.contains("db2")) {
	 * this.dataBaseType = "db2"; } else if
	 * (databaseProductName.contains("sqlserver")) { this.dataBaseType =
	 * "mssql"; } else if (databaseProductName.contains("jtds")) {
	 * this.dataBaseType = "mssql"; } else if
	 * (databaseProductName.contains("sybase")) { this.dataBaseType = "sybase";
	 * }
	 * 
	 * return dataBaseType; }
	 * 
	 * @Override public List<String> getDataBaseAllTables() { if
	 * (dataBaseAllTables != null) return dataBaseAllTables; dataBaseAllTables =
	 * getJdbc().getJdbcOperations().execute( new
	 * ConnectionCallback<List<String>>() { public List<String>
	 * doInConnection(Connection connection) throws SQLException,
	 * DataAccessException { List<String> tables = new ArrayList<String>();
	 * DatabaseMetaData dbMetaData = connection.getMetaData(); // 可为:"TABLE",
	 * "VIEW", "SYSTEM   TABLE", // "GLOBAL   TEMPORARY", "LOCAL   TEMPORARY",
	 * "ALIAS", // "SYNONYM" String[] types = { "TABLE" }; ResultSet tabs =
	 * dbMetaData.getTables(null, null, null, types);//只要表就好了 while
	 * (tabs.next()) { // 只要表名这一列 tables.add(tabs.getString("TABLE_NAME")); }
	 * return tables; } }); return dataBaseAllTables; }
	 */
}
