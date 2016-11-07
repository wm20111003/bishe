package com.centfor.school.service;

import com.centfor.school.entity.WorkVideo;
import com.centfor.system.service.IBaseSuperCMSService;;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-05-05 21:53:10
 * @see com.centfor.school.service.WorkVideo
 */
public interface IWorkVideoService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	WorkVideo findWorkVideoById(Object id) throws Exception;
	
	
	
}
