package com.centfor.front.web;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.service.ISiteService;

/**
 * APP首页Controller,前台
 * 
 * @author lyf
 * @version 2015-03-02
 */
@Controller
@RequestMapping("/front/index")
public class IndexController extends FrontBaseController {
	@Resource
	private ISiteService siteService;
	
	/**
	 * 返回商场首页json数据，需传入省份
	 * @param model
	 * @param request
	 * @param response
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/json")
	public @ResponseBody
	Map<String, Object> json(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String province=request.getParameter("province");
		String ctx = request.getParameter("ctx");
		Map<String, Object> map = new HashMap<String, Object>();
		map = siteService.findSiteByPrivince(province,ctx);
		return map;
	}
}
