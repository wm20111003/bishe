package com.centfor.cms.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.centfor.cms.directive.abs.AbstractChannelDirective;
import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.cms.directive.util.DirectiveUtils.InvokeType;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.service.IPictureService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目列表标签
 */
@Component("pptListDirective")
public class PPTListDirective extends AbstractChannelDirective {
	@Resource
	private IPictureService pictureService;
	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "cms_ppt_list"; 

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String siteId = DirectiveUtils.getString("siteId", params);
		CmsPicture qb=new CmsPicture();
		qb.setSiteId(siteId);
		qb.setModelType("ppt"); 
		List<CmsPicture> list=null;
		
		try {
			list = pictureService.findPictureBySiteId(null, null, CmsPicture.class, qb);
		} catch (Exception e) {
		}
	 
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		
		paramWrap.put("ppt_list", DEFAULT_WRAPPER.wrap(list));
		
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		//根据type 可以辨别类型 例如 手机 app 引入不同的css js 等
		InvokeType type = DirectiveUtils.getInvokeType(params);
		
	    body.render(env.getOut());
		
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
		
	}
}
