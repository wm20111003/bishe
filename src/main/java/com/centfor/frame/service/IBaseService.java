
/**
 * 
 */
package com.centfor.frame.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.SqlParameter;

import com.centfor.frame.entity.IBaseEntity;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.SpringUtils;


/**
 * 基础的Service接口,所有的Service都必须实现此接口,每个数据库都需要一个实现.</br> 
 * 例如 demo数据的实现接口是com.centfor.centfor.service.IBasecentforService,demo2数据的实现接口是com.centfor.demo2.service.IBasedemo2Service</br>
 * 
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2013-03-19 11:08:15
 * @see com.centfor.frame.service.IBaseService
 */
public interface IBaseService {
	/**
	 * 获取SpringUtils
	 * @return
	 * @throws Exception
	 */
	public SpringUtils getSpringUtils() throws Exception;
	/**
	 * 获取 获取 spring 中的Bean
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	public  Object getBean(String beanName) throws Exception;
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param baseService service 调用
	 * @param queryBean  querybean
	 * @return
	 * @throws Exception
	 */
	
	public<T> File findDataExportExcel(Finder finder,String ftlurl,Page page,Class<T> clazz,  Object queryBean) throws Exception;
	
	
	/**
	 * 查询结果是 List<Entity>
	 * @param finder
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForList(Finder finder, Class<T> clazz) throws Exception;
	
	/**
	 * 调用数据库函数  查询结果是 List<Entity>
	 * @param finder
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForListByFunction(Finder finder, Class<T> clazz) throws Exception;
	
	
	
	
	/**
	 * 调用数据库存储过程  查询结果是 List
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>> queryForListByProc(Finder finder) throws Exception;
	
	/**
	 * 调用数据库函数  查询结果是 List
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>> queryForListByFunction(Finder finder) throws Exception;
	
	
	/**
	 * 调用数据库存储过程  返回指定 对象
	 * @param <T>
	 * @param sql
	 * @param paramMap
	 * @param clazz
	 * @return
	 * @throws Exception
	 */

	public <T> T queryForObjectByProc(Finder finder, Class<T> clazz) throws Exception ;
	
	/**
	 * 调用数据库存储过程  返回指定 对象
	 * @param <T>
	 * @param sql
	 * @param paramMap
	 * @param clazz
	 * @return
	 * @throws Exception
	 */

	public <T> List<T> queryForListByProc(Finder finder, Class<T> clazz) throws Exception ;
	
	/**
	 *  调用数据库函数  返回指定 对象
	 * @param <T>
	 * @param sql
	 * @param paramMap
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T queryForObjectByByFunction(Finder finder, Class<T> clazz) throws Exception ;
	
	/**
	 * 执行函数 返回执行结果为Map
	 * @param finder
	 * @return
	 * @throws Exception 
	 */
	public Object queryObjectByFunction(Finder finder) throws Exception;
	
	
	/**
	 * 执行存储过程 返回执行结果为
	 * @param finder
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> queryObjectByProc(Finder finder) throws Exception;
	/**
	 * 返回结果是List<Map>
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryForList(Finder finder)  throws Exception ;
	
	/**
	 * 分页查询 返回 List<Map>
	 * @param finder
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>  queryForList(Finder finder,Page page) throws Exception ;
	
	

	
	/**
	 * 查询打个对象
	 * @param <T>
	 * @param sql
	 * @param paramMap
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T queryForObject(Finder finder, Class<T> clazz) throws Exception ;
	/**
	 * 查询一个map
	 * @param sql
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public  Map<String,Object>  queryForObject(Finder finder) throws Exception;
	
	
	/**
	 * Entity作为查询的query bean,并返回Entity
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public <T> T queryForObject(T entity) throws Exception ;
	
	/**
	 *  Entity作为查询的query bean,并返回Entity
	 * @param entity
	 * @param page 分页对象
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForListByEntity(T entity,Page page) throws Exception ;
	
	

	
	
	
	/**
	 * 更新数据
	 * @param sql
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Integer update(Finder finder) throws Exception;
	


  
	
	
	/**
	 * 分页查询,自己提供详细参数
	 * @param <T>
	 * @param sql
	 * @param countSql
	 * @param orderSql
	 * @param paramMap
	 * @param clazz
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> queryForList(Finder finder, Class<T> clazz, Page page) throws Exception;
	

	
/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param queryBean
 * @return
 * @throws Exception
 */
	public <T> List<T>  findListDataByFinder(Finder finder,Page page,Class<T> clazz,Object queryBean)  throws Exception;


    /**
     * 保存一个对象
     * @param <T>
     * @param clazz
     * @return
     */
	public  Object save( Object entity) throws Exception;
	
	
	/**
	 * 批量更新
	 * @param list
	 * @return List
	 * @throws Exception
	 */
		public List<Integer>  save(List list) throws Exception;
	
	
	 /**
     * 更新一个对象
     * @param <T>
     * @param clazz
     * @return
     */
	public  Integer update( IBaseEntity entity) throws Exception;
	
	
	
	/**
	 * 更新一个对象,id必须有值,updatenotnull=true 不更新为null的字段,false更新所有字段
	 * @param entity
	 * @param updatenotnull
	 * @return
	 * @throws Exception
	 */
		public Integer update(IBaseEntity entity,boolean onlyupdatenotnull) throws Exception;
	
	

	
	/**
	 * 批量更新对象
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public  List<Integer> update(List list) throws Exception;
	
	
	/**
	 * 批量更新对象,id必须有值,updatenotnull=true 不更新为null的字段,false更新所有字段
	 * @param list
	 * @param updatenotnull
	 * @return
	 * @throws Exception
	 */
	public  List<Integer> update(List list,boolean onlyupdatenotnull) throws Exception;
	
	
	
	
	/**
	 * 根据ID 查询对象
	 * @param <T>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public <T> T findById(Object id,Class<T> clazz)throws Exception;
	
	/**
	 * 根据ID 查询对象,用于分表
	 * @param id
	 * @param clazz
	 * @param tableExt
	 * @return
	 * @throws Exception
	 */
	public <T> T findById(Object id, Class<T> clazz,String tableExt) throws Exception ;
	
	/**
	 * 根据Id 删除
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(Object id,Class clazz) throws Exception;
	
	/**
	 * 根据ID批量删除
	 * @param ids
	 * @param clazz
	 * @throws Exception
	 */
	public void deleteByIds(List ids,Class clazz) throws Exception;
	
	/**
	 * 根据Entity 删除
	 * @param IBaseEntity entity
	 * @throws Exception
	 */
	public void deleteByEntity(IBaseEntity entity) throws Exception;
	
	/**
	 * 判断主键是否有值 save or update 对象
	 * @param entity
	 * @return
	 */
	public Object saveorupdate(Object entity) throws Exception ;
	
	
	/**
	 * 根据查询的queryBean,拼接Finder 的 Where条件,只包含 and 条件,用于普通查询
	 * @param finder
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public Finder getFinderWhereByQueryBean(Finder finder,Object o) throws Exception;
	/**
	 * 根据page对象中sort和order 添加order by 排序,一般用于前台传递的自定义排序
	 * 
	 * @param finder
	 * @param page
	 * @return
	 */
	Finder getFinderOrderBy(Finder finder, Page page) throws Exception;
	/**
	 * 导入Excle文件
	 * @param excel
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> String saveImportExcelFile(File excel,Class<T> clazz)throws Exception;
	
	/**
	 * 保存后的数据List
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Object savedFromExcel(List list)throws Exception;
	
	
	
	/**
	 * 导入Excle文件
	 * @param excel
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> String saveImportExcelFile(File excel,Class<T> clazz,boolean istest)throws Exception;
	
	
	
	/**
	 * Excel 导入时会循环调用该方法
	 * @param entity
	 * @param index TODO
	 * @param listTitle TODO
	 * @param issave TODO
	 * @return
	 * @throws Exception
	 */
   public String saveFromExcel(Object entity, int index, boolean istest, List<String> listTitle) throws Exception;
   /**
	   * 执行 call 操作,执行存储过程,和数据库函数
	   * @param callableStatementCreator
	   * @param parameter
	   * @return
	   * @throws Exception
	   */
	 public Object executeCallBack(CallableStatementCreator callableStatementCreator,List<SqlParameter> parameter)throws Exception;


}
