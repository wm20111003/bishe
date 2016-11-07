package com.centfor.cms.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.Constants;
import com.centfor.cms.entity.CmsTheme;
import com.centfor.cms.service.ICmsTableindexService;
import com.centfor.cms.service.IThemeService;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;

/**
 * 主题管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link 9iu.org}
 * @author 9iu.org<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see com.centfor.bbz.web.User
 */
@Controller
@RequestMapping(value = "/cms/theme")
public class ThemeController extends BaseController {
	@Resource
	private IThemeService themeService;

	private String listurl = "/cms/theme/themeList";

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
	public String list(HttpServletRequest request, Model model, CmsTheme qb)
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, qb);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		model.addAttribute("ostypeMap", Constants.getkv(Constants.ostype));
		model.addAttribute("modelTypeMap", Constants.getkv(Constants.modelType));
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
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model, CmsTheme qb)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		if(qb!=null){
			//默认查询有效
			if(qb.getState()==null){
				qb.setState(1);
			}
		}
		List<CmsTheme> datas = themeService.findListDataByFinder(null, page,
				CmsTheme.class, qb); 
		returnObject.setQueryBean(qb); 
		returnObject.setPage(page);
		returnObject.setData(datas);
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
			CmsTheme en = themeService.findById(id, CmsTheme.class);
			returnObject.setData(en);
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
	public @ResponseBody
	ReturnDatas saveorupdate(CmsTheme theme, HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id=request.getParameter("id");
			theme.setState(1); 
			if(StringUtils.isNotBlank(id)){ 
				themeService.update(theme);
			}else{
				theme.setId(null);  
				themeService.saveTheme(theme);  
			}
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}





	
}
