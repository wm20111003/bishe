package com.centfor.cms.service;


import java.util.List;

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsSite;
import com.centfor.system.service.IBaseSuperCMSService;
/**
 * TODO 在此加入类描述
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version  2013-07-29 11:36:48
 * @see com.centfor.bbz.service.SiteChannel
 */
public interface ISiteChannelService extends IBaseSuperCMSService {
	
	/**
	 * 根据栏目Id 查找使用该栏目的站点
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	List<CmsSite> findSiteByChannelId(String channelId) throws Exception;

	
	/**
	 * 根据站点ID,查找使用该站点使用的栏目
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findChannelBySiteId(String siteId) throws Exception;
	

	
	
		

	
}
