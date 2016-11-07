package com.centfor.school.service;

import com.centfor.school.entity.Record;
import com.centfor.system.service.IBaseSuperCMSService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-04-20 21:49:19
 * @see com.centfor.school.service.Record
 */
public interface IRecordService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Record findRecordById(Object id) throws Exception;
	
	Record findRecordByUserId(String UserId) throws Exception;
	
}
