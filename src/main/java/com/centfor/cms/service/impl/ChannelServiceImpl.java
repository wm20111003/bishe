package com.centfor.cms.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsChannelContent;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.entity.CmsSiteChannel;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.ICmsTableindexService;
import com.centfor.cms.service.IPictureService;
import com.centfor.cms.service.IThemeService;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:02:59
 * @see com.centfor.bbz.service.impl.Channel
 */
@Service("channelService")
public class ChannelServiceImpl extends BaseSuperCMSServiceImpl implements
		IChannelService {
	@Resource
	ICmsTableindexService cmsTableindexService;
	@Resource
	IThemeService themeService;
	@Resource
	private IPictureService pictureService;

	@Override
	public List<CmsChannel> findChildChannel(String channelId, String ostype)
			throws Exception {
		CmsChannel c = new CmsChannel();

		findNextChild(channelId, ostype, c);
		if (c != null
				&& org.apache.commons.collections.CollectionUtils.isNotEmpty(c
						.getLeafChannel())) {
			return c.getLeafChannel();
		}
		return null;
	}
	@Override
	public CmsChannel findById(String id) throws Exception{
		CmsChannel entity = super.findById(id, CmsChannel.class);
		List<CmsPicture> ps = pictureService.findPics(id);
		if(ps!=null&&ps.size()>0){
			entity.setPic(ps.get(0));
		}
		return  entity;
	}

	private void findNextChild(String channelId, String ostype, CmsChannel ch)
			throws Exception {
		/*
		 * Finder finder = Finder .getSelectFinder(CmsChannel.class,
		 * " c.*,l.link as linkUrl ") .append(" as c ") .append(" left join ")
		 * .append(Finder.getTableName(CmsLink.class)) .append(" as l ")
		 * .append(
		 * " on l.businessId=c.id  and l.ostype=:ostype and l.state=1  ")
		 * .setParam("ostype", ostype); finder.append(" where 1=1 ");
		 * finder.append(" and c.pid=:pid ").setParam("pid", channelId);
		 */
		Finder finder = Finder
				.getSelectFinder(CmsChannel.class,
						" c.*,l.link as linkUrl,sc.sort as sort ")
				.append(" as c ")
				.append(" left join ")
				.append(Finder.getTableName(CmsLink.class))
				.append(" as l ")
				.append(" on l.businessId=c.id  and l.ostype=:ostype and l.state=1  ")
				.setParam("ostype", ostype).append(" left join ")
				.append(Finder.getTableName(CmsSiteChannel.class))
				.append(" as sc ")
				.append(" on sc.channelId=c.id and sc.ostype=:ostype ")
				.setParam("ostype", ostype);
		finder.append(" where 1=1 ");
		finder.append(" and c.pid=:pid order by sc.sort asc").setParam("pid",
				channelId);
		
		List<CmsChannel> ls = super.queryForList(finder, CmsChannel.class);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(ls)) {
			ch.setLeafChannel(ls);
			for (CmsChannel c : ls) {
				findNextChild(c.getId(), ostype, c);
			}
		}
	}

	@Override
	public List<CmsChannel> findParentChannel(String channelId, String ostype,
			String ctx) throws Exception {
		List<CmsChannel> list = new ArrayList<CmsChannel>();
		CmsChannel temp = super.findById(channelId, CmsChannel.class);
		if (temp != null) {
			Finder finder = Finder
					.getSelectFinder(CmsLink.class)
					.append("as l where l.businessId=:businessId and ostype=:ostype and 1=1")
					.setParam("businessId", channelId)
					.setParam("ostype", ostype);
			String link = super.queryForList(finder, CmsLink.class).get(0)
					.getLink();
			if (StringUtils.isNotBlank(link)) {
				if (link.startsWith("/front/")) {
					link = ctx + link;
				}
				temp.setLinkUrl(link);
				list.add(temp);
			}
			if (StringUtils.isNotBlank(temp.getPid())) {
				findNextParent(temp.getPid(), ostype, list, ctx);
			}
			if (list.size() > 0) {
				return list;
			}
		}
		return null;
	}

	private List<CmsChannel> findNextParent(String pid, String ostype,
			List<CmsChannel> list, String ctx) throws Exception {
		CmsChannel temp2 = super.findById(pid, CmsChannel.class);
		if (temp2 != null) {
			Finder finder2 = Finder
					.getSelectFinder(CmsLink.class)
					.append("as l where l.businessId=:businessId and ostype=:ostype and 1=1")
					.setParam("businessId", temp2.getId())
					.setParam("ostype", ostype);
			List<CmsLink> cmslinks = super.queryForList(finder2, CmsLink.class);
			if (org.apache.commons.collections.CollectionUtils
					.isNotEmpty(cmslinks)) {
				String link2 = cmslinks.get(0).getLink();
				if (StringUtils.isNotBlank(link2)) {
					if (link2.startsWith("/front/")) {
						link2 = ctx + link2;
					}
					temp2.setLinkUrl(link2);
				}
			}
			list.add(temp2);
			if (StringUtils.isNotBlank(temp2.getPid())) {
				findNextParent(temp2.getPid(), ostype, list, ctx);
			}
		}
		return list;
	}

	@Override
	@CacheEvict(value = GlobalStatic.cacheKey, key = "'findLoginReqBusiness'")
	public void saveOrUpdateSiteChannel(CmsSite site, CmsChannel channel,
			List<CmsSiteChannel> listsc, List<CmsLink> listlink)
			throws Exception {
		String siteId = site.getId();
		String channelId = channel.getId();
		if (StringUtils.isNotBlank(channelId)) {
			// 删除站点栏目
			Finder finder = Finder.getDeleteFinder(CmsSiteChannel.class)
					.append(" where siteId=:siteId and channelId=:channelId ");
			finder.setParam("siteId", siteId).setParam("channelId", channelId);
			super.update(finder);

			super.update(channel);
		} else {
			channel.setId(cmsTableindexService.saveNewId(CmsChannel.class));
			channelId = super.save(channel).toString();
		}
		// 保存站点栏目
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listsc)) {
			for (CmsSiteChannel sc : listsc) {
				sc.setChannelId(channelId);
			}
			super.save(listsc);
		}

		// 保存站点栏目的url
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listlink)) {
			for (CmsLink link : listlink) {
				link.setBusinessId(channelId);
				link.setDefaultLink("/front/" + link.getOstype() + "/"
						+ channelId);
				if (StringUtils.isBlank(link.getLink())) {
					link.setLink(link.getDefaultLink());
					link.setIsoutlink("n");
				} else {
					link.setIsoutlink("y");
				}
				link.setSort(1);
				link.setState(1);
				link.setSiteId(siteId);
				link.setName(channel.getName());
				// 获取栏目主题私有路径
				if (StringUtils.isNotBlank(link.getFtlId())) {
					String ftlfile = themeService.findThemeUrlInSite(
							link.getFtlId(), siteId);
					if (StringUtils.isBlank(ftlfile)) {
						// 站点下不存在所选主题
						ftlfile = themeService.saveCopyTheme(link.getFtlId(),
								site.getUserId(), siteId);
					}
					link.setFtlfile(ftlfile);
				}

			}
		}
		// 删除该站点栏目的url
		Finder finder2 = new Finder("delete from "
				+ Finder.getTableName(CmsLink.class)
				+ " where siteId=:siteId and businessId=:channelId ");
		finder2.setParam("siteId", siteId).setParam("channelId", channelId);
		super.update(finder2);
		// 删除图片
		Finder finderDeletePic = new Finder("delete from "
				+ Finder.getTableName(CmsPicture.class)
				+ " where businessId=:businessId  and modelType='content' ");
		finderDeletePic.setParam("businessId", channelId);
		super.update(finderDeletePic);
		CmsPicture p = channel.getPic();
		p.setBusinessId(channelId);
		super.save(p);

		super.save(listlink);

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
		CmsChannel qb = null;
		if (o != null) {
			qb = (CmsChannel) o;
		}
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsChannel.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, qb);
		if (StringUtils.isNotBlank(qb.getSiteId())) {
			finder.append(
					" and id in (select channelId from "
							+ Finder.getTableName(CmsSiteChannel.class) + ""
							+ " where siteId=:siteId ").setParam("siteId",
					qb.getSiteId());
			if (StringUtils.isNotBlank(qb.getChannelType())) {
				finder.append(" and  channelType=:channelType ").setParam(
						"channelType", qb.getChannelType());
			}
			finder.append(")");
		}
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);

		return queryForList;
	}

	@Override
	public List<CmsChannel> findChannelByQb(Finder finder, CmsChannel qb,
			Page page) throws Exception {
		if (finder == null) {
			finder = new Finder();
		}
		finder.append(" select c.* from  "
				+ Finder.getTableName(CmsChannel.class) + " as c,");
		finder.append(Finder.getTableName(CmsSiteChannel.class)
				+ " as mid  where c.id=mid.channelId  ");
		if (qb != null) {
			if (StringUtils.isNotBlank(qb.getSiteId())) {
				finder.append(" and mid.siteId=:siteId ").setParam("siteId",
						qb.getSiteId());
			}
			if (StringUtils.isNotBlank(qb.getChannelType())) {
				finder.append(" and mid.channelType=:channelType ").setParam(
						"channelType", qb.getChannelType());
			}
			if (StringUtils.isNotBlank(qb.getName())) {
				finder.append(" and c.name=:name ").setParam(
						"name", qb.getName());
			}
			if (StringUtils.isNotBlank(qb.getChannelSort())) {
				finder.append(" and c.channelSort=:channelSort ").setParam(
						"channelSort", qb.getChannelSort());
			}
			if (StringUtils.isNotBlank(qb.getOstype())) {
				finder.append(" and mid.ostype=:ostype ").setParam("ostype",
						qb.getOstype());
			}
			if (StringUtils.isNotBlank(qb.getPosition())) {
				finder.append(" and mid.position=:position ").setParam(
						"position", qb.getPosition());
			}
			if (StringUtils.isNotBlank(qb.getPid())) {
				if ("none".equalsIgnoreCase(qb.getPid())) {
					finder.append(" and c.pid is null ");
				} else {
					finder.append(" and c.pid=:pid ").setParam("pid",
							qb.getPid());
				}
			}
		}
		if (StringUtils.isBlank(finder.getOrderSql())) {
			finder.append(" order by c.id asc ");
		}
		List<CmsChannel> list = super.queryForList(finder, CmsChannel.class,
				page);
		return list;
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

	/**
	 * 查找Channel的树形结构
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CmsChannel> findTreeChannel() throws Exception {
		Finder finder = Finder.getSelectFinder(CmsChannel.class).append(
				" WHERE 1=1 ");

		List<CmsChannel> list = super.queryForList(finder, CmsChannel.class);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<CmsChannel> wrapList = new ArrayList<CmsChannel>();
		diguiwrapList(list, wrapList, null);

		return wrapList;
	}

	private List<CmsChannel> diguiwrapList(List<CmsChannel> from,
			List<CmsChannel> tolist, String parentId) {
		if (CollectionUtils.isEmpty(from)) {
			return null;
		}

		for (int i = 0; i < from.size(); i++) {
			CmsChannel m = from.get(i);
			if (m == null) {
				// from.remove(i);
				// i=i-1;
				continue;
			}

			String pid = m.getPid();
			if ((pid == null) && (parentId != null)) {
				continue;
			}

			if ((parentId == m.getPid()) || (m.getPid().equals(parentId))) {
				List<CmsChannel> leaf = new ArrayList<CmsChannel>();
				m.setLeafChannel(leaf);
				tolist.add(m);
				// from.remove(i);
				// i=i-1;
				diguiwrapList(from, leaf, m.getId());
				if (CollectionUtils.isEmpty(leaf)) {
					continue;
				}

			}

		}

		return tolist;

	}

	@Caching(evict = {
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findChannelBySiteId_'+#siteId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'getChannelsAsString_'+#siteId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findSiteByChannelId_'+#siteId") })
	public void updateChannelSite(String siteId, String channelId)
			throws Exception {
		// 删除
		Finder finder = Finder.getDeleteFinder(CmsSiteChannel.class).append(
				" WHERE channelId=:channelId");
		finder.setParam("channelId", channelId);
		this.update(finder);
		// 添加
		String[] siteIds = siteId.split(",");
		List<CmsSiteChannel> list = new ArrayList<CmsSiteChannel>();

		for (String str : siteIds) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			CmsSiteChannel sc = new CmsSiteChannel();
			sc.setChannelId(channelId);
			sc.setSiteId(str);
			list.add(sc);
		}

		if (!CollectionUtils.isEmpty(list)) {
			super.save(list);
		}

	}

	@Override
	public List<CmsChannel> findChannel(String siteId) throws Exception {
		Finder finder = new Finder();
		finder.append("select * from " + Finder.getTableName(CmsChannel.class)
				+ " where id in ( select channelId from "
				+ Finder.getTableName(CmsSiteChannel.class) + "  ");
		finder.append(" where siteId=:siteId ) ").setParam("siteId", siteId);
		return queryForList(finder, CmsChannel.class);
	}
	
	@Override
	public CmsChannel findById(String channelId,String siteId,String osType,Integer channeltype) throws Exception {
		CmsChannel entity = super.findById(channelId, CmsChannel.class);
		List<CmsPicture> ps = pictureService.findPics(channelId);
		
		if(ps!=null&&ps.size()>0){
			entity.setPic(ps.get(0));
		}
		if(StringUtils.isNotBlank(siteId)&&StringUtils.isNotBlank(osType)&&channeltype!=null){
			
		
		Finder finder = Finder.getSelectFinder(CmsSiteChannel.class);
		finder.append(" where channelId=:channelId and siteId=:siteId  and ostype=:ostype and channeltype=:channeltype")
		.setParam("channelId", channelId).setParam("sietId", siteId).setParam("osType",osType).setParam("channeltype",channeltype);
		
		List<CmsSiteChannel> sc= super.queryForList(finder,CmsSiteChannel.class);
		if(sc!=null&&sc.size()>0){
			entity.setPosition(sc.get(0).getPosition());
		}
		}
		return entity;
	}

	@Override
	public void updateChannelSort(String siteId, String channelId,
			String channelSort) throws Exception {
		// 非系统栏目没有标识
		if (StringUtils.isNotBlank(channelSort)) {
			// 去掉旧标识
			Finder f = new Finder(" update "
					+ Finder.getTableName(CmsChannel.class)
					+ " set channelSort=null  where  channelSort=:channelSort ")
					.append(" and id in ( select channelId from  ")
					.append(Finder.getTableName(CmsSiteChannel.class))
					.append(" where siteId=:siteId group by channelId ) ")
					.setParam("channelSort", channelSort)
					.setParam("siteId", siteId);
			super.update(f);
			// 重新标识
			Finder f2 = new Finder(" update "
					+ Finder.getTableName(CmsChannel.class)
					+ " set channelSort=:channelSort  where id=:id").setParam(
					"id", channelId);
			super.update(f2);
		}

	}

	@Override
	public CmsLink findLinkByChannelSort(String siteId, String channelSort,
			String ostype) throws Exception {
		if (StringUtils.isBlank(channelSort) || StringUtils.isBlank(ostype))
			return null;
		Finder finder = Finder
				.getSelectFinder(CmsLink.class, " k.* ")
				.append(" as k, ")
				.append(Finder.getTableName(CmsChannel.class))
				.append(" as c, ")
				.append(Finder.getTableName(CmsSiteChannel.class))
				.append(" as mid ")
				.append(" where c.id=k.businessId and k.ostype=:ostype and c.channelSort=:channelSort ")
				.append(" and mid.channelId=c.id and mid.ostype=:ostype and mid.siteId=:siteId ")
				.setParam("siteId", siteId).setParam("ostype", ostype)
				.setParam("channelSort", channelSort);
		CmsLink link = super.queryForObject(finder, CmsLink.class);
		return link;
	}

	@Override
	public List<CmsChannel> findChannelByContentId(String contentId)
			throws Exception {
		Finder finder = Finder.getSelectFinder(CmsChannel.class);
		finder.append(" where id in(select channelId from "
				+ Finder.getTableName(CmsChannelContent.class)
				+ " where contentId=:contentId )");
		finder.setParam("contentId", contentId);
		return super.queryForList(finder, CmsChannel.class);
	}

}
