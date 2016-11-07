package com.centfor.cms.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsContent;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.service.ILinkService;
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
 * @see com.centfor.bbz.service.impl.CmsSite
 */
@Service("linkService")
public class LinkServiceImpl extends BaseSuperCMSServiceImpl implements
		ILinkService {

	@Override
	public void deleteLink(String bussinessId, String ostype) throws Exception {
		if (StringUtils.isBlank(bussinessId)) {
			return;
		}
		Finder finder = new Finder(" delete from "
				+ Finder.getTableName(CmsLink.class));
		finder.append(" where businessId=:businessId ").setParam("businessId",
				bussinessId);
		if (StringUtils.isNotBlank(ostype)) {
			finder.append(" and ostype=:ostype").setParam("ostype", ostype);
		}
		super.update(finder);
	}

	@Override
	public List<String> findLinkThemeIds(String bussinessId) throws Exception {
		if (StringUtils.isBlank(bussinessId))
			return null;
		CmsLink qb = new CmsLink();
		qb.setBusinessId(bussinessId);
		List<CmsLink> themes = findListDataByFinder(null, null, CmsLink.class,
				qb);
		List<String> list = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(themes)) {
			for (CmsLink link : themes) {
				list.add(link.getFtlId());
			}
		}
		return list;
	}

	@Override
	public List<String> findNodeLinkThemeIds(String bussinessId)
			throws Exception {
		if (StringUtils.isBlank(bussinessId))
			return null;
		CmsLink qb = new CmsLink();
		qb.setBusinessId(bussinessId);
		List<CmsLink> themes = findListDataByFinder(null, null, CmsLink.class,
				qb);
		List<String> list = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(themes)) {
			for (CmsLink link : themes) {
				list.add(link.getNodeftlId());
			}
		}
		return list;
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
		CmsLink qb = null;
		if (o != null) {
			qb = (CmsLink) o;
		}
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsLink.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, qb);
		if (qb != null) {
			if (StringUtils.isNotBlank(qb.getBusinessId())) {
				finder.append(" and businessId=:businessId ").setParam(
						"businessId", qb.getBusinessId());
			}
			if (StringUtils.isNotBlank(qb.getOstype())) {
				finder.append(" and ostype=:ostype").setParam("ostype",
						qb.getOstype());
			}
		}
		finder.append(" and state=1 ");
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);

		return queryForList;
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案 全部查询（state=0,1）
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findAllListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		CmsLink qb = null;
		if (o != null) {
			qb = (CmsLink) o;
		}
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsLink.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, qb);
		if (qb != null) {
			if (StringUtils.isNotBlank(qb.getBusinessId())) {
				finder.append(" and businessId=:businessId ").setParam(
						"businessId", qb.getBusinessId());
			}
			if (StringUtils.isNotBlank(qb.getOstype())) {
				finder.append(" and ostype=:ostype").setParam("ostype",
						qb.getOstype());
			}
		}
		super.getFinderOrderBy(finder, page);
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

	@Override
	@Cacheable(value = GlobalStatic.cacheKey, key = "'findLoginReqBusiness'")
	public List<String> findLoginReqBusiness() throws Exception {
		// 栏目
		Finder f = Finder.getSelectFinder(CmsChannel.class, "id").append(
				" where loginReq=1 ");
		List<String> list = super.queryForList(f, String.class);
		// 内容
		Finder f2 = Finder.getSelectFinder(CmsContent.class, "id").append(
				" where loginReq=1 ");
		List<String> list2 = super.queryForList(f2, String.class); 
		list.addAll(list2);
		return list;
	}

}
