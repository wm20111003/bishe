package com.centfor.front.web;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centfor.cms.entity.CmsContent;
import com.centfor.cms.service.IContentService;
import com.centfor.cms.service.ISiteService;

@Controller
@RequestMapping(value = "/front/content")
public class FrontContentController extends FrontBaseController {


	@Resource
	private ISiteService siteService;
	@Resource
	private IContentService contentService;

	/**
	 * 新闻详情@bishe
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/contentInfo")
	public String contentInfo(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String cid=request.getParameter("cid");
		try {
			if(StringUtils.isNotBlank(cid)){
				CmsContent content=new CmsContent();
				content=contentService.findById(cid, CmsContent.class);
				model.addAttribute("content", content);
			}
			CmsContent con=new CmsContent();
			con.setSiteId("a16");
		List<CmsContent> conList=contentService.findListDataByFinder(null, null, CmsContent.class, con);
		model.addAttribute("conList", conList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "userpage/newsDetail";
	}
	
}
