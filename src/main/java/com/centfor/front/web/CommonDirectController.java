package com.centfor.front.web;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.ISiteService;
import com.centfor.frame.shiro.MemberUser;
import com.centfor.system.entity.User;

@Controller
@RequestMapping(value = "/front")
public class CommonDirectController extends FrontBaseController {
	@Resource
	ILinkService linkService;
	@Resource
	IChannelService channelService;
	@Resource
	private ISiteService siteService;
	private static String serverUrl = null;;

	/**
	 * 根据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param mark
	 *            栏目标签
	 * @param ostype
	 *            平台标示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/commonDirect")
	public String commonDirect(HttpServletRequest request,
			HttpServletResponse response, Model model, 
			String ostype, String siteId) throws Exception {
		String mark=request.getParameter("mark");
		if (StringUtils.isBlank(siteId)) {
			siteId = (String) request.getSession().getAttribute("siteId");
		}
		if (StringUtils.isBlank(ostype)) {
			ostype = (String) request.getSession().getAttribute("ostype");
		}
		// 根据标签获取的链接
		CmsLink link = channelService.findLinkByChannelSort(siteId, mark,
				ostype);

		if (link == null || StringUtils.isBlank(link.getLink())) {
			return "/userpage/404";
		}
		List<String> listLoginReq = linkService.findLoginReqBusiness();
		if (listLoginReq.contains(link.getBusinessId())) {
			if (MemberUser.getMember() == null) {
				// 没有登录跳转到登陆
				CmsLink link_login = channelService.findLinkByChannelSort(
						link.getSiteId(), "login", ostype);
				return "redirect:" + link_login.getLink();
			}
		}
		String ftl = link.getFtlfile();
		if ("y".equals(link.getIsoutlink())) {
			ftl = link.getLink();
		}
		if (ftl == null || StringUtils.isBlank(ftl)) {
			return "/userpage/404";
		}
		CmsSite site = siteService.findSiteById(link.getSiteId());
		User user = linkService.findById(site.getUserId(), User.class);
		model.addAttribute("link", link);
		model.addAttribute("siteId", link.getSiteId());
		model.addAttribute("themeId", link.getFtlId());
		model.addAttribute("site", site);
		model.addAttribute("userAccount", user.getAccount());
		model.addAttribute("ostype", ostype);
		model.addAttribute("user", user);
		model.addAttribute("businessId", link.getBusinessId());
		model.addAttribute("parentId", request.getParameter("parentId"));
		request.getSession().setAttribute("realPath", serverUrl);
		return ftl;

	}
}
