package com.centfor.cms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.cms.Constants;
import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsChannelContent;
import com.centfor.cms.entity.CmsProperty;
import com.centfor.cms.entity.CmsPropertyvalue;
import com.centfor.cms.entity.CmsSiteChannel;
import com.centfor.cms.service.IChannelContentService;
import com.centfor.cms.service.IPropertyService;
import com.centfor.frame.util.Finder;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.User
 */
@Service("propertyService")
public class PropertyServiceImpl extends BaseSuperCMSServiceImpl implements
		IPropertyService {
	@Resource
	IChannelContentService channelContentService;

	@Override
	public void saveupdate(CmsProperty en) throws Exception {
		if (StringUtils.isNotBlank(en.getId())) {
			super.update(en);
		} else {
			super.save(en);
		}

	}

	@Override
	public List<CmsProperty> findParentProperty(String businessId,
			String modelType) throws Exception {
		if (StringUtils.isBlank(businessId))
			return null;
		// 查询本身字段code
		Finder finder = Finder.getSelectFinder(CmsProperty.class, " code ");
		finder.append(" where state=1 and  businessId=:businessId ").setParam(
				"businessId", businessId);
		List<String> listcode = super.queryForList(finder, String.class);
		// 查询父字段
		List<CmsProperty> listp = new ArrayList<CmsProperty>();
		if (Constants.TPLDIR_INDEX.equalsIgnoreCase(modelType)) {
			// 空实现

		} else if (Constants.TPLDIR_CHANNEL.equalsIgnoreCase(modelType)) {
			// 递归查询父栏目 一直到站點 并排除子栏目中已有字段
			CmsChannel channel = super.findById(businessId, CmsChannel.class);
			if (StringUtils.isNotBlank(channel.getPid())) {
				findChannelParentProperty(channel.getPid(), listp, listcode);
			}
		} else if (Constants.TPLDIR_CONTENT.equalsIgnoreCase(modelType)) {
			// 递归查詢文章上面的欄目，一直到站點
			// 查询文章对应的栏目 以电脑版为主
			CmsChannelContent qb = new CmsChannelContent();
			qb.setContentId(businessId);
			qb.setState(1);
			qb.setOstype("pc");
			List<CmsChannelContent> listmid = channelContentService
					.findListDataByFinder(null, null, CmsChannelContent.class,
							qb);
			if (CollectionUtils.isNotEmpty(listmid)) {
				String channelId = listmid.get(0).getChannelId();
				if (StringUtils.isNotBlank(channelId)) {
					findChannelParentProperty(channelId, listp, listcode);
				}
			}

		}
		return listp;
	}

	/**
	 * 递归查询栏目的属性 一直到站点
	 * 
	 * @param channelId
	 *            栏目Id
	 * @param listp
	 *            初始化为空
	 * @param listcode
	 *            初始当前查询对象的 code
	 * @throws Exception
	 */
	void findChannelParentProperty(String channelId, List<CmsProperty> listp,
			List<String> listcode) throws Exception {

		/*
		 * if(channel==null){ return; }
		 */
		CmsChannel channel = null;
		if (StringUtils.isNotBlank(channelId)) {
			channel = super.findById(channelId, CmsChannel.class);
		}
		if (channel != null) {

			Finder f = Finder.getSelectFinder(CmsProperty.class);
			f.append(
					" where state=1 and isextend=1 and  businessId=:businessId ")
					.setParam("businessId", channel.getId());
			List<CmsProperty> list = super.queryForList(f, CmsProperty.class);
			if (CollectionUtils.isNotEmpty(list)) {
				for (CmsProperty p : list) {
					if (!listcode.contains(p.getCode())) {
						listp.add(p);
						listcode.add(p.getCode());
					}
				}
			}
			// 递归
			findChannelParentProperty(channel.getPid(), listp, listcode);
		} else {
			// 查询栏目下站点
			Finder finder = Finder.getSelectFinder(CmsProperty.class);
			finder.append(
					" where state=1 and isextend=1 and  businessId in (select siteId from "
							+ Finder.getTableName(CmsSiteChannel.class)
							+ " where channelId=:businessId  group by siteId ) ")
					.setParam("businessId", channelId);
			List<CmsProperty> listsite = super.queryForList(finder,
					CmsProperty.class);
			if (CollectionUtils.isNotEmpty(listsite)) {
				for (CmsProperty p : listsite) {
					if (!listcode.contains(p.getCode())) {
						listp.add(p);
						listcode.add(p.getCode());
					}
				}
			}
		}
	}

	@Override
	public List<CmsProperty> findByBusinessId(String businessId, String state)
			throws Exception {
		if (StringUtils.isBlank(businessId)) {
			return null;
		}
		// 查询本身字段
		Finder finder = Finder.getSelectFinder(CmsProperty.class);
		finder.append(" where businessId=:businessId ").setParam("businessId",
				businessId);
		if (StringUtils.isNotBlank(state)) {
			finder.append(" and state=:state").setParam("state", state);
		}
		finder.append(" order by sort desc ");
		List<CmsProperty> list = super.queryForList(finder, CmsProperty.class);
		return list;
	}

	@Override
	public List<CmsProperty> findByBusinessId(String businessId, String state,
			String bId, String modelType) throws Exception {
		if (StringUtils.isBlank(businessId)) {
			return null;
		}
		// 查询本身字段
		Finder finder = Finder.getSelectFinder(CmsProperty.class);
		finder.append(" where businessId=:businessId ").setParam("businessId",
				businessId);
		if (StringUtils.isNotBlank(state)) {
			finder.append(" and state=:state").setParam("state", state);
		}
		if (StringUtils.isNotBlank(modelType)) {
			finder.append(" and modelType=:modelType").setParam("modelType",
					modelType);
		}
		finder.append(" order by sort desc ");
		List<CmsProperty> list = super.queryForList(finder, CmsProperty.class);
		if (CollectionUtils.isNotEmpty(list)) {
			for (CmsProperty p : list) {
				p.setBvalue(findCmsPropertyvalueByPropertyId(p.getId(), bId));
			}
		}
		return list;
	}

	public String findCmsPropertyvalueByPropertyId(String propertyId,
			String businessId) throws Exception {
		if (StringUtils.isBlank(propertyId) || StringUtils.isBlank(businessId)) {
			return null;
		}
		Finder finder = Finder.getSelectFinder(CmsPropertyvalue.class);
		finder.append(" where propertyId=:propertyId and businessId=:businessId and state=1");

		finder.setParam("propertyId", propertyId).setParam("businessId",
				businessId);
		List<CmsPropertyvalue> list = super.queryForList(finder,
				CmsPropertyvalue.class);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0).getPvalue();
		}
		return null;
	}

	@Override
	public List<CmsProperty> findProperty(String businessId, String contentId)
			throws Exception {
		Finder finder = Finder.getSelectFinder(CmsProperty.class)
				.append(" where businessId=:businessId ");
		finder.setParam("businessId", businessId);
		finder.append(" order by sort asc ");
		List<CmsProperty> cps = super.queryForList(finder, CmsProperty.class);
		if (cps != null) {
			for (CmsProperty cp : cps) {
				finder = Finder.getSelectFinder(CmsPropertyvalue.class);
				finder.append(" where propertyId=:propertyId and businessId=:businessId");
				finder.setParam("propertyId", cp.getId());
				finder.setParam("businessId", contentId);
				List<CmsPropertyvalue> cpvs = super.queryForList(finder,
						CmsPropertyvalue.class);
				if (cpvs != null && cpvs.size() > 0) {
					cp.setBvalue(cpvs.get(0).getPvalue());
				}
			}
		}
		return cps;
	}

}
