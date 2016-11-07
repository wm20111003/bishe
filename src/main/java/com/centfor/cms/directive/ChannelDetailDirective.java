package com.centfor.cms.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.centfor.cms.directive.abs.AbstractChannelDirective;
import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.service.IChannelService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目列表标签
 */
@Component("channelDetailDirective")
public class ChannelDetailDirective extends AbstractChannelDirective {
	@Resource
	IChannelService channelService;

	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "cms_channel_detail";

	@SuppressWarnings("unchecked")
	public void execute(Environment env,
			@SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String id = DirectiveUtils.getString("channelId", params);
		CmsChannel qb = new CmsChannel();
		try {
			qb = channelService.findById(id, CmsChannel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put("channel_detail", DEFAULT_WRAPPER.wrap(qb));

		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);

		// 根据type 可以辨别类型 例如 手机 app 引入不同的css js 等
		// InvokeType type = DirectiveUtils.getInvokeType(params);

		body.render(env.getOut());

		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);

	}
}
