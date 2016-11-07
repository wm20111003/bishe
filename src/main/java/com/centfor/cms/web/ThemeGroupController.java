package com.centfor.cms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.Constants;
import com.centfor.cms.entity.CmsReThemeGroup;
import com.centfor.cms.entity.CmsTheme;
import com.centfor.cms.entity.CmsThemeGroup;
import com.centfor.cms.service.IThemeGroupService;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;

/**
 * 主题组管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link 9iu.org}
 * @author 9iu.org<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see com.centfor.bbz.web.User
 */
@Controller
@RequestMapping(value = "/cms/themeGroup")
public class ThemeGroupController extends BaseController {
	@Resource
	private IThemeGroupService themeGroupService;

	private String listurl = "/cms/themeGroup/themeGroupList";

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, CmsThemeGroup qb)
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, qb);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody ReturnDatas listjson(HttpServletRequest request,
			Model model, CmsThemeGroup qb) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		List<CmsThemeGroup> datas = themeGroupService.findListDataByFinder(
				null, page, CmsThemeGroup.class, qb);
		returnObject.setQueryBean(qb);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas saveorupdate(CmsThemeGroup en,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			en.setState(1); 
			List<CmsReThemeGroup> listre = new ArrayList<CmsReThemeGroup>();
			Set<String> set_ostype=Constants.getk(Constants.ostype);
			Set<String> set_modelType=Constants.getk(Constants.modelType);
			
			for(String ostype:set_ostype){
				for(String modelType:set_modelType){
					CmsReThemeGroup mid = new CmsReThemeGroup();
					String param=ostype+modelType;
					String str = request.getParameter(param);
					if (StringUtils.isNotBlank(str)) {
						mid.setThemeId(str);
                        mid.setOstype(ostype);
						listre.add(mid);
					}
				}
			}
			
			themeGroupService.saveOrUpdateThemeGroup(en, listre);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			CmsThemeGroup en=themeGroupService.findById(id, CmsThemeGroup.class);
			CmsReThemeGroup qb=new CmsReThemeGroup();
			qb.setThemeGroupId(en.getId());
			List<CmsReThemeGroup> list=themeGroupService.findReTheme(qb, null);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("themeGroupId", en.getId());
			map.put("themeGroupName", en.getName());
			List<String> listThemeId=new ArrayList<String>();
			if(list!=null&&list.size()>0){
				for(CmsReThemeGroup re:list){
					listThemeId.add(re.getThemeId());
				}
			}
			map.put("listThemeId", listThemeId); 
			returnObject.setData(map);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;

	}
	
	@RequestMapping("/ajax/findGroupTheme")
	public @ResponseBody ReturnDatas findGroupTheme(HttpServletRequest request,
			Model model, CmsThemeGroup qb) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String groupId=request.getParameter("groupId");
		List<CmsTheme> list = themeGroupService.findListThemeByGroup(groupId, null);
		List<String> siteThemes=new ArrayList<String>();
		List<String> channelThemes=new ArrayList<String>();
		List<String> contentThemes=new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(list)){
			for(CmsTheme th:list){
				if("site".equals(th.getModelType())){
					siteThemes.add(th.getId());
				}else if("channel".equals(th.getModelType())){
					channelThemes.add(th.getId());
				}else if("content".equals(th.getModelType())){
					contentThemes.add(th.getId());
				}
			}
		}
		Map<String,List<String>> map=new HashMap<String, List<String>>();
		map.put("site", siteThemes);
		map.put("channel", channelThemes);
		map.put("content", contentThemes);                                                                                   
		returnObject.setData(map);
		return returnObject;
	}


}
