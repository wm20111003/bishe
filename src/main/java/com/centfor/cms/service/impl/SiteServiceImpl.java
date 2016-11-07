package com.centfor.cms.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.entity.CmsSiteChannel;
import com.centfor.cms.entity.CmsTheme;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.ICmsTableindexService;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.IPictureService;
import com.centfor.cms.service.ISiteChannelService;
import com.centfor.cms.service.ISiteService;
import com.centfor.cms.service.IThemeGroupService;
import com.centfor.cms.service.IThemeService;
import com.centfor.common.service.InitService;
import com.centfor.frame.common.SessionUser;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.entity.User;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * 站点管理service
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.CmsSite
 */
@Service("siteService")
public class SiteServiceImpl extends BaseSuperCMSServiceImpl implements
		ISiteService {
	@Resource
	private IPictureService pictureService;
	@Resource
	IThemeGroupService themeGroupService;
	@Resource
	private ISiteChannelService siteChannelService;
	@Resource
	private IThemeService themeService;
	@Resource
	private ILinkService linkService;
	@Resource
	private ICmsTableindexService tableindexService;

	@Resource
	private InitService initService;
	
	@Resource
	IChannelService channelService;
	private void saveLogo(CmsSite site) throws Exception {
		if (StringUtils.isNotBlank(site.getLogo())) {
			CmsPicture pic = new CmsPicture();
			pic.setSiteId(site.getId());
			pic.setBusinessId(site.getId());
			pic.setFilepath(site.getLogo());
			pic.setName(site.getName());
			pic.setCreateDate(new Date());
			pic.setState(1);
			pic.setModelType("logo");
			super.save(pic);
		}
	}

	private void updateLogo(CmsSite site) throws Exception {
		String businessId = site.getId();
		Finder finder = Finder
				.getSelectFinder(CmsPicture.class)
				.append(" where state=1 and businessId=:businessId and modelType='logo' ")
				.setParam("businessId", businessId);
		CmsPicture pic = super.queryForObject(finder, CmsPicture.class);
		if (pic != null) {
			pic.setFilepath(site.getLogo());
			super.update(pic);
		}
	}

	@Override
	public String saveOrUpdateSite(CmsSite site) throws Exception {
		String siteId = site.getId();
		if (StringUtils.isBlank(siteId)) {
			site.setId(tableindexService.saveNewId(CmsSite.class));
			siteId = super.save(site).toString();

			// 如果是第一次创建初始化系统数据
			boolean f = initService.saveInit(siteId, SessionUser.getUserCode());

			if (!f) {
				throw new Exception("初始化数据失败");
			}

			// 保存logo
			saveLogo(site);
		} else {
			super.update(site);
			updateLogo(site);
		}

		// 保存站点主题
		List<String> siteThemeIds = site.getSiteThemeIds();
		List<String> channelThemeIds = site.getChannelThemeIds();
		List<CmsLink> listlink = new ArrayList<CmsLink>();
		// 用户站点目录
		User user = super.findById(site.getUserId(), User.class);
		String usersitepath = user.getAccount() + "/" + siteId + "/";
		if (CollectionUtils.isNotEmpty(siteThemeIds)) {
			List<CmsTheme> siteThemes = themeService.findByIds(siteThemeIds);
			// 子模板 平台 路径
			Map<String, String> map_channel_theme = new HashMap<String, String>();
			if (CollectionUtils.isNotEmpty(channelThemeIds)) {
				List<CmsTheme> channelThemes = themeService
						.findByIds(channelThemeIds);
				for (CmsTheme th : channelThemes) {
					map_channel_theme.put(th.getOstype(), th.getId());
				}
			}
			for (CmsTheme th : siteThemes) {
				String themeId = th.getId();
				String ostype = th.getOstype();
				// 查询主题在site的link中是否存在，不存在复制
				String ftlfile = themeService.findThemeUrlInSite(themeId,
						siteId);
				if (StringUtils.isBlank(ftlfile)) {
					ftlfile = themeService.saveCopyTheme(themeId,
							site.getUserId(), siteId);
				}
				// link表写入数据
				CmsLink link = new CmsLink();
				link.setName(site.getName());
				link.setIsoutlink("n");
				link.setDefaultLink("/front/" + ostype + "/" + siteId);
				link.setLink(link.getDefaultLink());
				link.setBusinessId(siteId);
				link.setSiteId(siteId);
				link.setOstype(ostype);
				link.setModelType(th.getModelType());

				// 站点模板
				link.setFtlfile("/userpage/" + usersitepath + themeId + "/t");
				link.setFtlId(themeId);
				// 栏目模板
				if (map_channel_theme != null && map_channel_theme.size() > 0) {
					link.setNodeftlId(map_channel_theme.get(link.getOstype()));
				}
				link.setState(1);
				link.setSort(1);
				listlink.add(link);
			}
			// 删除link
			linkService.deleteLink(site.getId(), null);
			if (CollectionUtils.isNotEmpty(listlink)) {
				super.save(listlink);
			}
		}
		// 删除图片
		Finder finderDeletePic = new Finder("delete from "
				+ Finder.getTableName(CmsPicture.class)
				+ " where businessId=:businessId  and modelType='content' ");
		finderDeletePic.setParam("businessId", siteId);
		super.update(finderDeletePic);
		if (site.getPics() != null) {
			for (CmsPicture p : site.getPics()) {
				p.setBusinessId(siteId);
				p.setSiteId(siteId);
				super.save(p);
			}
		}

		return siteId;
	}

	@Override
	public CmsSite findSiteById(Object id) throws Exception {
		CmsSite s = super.findById(id, CmsSite.class);
		String siteId = s.getId();
		List<CmsChannel> channelBySiteId = siteChannelService
				.findChannelBySiteId(siteId);
		s.setSiteChannels(channelBySiteId);
		// 查询图片
		List<CmsPicture> ps = pictureService.findPics(siteId);
		s.setPics(ps);	
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
		CmsSite site = (CmsSite) o;
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsSite.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, site);
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
	@Caching(evict = {
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findChannelBySiteId_'+#siteId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'getChannelsAsString_'+#siteId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findSiteByChannelId_'+#siteId") })
	public void updateChannelSite(String siteId, String channelId)
			throws Exception {
		// 删除
		Finder finder = Finder.getDeleteFinder(CmsSiteChannel.class).append(
				" WHERE siteId=:siteId");
		finder.setParam("siteId", siteId);
		this.update(finder);
		// 添加
		String[] channelIds = channelId.split(",");
		List<CmsSiteChannel> list = new ArrayList<CmsSiteChannel>();

		for (String str : channelIds) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			CmsSiteChannel sc = new CmsSiteChannel();
			sc.setSiteId(siteId);
			sc.setChannelId(str);
			list.add(sc);
		}

		if (!CollectionUtils.isEmpty(list)) {
			super.save(list);
		}

	}
	
	/**
	 * 根据省份查询店铺，并查询店铺下的商品信息
	 * @author LiuYafei
	 * @param province
	 * @param ctx
	 * @param siteId
	 * @return	map
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> findSiteByPrivince(String province,String ctx)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		CmsSite site = new CmsSite();
		//site.setProvince(province);
		/*医疗分销项目，不根据省份取得店铺，默认医疗站点，id为a7 */
		site.setId("a7");
		List<CmsSite> sites = new ArrayList<CmsSite>();
		sites = super.queryForListByEntity(site, null);
		/*if(sites==null||sites.size()<1){
			site.setProvince(null);
			site.setProvincePinyin(province.toLowerCase().trim());
			sites=super.queryForListByEntity(site, null);
		}*/
		//需测试
		//site = super.queryForObject(site);
		if(CollectionUtils.isNotEmpty(sites)){
			site = sites.get(0);
			//List<CmsChannel> channelBySiteId = siteChannelService
					//.findChannelBySiteId(site.getId());
			//site.setSiteChannels(channelBySiteId);
			// 查询图片
			List<CmsPicture> ps = pictureService.findPics(site.getId());
			site.setPics(ps);
			
			//首页，快捷按钮栏目 list
			String ostype = "weixin";
			CmsChannel qb = new CmsChannel();
			qb.setSiteId(site.getId());
			qb.setOstype(ostype);
			qb.setChannelType("0");
			qb.setPosition("中");
			qb.setName("");
			List<CmsChannel> list_above = queryForChannelList(qb,ctx,ostype);
			
			//商品分类区
			
			//将店铺信息塞入map
			map.put("site", site);
			map.put("list_above", list_above);
			//秒杀专区URL
			String secZoneURL = ctx+"/front/commonDirect?mark=seckillzone&ostype="+ostype+"&siteId="+site.getId();
			map.put("secZoneURL", secZoneURL);
			//秒杀请求数据URL
			String secAjaxURL = ctx + "/front/seckill/ajax/data?siteId="+site.getId();
			map.put("secAjaxURL", secAjaxURL);
			//塞入 促销专区，各个链接
			//团购专区
			String tuanGou=ctx+"/front/commonDirect?mark=groupbuyingzone&ostype="+ostype+"&siteId="+site.getId();
			map.put("tuanGou",tuanGou);
			//免邮专区
			String freePostage=ctx+"/front/commonDirect?mark=freePostage&ostype="+ostype+"&siteId="+site.getId();
			map.put("freePostage",freePostage);
			//名牌抢购
			String famousBrand=ctx+"/front/commonDirect?mark=famousBrand&ostype="+ostype+"&siteId="+site.getId();
			map.put("famousBrand",famousBrand);
			//新品上市
			String newProduct=ctx+"/front/commonDirect?mark=newProduct&ostype="+ostype+"&siteId="+site.getId();
			map.put("newProduct",newProduct);
		}
		return map;
	}
	
	/**
	 * 返回值包含子级数据
	 * @param qb
	 * @param ctx
	 * @param ostype
	 * @return
	 * @throws Exception
	 */
	public List<CmsChannel> queryForChannelListAndChild(CmsChannel qb,String ctx,String ostype) throws Exception{
		List<CmsChannel> list = null;
		list = channelService.findChannelByQb(null, qb, null);
		if (CollectionUtils.isNotEmpty(list)) {
			for (CmsChannel c : list) {
				//图片
				// 查询图片
				List<CmsPicture> ps = pictureService.findPics(c.getId());
				if(ps!=null&&ps.size()>0){
					c.setPic(ps.get(0));
				}
				// 查询栏目url
				CmsLink qlink = new CmsLink();
				qlink.setBusinessId(c.getId());
				qlink.setOstype(ostype);
				List<CmsLink> ls = linkService.findListDataByFinder(null,
						null, CmsLink.class, qlink);  
				if(CollectionUtils.isEmpty(ls)){ 
					continue; 
				}
				CmsLink mlink = ls.get(0);
				if ("n".equalsIgnoreCase(mlink.getIsoutlink())) {
					c.setLinkUrl(ctx + mlink.getLink());
				} else {
					if(mlink.getLink().startsWith("/front/")){
						c.setLinkUrl(ctx + mlink.getLink()); 
					}else{
					    c.setLinkUrl(mlink.getLink());
					}
				}
				c.setOpentype(mlink.getOpentype());
			
			
				//取得子级列表
				List<CmsChannel> list_child = null;
				list_child = channelService.findChildChannel(c.getId(), ostype);
				//将子级数据，塞入父级
				c.setLeafChannel(dieDai(list_child,ctx));
			}
		}
		return list;
	}
	
	public List<CmsChannel> dieDai(List<CmsChannel> list,String ctx)throws Exception{
		if (CollectionUtils.isNotEmpty(list)) {
			for (CmsChannel c : list) {
				
				//图片
				// 查询图片
				List<CmsPicture> ps = pictureService.findPics(c.getId());
				if(ps!=null&&ps.size()>0){
					c.setPic(ps.get(0));
				}
				if (c.getLinkUrl() != null) {
					if (c.getLinkUrl().startsWith("/front/")) {
						c.setLinkUrl(ctx + c.getLinkUrl());
					} else {
						c.setLinkUrl(c.getLinkUrl());   
					}
				} 
				if (c.getLeafChannel() != null) {
					for (CmsChannel cc : c.getLeafChannel()) { 
						if (cc.getLinkUrl() != null) {
							if (cc.getLinkUrl().startsWith("/front/")) {
								cc.setLinkUrl(ctx + cc.getLinkUrl());
							} else {
								cc.setLinkUrl(cc.getLinkUrl());
							}
						} 
					}
				}
			}
		}
		return list;
	}
	/**
	 * 查询Channel信息，不包含子级信息
	 * @param qb
	 * @param ctx
	 * @param ostype
	 * @return	List<CmsChannel>
	 * @throws Exception
	 */
	public List<CmsChannel> queryForChannelList(CmsChannel qb,String ctx,String ostype) throws Exception{
		List<CmsChannel> list = null;
		list = channelService.findChannelByQb(null, qb, null);
		if (CollectionUtils.isNotEmpty(list)) {
			for (CmsChannel c : list) {
				//图片
				// 查询图片
				List<CmsPicture> ps = pictureService.findPics(c.getId());
				if(ps!=null&&ps.size()>0){
					c.setPic(ps.get(0));
				}
				// 查询栏目url
				CmsLink qlink = new CmsLink();
				qlink.setBusinessId(c.getId());
				qlink.setOstype(ostype);
				List<CmsLink> ls = linkService.findListDataByFinder(null,
						null, CmsLink.class, qlink);  
				if(CollectionUtils.isEmpty(ls)){ 
					continue; 
				}
				CmsLink mlink = ls.get(0);
				if ("n".equalsIgnoreCase(mlink.getIsoutlink())) {
					c.setLinkUrl(ctx + mlink.getLink());
				} else {
					if(mlink.getLink().startsWith("/front/")){
						c.setLinkUrl(ctx + mlink.getLink()); 
					}else{
					    c.setLinkUrl(mlink.getLink());
					}
				}
				c.setOpentype(mlink.getOpentype());
			}
		}
		return list;
	}
}
