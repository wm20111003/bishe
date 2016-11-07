package com.centfor.cms.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.centfor.cms.directive.abs.AbstractChannelDirective;
import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsContent;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.IContentService;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.IPictureService;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目明细页标签
 */
@Component("contentListDirective")
public class ContentListDirective extends AbstractChannelDirective {
	@Resource
	IContentService contentService;
	@Resource
	IChannelService channelService;
	@Resource
	IPictureService pictureService;
	@Resource
	ILinkService linkService;

	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "cms_content_list";

	@SuppressWarnings("unchecked")
	public void execute(Environment env,
			@SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String ctx = DirectiveUtils.getString("ctx", params);
		String siteId = DirectiveUtils.getString("siteId", params);
		String channelId = DirectiveUtils.getString("channelId", params);
		String ostype = DirectiveUtils.getString("ostype", params);
		String channelName = DirectiveUtils.getString("channelName", params);
		Integer pageIndex = DirectiveUtils.getInt("pageIndex", params);
		Integer pageSize = DirectiveUtils.getInt("pageSize", params);

		List<CmsContent> list = null;
		try {
			// 先按id 再按名字
			CmsChannel qchannel = new CmsChannel();
			qchannel.setName(channelName);
			List<CmsChannel> listChannel = channelService.findListDataByFinder(
					null, null, CmsChannel.class, qchannel);
			if (StringUtils.isNotBlank(channelName)) {
				if (StringUtils.isBlank(channelId)
						&& CollectionUtils.isNotEmpty(listChannel)) {
					channelId = listChannel.get(0).getId();
				}
			}
			// 控制分页
			Page page = new Page(1);
			if (pageIndex != null && pageIndex != 0) {
				page.setPageIndex(pageIndex);
			}
			if (pageSize != null && pageSize != 0) {
				page.setPageSize(pageSize);
			}
			// 默认排序
			Finder finder = new Finder();
			page.setOrder("sort");
			page.setSort("desc");

			CmsContent qb = new CmsContent();
			qb.setSiteId(siteId);
			qb.setChannelId(channelId);
			qb.setOstype(ostype);
			qb.setKeywords(channelName);
		/*	if (CollectionUtils.isNotEmpty(listChannel)) {
				qb.setChannelId(listChannel.get(0).getId());
			}*/
			list = contentService.findListDataByFinder(finder, page,
					CmsContent.class, qb);

			if (CollectionUtils.isNotEmpty(list)) {
				for (CmsContent c : list) {
					// 查询栏目url
					CmsLink qlink = new CmsLink();
					qlink.setBusinessId(c.getId());
					qlink.setOstype(ostype);
					List<CmsLink> ls = linkService.findListDataByFinder(null,
							null, CmsLink.class, qlink);
					if (CollectionUtils.isNotEmpty(ls)) {
						CmsLink mlink = ls.get(0);
						if ("n".equalsIgnoreCase(mlink.getIsoutlink())) {
							c.setLinkUrl(ctx + mlink.getLink());
						} else {
							if (mlink.getLink().startsWith("/front/")) {
								c.setLinkUrl(ctx + mlink.getLink());
							} else {
								c.setLinkUrl(mlink.getLink());
							}
						}
						c.setOpentype(mlink.getOpentype());
					}
					// 查询图片
					CmsPicture picture = new CmsPicture();
					picture.setBusinessId(c.getId());
					picture.setModelType("content");
					picture = pictureService.queryForObject(picture);
					if (picture != null) {
						c.setPicture(picture.getFilepath());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put("content_list", DEFAULT_WRAPPER.wrap(list));

		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);

		// 根据type 可以辨别类型 例如 手机 app 引入不同的css js 等
		// InvokeType type = DirectiveUtils.getInvokeType(params);

		body.render(env.getOut());

		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);

	}
}
