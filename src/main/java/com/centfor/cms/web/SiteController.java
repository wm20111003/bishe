package com.centfor.cms.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.Constants;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.entity.CmsThemeGroup;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.ISiteService;
import com.centfor.cms.service.IThemeGroupService;
import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.system.entity.Role;
import com.centfor.system.entity.User;
import com.centfor.system.service.IUserRoleMenuService;
import com.centfor.system.service.IUserService;

/**
 * 用户管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link 9iu.org}
 * @author 9iu.org<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see com.centfor.bbz.web.site
 */
@Controller
@RequestMapping(value = "/cms/site")
public class SiteController extends BaseController {
	@Resource
	private ISiteService siteService;
	@Resource
	private ILinkService linkService;
	@Resource
	private IUserService userService;
	@Resource
	private IUserRoleMenuService userRoleMenuService;
	@Resource
	private IThemeGroupService themeGroupService;

	private String listurl = "/cms/site/siteList";

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, CmsSite site)
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, site);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		CmsThemeGroup qb = new CmsThemeGroup();
		qb.setState(1);
		List<CmsThemeGroup> listgroup = themeGroupService.findListDataByFinder(
				null, null, CmsThemeGroup.class, qb);
		model.addAttribute("listgroup", listgroup);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody ReturnDatas listjson(HttpServletRequest request,
			Model model, CmsSite site) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);

		// 得到当前登陆用户的id
		String cur_id = SessionUser.getUserId();

		List<Role> list = userRoleMenuService.findRoleByUserId(cur_id);
		boolean isManager = false;
		for (Role r : list) {
			if (r.getId().equals("admin")) {
				isManager = true;
			}
		}
		// 如果当前用户为admin(管理员)，可查看所有数据
		List<CmsSite> datas = new ArrayList<CmsSite>();
		if (isManager) {
			datas = siteService.findListDataByFinder(null, page, CmsSite.class,
					site);
		} else {
			String siteId = SessionUser.getShiroUser().getUser().getSiteId();
			if (StringUtils.isNotBlank(siteId)) {
				site.setId(siteId);
				datas = siteService.queryForListByEntity(site, page);
			}
		}
		returnObject.setQueryBean(site);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 导出Excle格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param site
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,
			HttpServletResponse response, Model model, CmsSite site)
			throws Exception {
		// ==构造分页请求
		Page page = newPage(request);
		File file = siteService.findDataExportExcel(null, listurl, page,
				CmsSite.class, site);
		String fileName = "site" + GlobalStatic.excelext;
		downFile(response, file, fileName, true);
		return;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/site/siteLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody ReturnDatas lookjson(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			CmsSite site = siteService.findSiteById(id);
			if (site != null && StringUtils.isNotBlank(site.getUserId())) {
				User user = siteService.findById(site.getUserId(), User.class);
				site.setUserName(user.getName());
			}
			// 查询站点各个平台主题
			List<String> siteThemeIds = linkService.findLinkThemeIds(site
					.getId());
			site.setSiteThemeIds(siteThemeIds);
			// 查询站点下栏目主题
			List<String> channelThemeIds = linkService
					.findNodeLinkThemeIds(site.getId());
			site.setChannelThemeIds(channelThemeIds);
			// 查询站点下的自定义字段
			returnObject.setData(site);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas saveorupdate(CmsSite site,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id = site.getId();
			if (StringUtils.isNotBlank(id)) {
				CmsSite db_site = siteService.findById(id, CmsSite.class);
				site.setState(db_site.getState());
			}
			Set<String> ostypes = Constants.getk(Constants.ostype);
			// List<String> siteThemeIds = new ArrayList<String>();
			Finder finder = new Finder(
					"select themeId from cms_re_theme_group where themeGroupId=:themeGroupId");
			finder.setParam("themeGroupId", site.getThemeGroupId());
			List<String> siteThemeIds = siteService.queryForList(finder,
					String.class);
			/*
			 * for (String os : ostypes) { String thid =
			 * request.getParameter(os); if (StringUtils.isNotBlank(thid)) {
			 * siteThemeIds.add(thid); } }
			 */
			List<String> channelThemeIds = new ArrayList<String>();
			for (String os : ostypes) {
				String thid = request.getParameter(os + "2");
				if (StringUtils.isNotBlank(thid)) {
					channelThemeIds.add(thid);
				}
			}

			// lyf 修改 去除原用户中的siteId
			String user_id = request.getParameter("user_id");
			if (StringUtils.isNotBlank(user_id)) {
				User user2 = userService.findUserById(user_id);
				user2.setSiteId("");
				userService.update(user2);
			}
			// 添加新用户中的siteId
			String userId = site.getUserId();
			if (StringUtils.isNotBlank(userId)) {
				User user = userService.findUserById(userId);
				user.setSiteId(id);
				userService.update(user);
			}else{
				if(!SessionUser.getUserId().equals("admin")){
					site.setUserId(SessionUser.getUserId());
				}else{
					site.setUserId(null);
				}
			}

			site.setSiteThemeIds(siteThemeIds);
			site.setChannelThemeIds(channelThemeIds);
			// 保存幻灯片
			String[] _url = request.getParameterValues("_url");
			List<CmsPicture> ps = new ArrayList<CmsPicture>();
			if (_url != null) {
				for (String _u : _url) {
					CmsPicture p = new CmsPicture();
					p.setModelType("content");
					p.setFilepath(_u);
					p.setIsmain(0);
					p.setSort(1);
					p.setCreateDate(new Date());
					p.setState(1);
					ps.add(p);
				}
			}
			site.setPics(ps);
			//将省份转成拼音
//			String[] propinyin=PinyinUtils.stringToPinyin(site.getProvince());
//			String ss="";
//			for(int i=0;i<(propinyin.length-1);i++){
//					ss+=propinyin[i];
//			}
//			site.setProvincePinyin(ss.trim());
			
			siteService.saveOrUpdateSite(site);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}

		return returnObject;
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ReturnDatas destroy(HttpServletRequest request)
			throws Exception {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				siteService.deleteById(id, CmsSite.class);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}

	/**
	 * 删除多条记录
	 * 
	 */
	@RequestMapping("/delete/more")
	public @ResponseBody ReturnDatas delMultiRecords(
			HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			siteService.deleteByIds(ids, CmsSite.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}

}
