package com.centfor.cms.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.Constants;
import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsChannelContent;
import com.centfor.cms.entity.CmsContent;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.service.IChannelContentService;
import com.centfor.cms.service.IContentService;
import com.centfor.cms.service.IPictureService;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version 2013-07-29 11:36:46
 * @see com.centfor.bbz.web.Channel
 */

@Controller
@RequestMapping(value = "/cms/content")
public class ContentController extends BaseController {
	@Resource
	private IContentService contentService;
	@Resource
	private IChannelContentService channelContentService;
	@Resource
	private IPictureService pictureService;

	private String cruurl = "/cms/content/contentCru";
	private String listurl = "/cms/content/contentList";

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
	}

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,
			CmsContent content) throws Exception {
		ReturnDatas returnObject = listjson(request, model, content);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody ReturnDatas listjson(HttpServletRequest request,
			Model model, CmsContent content) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		// Page page = newPage(request);
		Page page = null;
		// ==执行分页查询
		List<CmsContent> datas = contentService.findListDataByFinder(null,
				page, CmsContent.class, content);
		returnObject.setQueryBean(content);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */

	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/cms/content/contentLook";
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
			CmsContent content = contentService.findById(id, CmsContent.class);
			if (content != null) {
				CmsChannelContent qb = new CmsChannelContent();
				qb.setContentId(content.getId());
				List<CmsChannelContent> list = channelContentService
						.findListDataByFinder(null, null,
								CmsChannelContent.class, qb);
				if (CollectionUtils.isNotEmpty(list)) {
					content.setSiteId(list.get(0).getSiteId());
				}
				Finder finder = new Finder(
						"select * from "
								+ Finder.getTableName(CmsPicture.class)
								+ " where businessId=:businessId  and modelType='content' ");
				finder.setParam("businessId", id);
				CmsPicture picture = pictureService.queryForObject(finder,
						CmsPicture.class);
				if (picture != null) {
					String filepath = picture.getFilepath();
					content.setPicture(filepath);
				} 
			}
			returnObject.setData(content);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

	/**
	 * 修改保存栏目
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas saveorupdate(Model model,
			CmsContent content, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		try {
			String contentId = content.getId();
			if (StringUtils.isNotBlank(contentId)) {
				CmsContent content_db = contentService.findById(contentId,
						CmsContent.class);
				content.setCreateDate(content_db.getCreateDate());
				content.setState(content_db.getState());
				content.setCreatePerson(content_db.getCreatePerson());
				content.setSort(content_db.getSort());
			} else {
				content.setSort(1);
				content.setState(1);
			}
			String siteId = request.getParameter("siteId");
			CmsSite site = contentService.findById(siteId, CmsSite.class);

			// 中间表
			List<CmsChannelContent> listmid = new ArrayList<CmsChannelContent>();
			// 站点url
			List<CmsLink> listlink = new ArrayList<CmsLink>();
			Set<String> ostypes = Constants.getk(Constants.ostype);
			for (String os : ostypes) {
				// channelContent
				String channelId = request.getParameter(os + "channelId");
				if (StringUtils.isBlank(channelId)) {
					continue;
				}
				String state = request.getParameter(os + "state");
				CmsChannelContent re = new CmsChannelContent();
				re.setChannelId(channelId);
				re.setContentId(contentId);
				re.setSiteId(siteId);
				re.setOstype(os);
				re.setSort(1);
				if (StringUtils.isNotBlank(state)) {
					re.setState(1);
				} else {
					re.setState(0);
				}
				listmid.add(re);
				// link
				String ftlId = request.getParameter(os);
				CmsLink link = new CmsLink();
				link.setOstype(os);
				link.setModelType("content");
				if (StringUtils.isNotBlank(ftlId)) {
					link.setFtlId(ftlId);
					listlink.add(link);
				}
			}
			// 保存图片
			CmsPicture picture = new CmsPicture();
			String filepath = request.getParameter("filepath");
			picture.setModelType("content");
			picture.setSiteId(siteId);
			picture.setFilepath(filepath);
			picture.setSort(1);
			picture.setCreateDate(new Date());
			picture.setState(1);
			// 栏目下文章主题
			contentService.saveOrUpdateChannelContent(site, content, listmid,
					listlink, picture);

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 编辑站点栏目
	 */
	@RequestMapping(value = "/listChannelSite")
	public String updateChannelSite(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/cms/channel/channelSite";
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return cruurl;
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ReturnDatas destroy(HttpServletRequest request)
			throws Exception {
		// 执行删除
		try {
			String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				CmsContent content = contentService.findById(id,
						CmsContent.class);
				if (content != null) {
					CmsChannelContent qb = new CmsChannelContent();
					qb.setContentId(content.getId());
					List<CmsChannelContent> list = channelContentService
							.findListDataByFinder(null, null,
									CmsChannelContent.class, qb);
					if (CollectionUtils.isNotEmpty(list)) {
						qb = list.get(0);
						qb.setState(0);
						content.setState(0);
						contentService.deleteChannelContent(content, qb);
						return new ReturnDatas(ReturnDatas.SUCCESS,
								MessageUtils.DELETE_SUCCESS);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.WARNING,
					MessageUtils.DELETE_WARNING);
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
			contentService.deleteByIds(ids, CmsChannel.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajax/listChannelContent")
	public @ResponseBody ReturnDatas listChannelContent(
			HttpServletRequest request, Model model, CmsChannelContent qb)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		// Page page = newPage(request);
		Page page = null;
		// ==执行分页查询
		List<CmsChannelContent> datas = channelContentService
				.findListDataByFinder(null, page, CmsChannelContent.class, qb);
		returnObject.setQueryBean(qb);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

}
