package com.centfor.cms.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.entity.CmsSiteChannel;
import com.centfor.cms.service.ISiteChannelService;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version  2013-07-29 11:36:48
 * @see com.centfor.bbz.service.impl.CmsSiteChannel
 */
@Service("siteChannelService")
public class SiteChannelServiceImpl extends BaseSuperCMSServiceImpl implements ISiteChannelService {

   

	@Override
	public List<CmsSite> findSiteByChannelId(String channelId) throws Exception {
		if(StringUtils.isBlank(channelId)){
			return null;
		}
		Finder finder=new Finder("SELECT s.* FROM ").append(Finder.getTableName(CmsSite.class)).append("  s,").append(Finder.getTableName(CmsSiteChannel.class)).append(" sc where sc.siteId=s.id and sc.channelId=:channelId");
		finder.setParam("channelId", channelId);
		return super.queryForList(finder,CmsSite.class);
	}



	@Override
	public List<CmsChannel> findChannelBySiteId(String siteId) throws Exception {
		if(StringUtils.isBlank(siteId)){
			return null;
		}
		Finder finder=new Finder("SELECT c.* FROM  ").append(Finder.getTableName(CmsSiteChannel.class)).append(" sc ,").append(Finder.getTableName(CmsChannel.class)).append(" c  WHERE sc.siteId=:siteId and c.id=sc.channelId  ");
		finder.setParam("siteId", siteId);
		return super.queryForList(finder, CmsChannel.class);
	}



	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object queryBean) throws Exception {
		CmsSiteChannel qb=null;
		if(queryBean!=null){
			qb=(CmsSiteChannel) queryBean;
		}
		// ==执行分页查询
		finder = Finder.getSelectFinder(CmsSiteChannel.class).append(" WHERE 1=1 ");
		super.getFinderWhereByQueryBean(finder, qb);
		if(qb!=null){
			if(StringUtils.isNotBlank(qb.getChannelId()) ){
				finder.append(" and channelId=:channelId").setParam("channelId", qb.getChannelId());
			}
			if(StringUtils.isNoneBlank(qb.getSiteId())){
				finder.append(" and siteId=:siteId").setParam("siteId", qb.getSiteId());
			}
		}
		finder.append(" and state=1 ");
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);
		return queryForList;
	}



}
