package com.centfor.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.IPictureService;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * 图片管理service
 * 
 * @copyright {@link 9iu.picture}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.CmsPicture
 */
@Service("pictureService")
public class PictureServiceImpl extends BaseSuperCMSServiceImpl implements
		IPictureService {

	@Resource
	private IPictureService pictureService;

	@Resource
	private ILinkService linkService;

	@Override
	public String savePicture(List<CmsPicture> entity) throws Exception {
		String save = super.save(entity).toString();
		return save;
	}

	@Override
	public String savePicture(CmsPicture entity) throws Exception {
		String pictureId = super.save(entity).toString();
		this.updatePictureInfo(entity);
		return pictureId;
	}

	@Override
	@CacheEvict(value = GlobalStatic.qxCacheKey, allEntries = true)
	public Integer updatePicture(CmsPicture entity) throws Exception {
		Integer update = super.update(entity, true);
		this.updatePictureInfo(entity);
		return update;
	}

	// 修改关联的链接link
	@Override
	public String updatePictureInfo(CmsPicture entity) throws Exception {
		List<CmsLink> list = entity.getPictureLinks();
		String businessId = entity.getId();
		linkService.deleteLink(businessId, null);
		for (CmsLink cl : list) {
			cl.setBusinessId(businessId);
		}
		return linkService.save(list).toString();
	};

	//
	// 删除文件夹中的原图片
	//
	@Override
	public String deletePictureById(String id) throws Exception {
		// 删除链接（link）
		linkService.deleteLink(id, null);
		// 删除图片
		Finder f_del = Finder.getDeleteFinder(CmsPicture.class).append(
				" WHERE id=:id and modelType='ppt'");
		f_del.setParam("id", id);
		return super.update(f_del).toString();
	}

	@Override
	public CmsPicture findPictureById(Object id) throws Exception {
		CmsPicture s = super.findById(id, CmsPicture.class);
		return s;
	}

	// 根据SiteId查询图片
	@Override
	public List<CmsPicture> findPictureBySiteId(Finder finder, Page page,
			Class<CmsPicture> clazz, CmsPicture picture) throws Exception {
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsPicture.class).append(
				" WHERE siteId=:siteId and modelType='ppt'");
		finder.setParam("siteId", picture.getSiteId());
		super.getFinderWhereByQueryBean(finder, picture);
		super.getFinderOrderBy(finder, page);
		List<CmsPicture> queryForList = super.queryForList(finder, clazz, page);
		return queryForList;
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		CmsPicture picture = (CmsPicture) o;
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsPicture.class, "distinct siteId")
				.append(" WHERE 1=1 ");
		super.getFinderWhereByQueryBean(finder, picture);
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);
		return queryForList;
	}

	@Override
	public List<CmsPicture> findPics(String businessId) throws Exception {
		Finder finder = new Finder("select * from "
				+ Finder.getTableName(CmsPicture.class)
				+ " where businessId=:businessId  and modelType='content' ");
		finder.setParam("businessId", businessId);
		List<CmsPicture> picture = pictureService.queryForList(finder,
				CmsPicture.class);
		return picture;
	}

}
