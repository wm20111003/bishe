package com.centfor.cms.service;

import java.util.Map;

import com.centfor.cms.entity.CmsSite;
import com.centfor.system.service.IBaseSuperCMSService;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see com.centfor.bbz.service.CmsSite
 */
public interface ISiteService extends IBaseSuperCMSService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveOrUpdateSite(CmsSite entity) throws Exception;

	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
//	Integer updateSite(CmsSite entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsSite findSiteById(Object id) throws Exception;
	/**
	 * 修改关联的CmsSiteChannel表
	 * @param id
	 * @return
	 * @throws Exception
	 */	
	void updateChannelSite(String siteId,String channelIds)throws Exception;
	/**
	 * 根据省份查询店铺，并查询店铺下的商品信息
	 * @author LiuYafei
	 * @param province
	 * @param ctx
	 * @return	map
	 * @throws Exception
	 */
	Map<String, Object> findSiteByPrivince(String province,String ctx)throws Exception;
}
