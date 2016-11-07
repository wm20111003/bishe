package com.centfor.cms.service;

import java.util.List;

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.entity.CmsSiteChannel;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.IBaseSuperCMSService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.CmsChannel
 */
public interface IChannelService extends IBaseSuperCMSService {

	/**
	 * 保存栏目
	 * 
	 * @param entity
	 * @param listlink
	 * @return
	 * @throws Exception
	 */
	public void saveOrUpdateSiteChannel(CmsSite site, CmsChannel channel,
			List<CmsSiteChannel> listsc, List<CmsLink> listlink)
			throws Exception;

	/**
	 * 查找Org的树形结构
	 * 
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findTreeChannel() throws Exception;

	/**
	 * 修改关联的CmsSiteChannel表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	void updateChannelSite(String siteId, String channelId) throws Exception;

	/**
	 * 查询站点下的栏目
	 * 
	 * @param siteId
	 * @param ostype
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findChannel(String siteId) throws Exception;
    /**
     * 根据条件查询channel
     * @param qb
     * @param page
     * @return
     * @throws Exception
     */
	List<CmsChannel> findChannelByQb(Finder finder,CmsChannel qb, Page page) throws Exception;
	/**
	 * 更新系统栏目标识  
	 * @param channelId
	 * @param channelSort
	 * @throws Exception
	 */
	void updateChannelSort(String siteId,String channelId,String channelSort)throws Exception;
	/**
	 * 根据栏目标识查询url
	 * @param channelSort
	 * @param ostype
	 * @throws Exception
	 */
	CmsLink findLinkByChannelSort(String siteId,String channelSort,String ostype)throws Exception;
	/**
	 * 查询栏目下子栏目（递归往下查）
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findChildChannel(String channelId,String ostype)throws Exception;
	/**
	 * 查询栏目父栏目（递归往上查）
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	List<CmsChannel> findParentChannel(String channelId, String ostype,String ctx)
			throws Exception;
	
	
	List<CmsChannel> findChannelByContentId(String contentId) throws Exception;

	CmsChannel findById(String channelId, String siteId, String osType,
			Integer channeltype) throws Exception;

	CmsChannel findById(String id) throws Exception;
}
