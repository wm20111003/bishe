package com.centfor.cms.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsChannelContent;
import com.centfor.cms.entity.CmsContent;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.service.ICmsTableindexService;
import com.centfor.cms.service.IContentService;
import com.centfor.cms.service.IPictureService;
import com.centfor.cms.service.IThemeService;
import com.centfor.frame.common.SessionUser;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:02:59
 * @see com.centfor.bbz.service.impl.Channel
 */
@Service("contentService")
public class ContentServiceImpl extends BaseSuperCMSServiceImpl implements
		IContentService {
	@Resource
	private IPictureService pictureService;
	@Resource
	IThemeService themeService;
	@Resource
	ICmsTableindexService cmsTableindexService;

	@Override
	@CacheEvict(value = GlobalStatic.cacheKey, key = "'findLoginReqBusiness'")
	public void saveOrUpdateChannelContent(CmsSite site,
			CmsContent content, List<CmsChannelContent> listmid,
			List<CmsLink> listlink,CmsPicture picture) throws Exception {
		String siteId = site.getId();
		String contentId = content.getId();
		if (StringUtils.isNotBlank(content.getId())) {			
			//删除中间表
			Finder finder = new Finder("delete from "
					+ Finder.getTableName(CmsChannelContent.class)
					+ " where siteId=:siteId  and contentId=:contentId ");
			finder.setParam("siteId", siteId).setParam("contentId", contentId);   
			super.update(finder);
			Finder finderDeletePic = new Finder("delete from "
					+ Finder.getTableName(CmsPicture.class)
					+ " where businessId=:businessId  and modelType='content' ");
			finderDeletePic.setParam("businessId", contentId); 
			super.update(finderDeletePic);
			
			super.update(content);
		} else { 
			content.setCreateDate(new Date());
			content.setCreatePerson(SessionUser.getUserName());
			content.setId(cmsTableindexService.saveNewId(CmsContent.class));
			contentId = super.save(content).toString();
		}
		// 保存站点栏目
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listmid)) {
			for (CmsChannelContent mid : listmid) {
				mid.setContentId(contentId);
			}
			super.save(listmid);
		}
		// 保存站点栏目的url
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listlink)) {
			// 判断站点是否有了该主题，如果没有进行复制
			for (CmsLink link : listlink) {
				link.setBusinessId(contentId); 
				link.setDefaultLink("/front/" + link.getOstype() + "/"
						+ contentId);
				if (StringUtils.isBlank(link.getLink())) {
					link.setLink(link.getDefaultLink());
					link.setIsoutlink("n");
				} else {
					link.setIsoutlink("y");
				}
				link.setSort(1);
				link.setState(1);
				link.setSiteId(siteId);
				link.setOpentype("_self");
				link.setName(content.getTitle());
				/*link.setIsoutlink("no");*/
				String themeId = link.getFtlId();
				// 获取栏目主题私有路径
				if (StringUtils.isNotBlank(themeId)) {
					String ftlfile=themeService.findThemeUrlInSite(themeId, siteId);
					if(StringUtils.isBlank(ftlfile)){
						//站点下不存在所选主题
						ftlfile=themeService.saveCopyTheme(themeId, site.getUserId(), siteId); 
					}
					link.setFtlfile(ftlfile);
				}
			}
		}
		//保存图片
		picture.setBusinessId(contentId);
		super.save(picture);
		// 删除文章的link
		Finder finder2 = new Finder("delete from "
				+ Finder.getTableName(CmsLink.class)
				+ " where siteId=:siteId and businessId=:businessId ");
		finder2.setParam("siteId", siteId)
				.setParam("businessId", contentId);
		super.update(finder2);  
		//重新保存link
		super.save(listlink);    
	}
	
	@Override
	public void deleteChannelContent(CmsContent content,CmsChannelContent pb) throws Exception {
		String contentId=content.getId();
		String siteId=pb.getSiteId();
			//删除文章内容表
			super.update(content);
			//删除中间表
			super.update(pb);
			//删除链接表
			Finder finderDeleteLink = new Finder("select * from "
					+ Finder.getTableName(CmsLink.class)
					+ " where siteId=:siteId and businessId=:businessId ");
			finderDeleteLink.setParam("businessId", contentId).setParam("siteId", siteId);   
			List<CmsLink> list1=super.queryForList(finderDeleteLink, CmsLink.class);
			if(CollectionUtils.isNotEmpty(list1)){
				CmsLink link=list1.get(0);
				link.setState(0);	
				super.update(link);
			}
			//删除图片
			Finder finderDeletePic = new Finder("select * from "
					+ Finder.getTableName(CmsPicture.class)
					+ " where businessId=:businessId  and modelType='content' ");
			finderDeletePic.setParam("businessId", contentId);
			List<CmsPicture> list=super.queryForList(finderDeletePic, CmsPicture.class);
			if(CollectionUtils.isNotEmpty(list)){
				CmsPicture picture=list.get(0);
				picture.setState(0);
				super.update(picture);
			}
			
				
		
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
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		CmsContent qb = (CmsContent) o;
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsContent.class).append(" WHERE 1=1 and state=1");

		super.getFinderWhereByQueryBean(finder, qb);
		// 查询条件
		if (qb != null) {
			// 站点 栏目 平台
			if (StringUtils.isNoneBlank(qb.getSiteId())) {
				finder.append(" and id in ( select contentId from  "
						+ Finder.getTableName(CmsChannelContent.class));
				finder.append(" where 1=1 ");
				if (StringUtils.isNoneBlank(qb.getChannelId())) {
					finder.append(" and channelId=:channelId ").setParam(
							"channelId", qb.getChannelId());
				}
				if (StringUtils.isNoneBlank(qb.getOstype())) {
					finder.append(" and ostype=:ostype").setParam("ostype",
							qb.getOstype());
				}
				finder.append(")");
			}
		}
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);
	/*	for(CmsContent content: (List<CmsContent>)queryForList){
			Finder finderFindPicture = Finder.getSelectFinder(CmsPicture.class).append(" WHERE 1=1 and businessId=:businessId and modelType='content' ").setParam("businessId", content.getId()); 
			CmsPicture picture=pictureService.queryForObject(finderFindPicture, CmsPicture.class);
			content.setPicture(picture.getFilepath());
		}*/
		return queryForList;
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * 
	 * @param finder
	 *            为空则只查询 clazz表
	 * @param ftlurl
	 *            类表的模版宏
	 * @param page
	 *            分页对象
	 * @param clazz
	 *            要查询的对象
	 * @param o
	 *            querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl,
			Page page, Class<T> clazz, Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

}
