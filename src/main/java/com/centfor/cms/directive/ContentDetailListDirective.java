package com.centfor.cms.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.centfor.cms.directive.abs.AbstractChannelDirective;
import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.cms.entity.CmsContent;
import com.centfor.cms.service.IContentService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 文章明细标签
 */
@Component("contentDetailDirective")
public class ContentDetailListDirective extends AbstractChannelDirective {
	@Resource
	IContentService contentService;

	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "cms_content_detail";

	@SuppressWarnings("unchecked")
	public void execute(Environment env,
			@SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String contentId = DirectiveUtils.getString("id", params);
		String contentName = DirectiveUtils.getString("name", params);
		
		CmsContent qb = new CmsContent();
		qb.setId(contentId);

		CmsContent content = null;
		try {
			content = contentService.findById(contentId,
					CmsContent.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put("content", DEFAULT_WRAPPER.wrap(content));

		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);

		// 根据type 可以辨别类型 例如 手机 app 引入不同的css js 等
		// InvokeType type = DirectiveUtils.getInvokeType(params);

		body.render(env.getOut());

		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);

	}
}
