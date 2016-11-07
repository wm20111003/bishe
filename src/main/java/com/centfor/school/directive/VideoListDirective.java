package com.centfor.school.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.centfor.cms.directive.abs.AbstractChannelDirective;
import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.school.entity.WorkExperiment;
import com.centfor.school.entity.WorkVideo;
import com.centfor.school.service.IWorkVideoService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目明细页标签
 */
@Component("videoListDirective")
public class VideoListDirective extends AbstractChannelDirective {
	@Resource
	IWorkVideoService workVideoService;

	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "sp_video_list";

	@SuppressWarnings("unchecked")
	public void execute(Environment env,
			@SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		String siteId = DirectiveUtils.getString("siteId", params);
		Integer pageIndex = DirectiveUtils.getInt("pageIndex", params);
		Integer pageSize = DirectiveUtils.getInt("pageSize", params);

		List<WorkVideo> list = null;
		try {
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
			page.setOrder("createDate");
			page.setSort("desc");

			WorkVideo qb = new WorkVideo();
				qb.setSiteId(siteId);
			list = workVideoService.findListDataByFinder(null, page, WorkVideo.class, qb);
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put("video_list", DEFAULT_WRAPPER.wrap(list));

		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);

		// 根据type 可以辨别类型 例如 手机 app 引入不同的css js 等
		// InvokeType type = DirectiveUtils.getInvokeType(params);

		body.render(env.getOut());

		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);

	}
}
