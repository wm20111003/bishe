package com.centfor.cms.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsProperty;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.IPropertyService;
import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.ReturnDatas;

/**
 * 自定义属性管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link 9iu.org}
 * @author 9iu.org<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see com.centfor.bbz.web.User
 */
@Controller
@RequestMapping(value = "/cms/property")
public class PropertyController extends BaseController {
	@Resource
	private IPropertyService propertyService;
	@Resource
	private ILinkService linkService;

	/**
	 * 新增或修改
	 * 
	 * @param en
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajax/update")
	public @ResponseBody ReturnDatas saveorupdate(CmsProperty en,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id = request.getParameter("propertyId");
			en.setId(id);
			String businessId = request.getParameter("businessId");
			String modelType = request.getParameter("modelType");
			if (StringUtils.isNotBlank(id)) {
				CmsProperty db = propertyService
						.findById(id, CmsProperty.class);
				en.setCreateDate(db.getCreateDate());
				en.setCreatePerson(db.getCreatePerson());
				en.setState(db.getState()); 
				en.setBusinessId(db.getBusinessId());
				en.setSiteId(db.getSiteId());
				en.setModelType(db.getModelType());
			} else {
				if (StringUtils.isNotBlank(businessId)) {
					// 查询site
					CmsLink qb = new CmsLink();
					qb.setBusinessId(businessId);
					List<CmsLink> list = linkService.findListDataByFinder(null,
							null, CmsLink.class, qb);
					if (CollectionUtils.isNotEmpty(list)) {
						en.setSiteId(list.get(0).getSiteId());
					}
					en.setCreateDate(new Date());
					en.setCreatePerson(SessionUser.getUserName());
					en.setModelType(modelType);
				}
			}
			if (StringUtils.isNotBlank(en.getBusinessId())) {
				propertyService.saveupdate(en);
			}

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		returnObject.setData(en);
		return returnObject;
	}

	@RequestMapping(value = "/ajax/findByBusiness")
	public @ResponseBody ReturnDatas findByBusiness(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String businessId = request.getParameter("businessId");
//		String modelType = request.getParameter("modelType");
		String state = request.getParameter("state");
		if (StringUtils.isBlank(state)) {
			state = "1";
		}

		if (StringUtils.isNotBlank(businessId)) {
			List<CmsProperty> list = propertyService.findByBusinessId(
					businessId, state);
			returnObject.setData(list);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}
	
	@RequestMapping(value = "/ajax/extendParent")
	public @ResponseBody ReturnDatas extendParent(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("propertyId");
		String businessId = request.getParameter("businessId");
		String pvalue = request.getParameter("pvalue");
		if (StringUtils.isNotBlank(id)) {
			CmsProperty en=propertyService.findById(id, CmsProperty.class);
			en.setId(null);
			en.setBusinessId(businessId);
			en.setPvalue(pvalue);     
			propertyService.save(en);
			returnObject.setData(en);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}
	
	@RequestMapping(value = "/ajax/findParentProperty")
	public @ResponseBody ReturnDatas findParentProperty(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String businessId = request.getParameter("businessId");
		String modelType = request.getParameter("modelType");

		if (StringUtils.isNotBlank(businessId)) {
			List<CmsProperty> list = propertyService.findParentProperty(businessId, modelType);
			returnObject.setData(list);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}

	@RequestMapping(value = "/look/json")
	public @ResponseBody ReturnDatas lookjson(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			CmsProperty en = propertyService.findById(id, CmsProperty.class);
			returnObject.setData(en);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}

	/**
	 * 删除 操作,返回json格式数据
	 * 
	 */
	@RequestMapping("/delete")
	public @ResponseBody ReturnDatas delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		try {
			String id = request.getParameter("propertyId");
			if (StringUtils.isNotBlank(id)) {
				CmsProperty en = propertyService
						.findById(id, CmsProperty.class);
				en.setState(0);
				propertyService.update(en);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.DELETE_WARNING);
		}

		return returnObject;
	}

}
