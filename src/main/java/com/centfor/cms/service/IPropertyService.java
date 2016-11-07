package com.centfor.cms.service;

import java.util.List;

import com.centfor.cms.entity.CmsProperty;
import com.centfor.system.service.IBaseSuperCMSService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.User
 */
public interface IPropertyService extends IBaseSuperCMSService {
	/**
	 * 添加或修改
	 * 
	 * @param en
	 * @throws Exception
	 */
	void saveupdate(CmsProperty en) throws Exception;

	/**
	 * 根据businessId查询 自定义字段
	 * 
	 * @param businessId
	 * @return
	 * @throws Exception
	 */
	List<CmsProperty> findByBusinessId(String businessId, String state)
			throws Exception;

	/**
	 * 查询父节点可继承的属性
	 * 
	 * @param businessId
	 * @param modelType
	 * @return
	 * @throws Exception
	 */
	List<CmsProperty> findParentProperty(String businessId, String modelType)
			throws Exception;


	List<CmsProperty> findByBusinessId(String businessId, String state,
			String bId, String modelType) throws Exception;

	List<CmsProperty> findProperty(String businessId, String contentId)
			throws Exception;
}
