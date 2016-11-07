package com.centfor.school.service;

import com.centfor.school.entity.WorkDocument;
import com.centfor.system.service.IBaseSuperCMSService;;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-04-29 16:06:13
 * @see com.centfor.school.service.WorkDocument
 */
public interface IWorkDocumentService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	WorkDocument findWorkDocumentById(Object id) throws Exception;
	
	
	
}
