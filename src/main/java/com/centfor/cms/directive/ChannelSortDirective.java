package com.centfor.cms.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.centfor.cms.directive.abs.AbstractChannelDirective;
import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.service.IChannelService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目标识
 */
@Component("channelSortDirective")
public class ChannelSortDirective extends AbstractChannelDirective {
	@Resource
	IChannelService channelService;

	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "cms_channel_sort";

	@SuppressWarnings("unchecked")
	public void execute(Environment env,
			@SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String siteId = DirectiveUtils.getString("siteId", params);
		String ostype = DirectiveUtils.getString("ostype", params);
		String channelSort = DirectiveUtils.getString("channelSort", params);
		CmsLink link=null;
		CmsChannel ch=null;
		try {
			link=channelService.findLinkByChannelSort(siteId, channelSort, ostype);
			if(link!=null&&link.getBusinessId()!=null){
				ch=channelService.findById(link.getBusinessId(), CmsChannel.class);
			}
//			if(link!=null){
//				if(StringUtils.isNotBlank(link.getLink())){
//					link.setLink(link.getLink().trim()); 
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put("channel_link", DEFAULT_WRAPPER.wrap(link));
		paramWrap.put("channel", DEFAULT_WRAPPER.wrap(ch)); 

		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);

		// 根据type 可以辨别类型 例如 手机 app 引入不同的css js 等
		// InvokeType type = DirectiveUtils.getInvokeType(params);

		body.render(env.getOut());

		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);

	}
}
