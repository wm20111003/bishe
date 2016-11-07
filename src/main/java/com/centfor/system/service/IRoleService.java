package com.centfor.system.service;

import com.centfor.system.entity.Role;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:02:59
 * @see com.centfor.bbz.service.Role
 */
public interface IRoleService extends IBaseSuperCMSService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveRole(Role entity) throws Exception;
	/**
	 * 修改或者保存,根据id是否为空判断
	 * @param entity
	 * @return
	 * @throws Exception
	 */
    String saveorupdateRole(Role entity) throws Exception;
	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateRole(Role entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Role findRoleById(Object id) throws Exception;
	/**
	 * 根据id查询name
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	String findNameById(String roleId)throws Exception;
	
}
