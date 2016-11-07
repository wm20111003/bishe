package com.centfor.system.service;

import java.util.List;

import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.system.entity.User;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see com.centfor.bbz.service.User
 */
public interface IUserService extends IBaseSuperCMSService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveUser(User entity) throws Exception;

	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateUser(User entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	User findUserById(Object id) throws Exception;
    
	void updateRoleUser(String userId,String roleIds)throws Exception;
    /**
     * 根据名字模糊查询用户
     * @param str
     * @return
     * @throws Exception
     */
	List<User> findLikeUser(String str,Page page)throws Exception;
	/**
	 * 查询用户是否存在
	 * @param account
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	boolean findUserExsit(String account,String userId)throws Exception;
	/**
	 * 根据角色查询用户
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	List<User> findUserByRole(String roleId) throws Exception;
	/**
	 * 根据id查询password_old是否与数据库中保存的密码一致
	 * @param id
	 * @param password_old
	 * @param password_new
	 * @return
	 * @throws Exception
	 */
	boolean changePassword(String id, String password_old, String password_new) throws Exception;
	/**
	 * 通过siteId查找所有的用户
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	List<User> findUserBySiteId(String siteId) throws Exception;
	
	/**
	 * @author lyf
	 * @time 2015.02.04
	 * @param id
	 * @return 响应信息
	 * @throws Exception
	 * 只有admin才能调用此方法
	 */
	ReturnDatas adminDelete(String id) throws Exception;
}
