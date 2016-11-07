package com.centfor.cms.service;

import com.centfor.system.service.IBaseSuperCMSService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2014-11-18 15:51:46
 * @see com.centfor.cms.service.CmsTableindex
 */
public interface ICmsTableindexService extends IBaseSuperCMSService {
	/**
	 * 更新获取一个可用的最新Id,并更新maxindex
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	String saveNewId(Class clazz) throws Exception;
	
}
