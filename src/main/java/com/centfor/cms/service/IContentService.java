package com.centfor.cms.service;

import java.util.List;

import com.centfor.cms.entity.CmsChannelContent;
import com.centfor.cms.entity.CmsContent;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.entity.CmsSite;
import com.centfor.system.service.IBaseSuperCMSService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.CmsChannel
 */
public interface IContentService extends IBaseSuperCMSService {

	/**
	 * 保存文章内容
	 * 
	 * @param entity
	 * @param site,content,listmid,listlink,picture
	 * @return
	 * @throws Exception
	 */
	public void saveOrUpdateChannelContent(CmsSite site,CmsContent content,List<CmsChannelContent> listmid, List<CmsLink> listlink,CmsPicture picture)
			throws Exception;
	/**
	 * 删除文章内容
	 * 
	 * @param entity
	 * @param content
	 * @param qb 
	 * @return
	 * @throws Exception
	 */
	void deleteChannelContent(CmsContent content, CmsChannelContent qb) throws Exception;



}
