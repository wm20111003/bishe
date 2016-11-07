package com.centfor.system.service;

import com.centfor.system.entity.Forgetpasswd;
import com.centfor.system.service.IBaseSuperCMSService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2014-09-12 22:52:27
 * @see com.centfor.system.service.Forgetpasswd
 */
public interface IForgetpasswdService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Forgetpasswd findForgetpasswdById(Object id) throws Exception;
	
	
	
}
