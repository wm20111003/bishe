package com.centfor.cms.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.centfor.cms.directive.abs.AbstractChannelDirective;
import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.ILinkService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目下的子栏目
 */
@Component("channelParentDirective")
public class ChannelParentDirective extends AbstractChannelDirective {
	@Resource
	IChannelService channelService;
	@Resource
	ILinkService linkService;

	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "cms_channel_parent";

	@SuppressWarnings("unchecked")
	public void execute(Environment env,
			@SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String ctx = DirectiveUtils.getString("ctx", params);
		String siteId = DirectiveUtils.getString("siteId", params);
		String ostype = DirectiveUtils.getString("ostype", params); 
		String channelId = DirectiveUtils.getString("channelId", params);
		List<CmsChannel> list  = null;
		try {
			
			list = channelService.findParentChannel(channelId, ostype,ctx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put("list_channel_parent", DEFAULT_WRAPPER.wrap(list));

		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);

		// 根据type 可以辨别类型 例如 手机 app 引入不同的css js 等
		// InvokeType type = DirectiveUtils.getInvokeType(params);

		body.render(env.getOut());

		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);

	}
}
