package com.centfor.cms.web;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.ISiteService;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.shiro.MemberUser;
import com.centfor.system.entity.User;

/**
 * 加载页面
 * 
 * @copyright {@link 9iu.org}
 * @author 9iu.org<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see com.centfor.bbz.web.site
 */
@Controller
@RequestMapping(value = "/front")
public class LoadController extends BaseController {
	@Resource
	ILinkService linkService;
	@Resource
	IChannelService channelService;
	@Resource
	private ISiteService siteService;
	@RequestMapping("/pc/{bussinessId}")
	public String pc(HttpServletRequest request,
			@PathVariable("bussinessId") String bussinessId, Model model)
			throws Exception {
		String ostype = "pc";
		return oscommon(request, ostype, bussinessId, model);
	}

	private String oscommon(HttpServletRequest request, String ostype,
			String bussinessId, Model model) throws Exception {


		CmsLink link = findFtl(bussinessId, ostype);
		if (link == null || StringUtils.isBlank(link.getLink())) {
			return "/userpage/404";
		}

		List<String> listLoginReq = linkService.findLoginReqBusiness();
		request.getSession().setAttribute("siteId",link.getSiteId());
		request.getSession().setAttribute("ostype",ostype);
		if (listLoginReq.contains(bussinessId)) {
			if (MemberUser.getUserId()== null) {
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
		model.addAttribute("businessId", bussinessId);
		model.addAttribute("parentId", request.getParameter("parentId"));
		model.addAttribute("memberUserId",MemberUser.getUserId());
		return ftl;
	}

	private CmsLink findFtl(String bussinessId, String ostype) throws Exception {
		CmsLink queryBean = new CmsLink();
		queryBean.setBusinessId(bussinessId);
		queryBean.setOstype(ostype);
		List<CmsLink> list = linkService.findListDataByFinder(null, null,
				CmsLink.class, queryBean);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
