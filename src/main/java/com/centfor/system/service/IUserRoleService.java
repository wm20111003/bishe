package com.centfor.system.service;

import java.io.File;
import java.util.List;

import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.entity.UserRole;
/**
 * TODO 在此加入类描述
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version  2013-07-29 11:36:48
 * @see com.centfor.bbz.service.UserRole
 */
public interface IUserRoleService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserRole findUserRoleById(Object id) throws Exception;
	
	
	
}
