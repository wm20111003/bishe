package com.centfor.cms.service;

import java.util.List;

import com.centfor.cms.entity.CmsPicture;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.IBaseSuperCMSService;

public interface IPictureService extends IBaseSuperCMSService {
	/**
	 * 保存多个图片
	 * 
	 * @param List
	 *            <CmsPicture> entity
	 * @return
	 * @throws Exception
	 */
	String savePicture(List<CmsPicture> entity) throws Exception;

	/**
	 * 更新
	 * 
	 * @param CmsPicture
	 *            entity
	 * @return
	 * @throws Exception
	 */
	Integer updatePicture(CmsPicture entity) throws Exception;

	/**
	 * 根据SiteId查找
	 * 
	 * @param SiteId
	 * @return
	 * @throws Exception
	 */
	String deletePictureById(String id) throws Exception;

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CmsPicture findPictureById(Object id) throws Exception;

	/**
	 * 根据SiteId查找图片
	 * 
	 * @param SiteId
	 * @return
	 * @throws Exception
	 */
	List<CmsPicture> findPictureBySiteId(Finder finder, Page page,
			Class<CmsPicture> class1, CmsPicture picture) throws Exception;

	/**
	 * 保存单个图片
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String savePicture(CmsPicture entity) throws Exception;

	/**
	 * 修改关联的链接cms_link
	 * 
	 * @param CmsPicture
	 * @return
	 * @throws Exception
	 */
	String updatePictureInfo(CmsPicture entity) throws Exception;

	List<CmsPicture> findPics(String businessId) throws Exception;

}
