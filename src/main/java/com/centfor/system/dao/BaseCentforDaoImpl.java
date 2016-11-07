package com.centfor.system.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.centfor.frame.dao.BaseJdbcDaoImpl;
import com.centfor.frame.dao.dialect.IDialect;
import com.centfor.frame.entity.IAuditLog;
import com.centfor.system.entity.AuditLog;

/**
 *   centfor项目的基础Dao,代理demo数据库
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2013-03-19 11:08:15
 * @see com.centfor.system.dao.BaseCentforDaoImpl
 */
@Repository("baseCentforDao")
public class BaseCentforDaoImpl extends BaseJdbcDaoImpl implements IBaseCentforDao{

	/**
	 * demo  数据库的jdbc,对应 spring配置的 jdbc bean
	 */
	@Resource
	NamedParameterJdbcTemplate jdbc;
	/**
	 * demo  数据库的jdbcCall,对应 spring配置的 jdbcCall bean
	 */
	@Resource
	public SimpleJdbcCall jdbcCall;
    /**
     * mysqlDialect 是mysql的方言,springBean的name,可以参考 IDialect的实现
     */
	@Resource
	public IDialect mysqlDialect;
	@Override
	public IDialect getDialect() {
		return mysqlDialect;
	}

	public BaseCentforDaoImpl() {
	}


	/**
	 * 实现父类方法,demo  数据库的jdbc,对应 spring配置的 jdbc bean
	 */
	@Override
	public SimpleJdbcCall getJdbcCall() {
		return this.jdbcCall;
	}
	/**
	 * 实现父类方法,demo  数据库的jdbcCall,对应 spring配置的 jdbcCall bean
	 */
	@Override
	public NamedParameterJdbcTemplate getJdbc() {
		return jdbc;
	}


/**
 * 实现父类方法,返回记录日志的Entity接口实现,reutrn null 则代表不记录日志
 */
	@Override
	public IAuditLog getAuditLog() {
		//return null;
		return new AuditLog();
	}
	@Override
	public boolean showsql() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
