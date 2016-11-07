package com.centfor.cms.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsFriendSite;
import com.centfor.cms.service.IFriendSiteService;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * 站点管理service
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.CmsFriendSite
 */
@Service("friendsiteService")
public class FriendSiteServiceImpl extends BaseSuperCMSServiceImpl implements
		IFriendSiteService {

	@Override
	public String saveFriendSite(CmsFriendSite entity) throws Exception {
		entity.setState(1);
		String id = super.save(entity).toString();
		return id;
	}

	@Override
	@CacheEvict(value = GlobalStatic.qxCacheKey, allEntries = true)
	public Integer updateFriendSite(CmsFriendSite entity) throws Exception {
		Integer update = super.update(entity, true);
		return update;
	}

	@Override
	public CmsFriendSite findFriendSiteById(Object id) throws Exception {
		CmsFriendSite s = super.findById(id, CmsFriendSite.class);
		return s;

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
		CmsFriendSite friendsite= (CmsFriendSite) o;
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsFriendSite.class).append(" WHERE siteId=:siteId and state= 1 ");
		String siteId=friendsite.getSiteId();
		finder.setParam("siteId", siteId);
		super.getFinderWhereByQueryBean(finder, friendsite);
		finder.append(" order by sort asc ");
//		super.getFinderOrderBy(finder, page);  
		List<T> queryForList = super.queryForList(finder, clazz, page); 
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
