package com.centfor.cms.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.entity.CmsSiteChannel;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.ISiteChannelService;
import com.centfor.frame.controller.BaseController;
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
@RequestMapping(value = "/cms/channel")
public class ChannelController extends BaseController {
	@Resource
	private IChannelService channelService;
	@Resource
	private ISiteChannelService siteChannelService;
	@Resource
	private ILinkService linkService;

	private String listurl = "/cms/channel/channelList";

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
			CmsChannel channel) throws Exception {
		ReturnDatas returnObject = listjson(request, model, channel);
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
			Model model, CmsChannel channel) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		// Page page = newPage(request);
		Page page = null;
		// ==执行分页查询
		List<CmsChannel> datas = channelService.findChannel(channel.getSiteId());
		List<CmsChannel> rets =new ArrayList<CmsChannel>();
		if(datas!=null){
			for(CmsChannel c:datas){
				c= channelService.findById(c.getId());
				rets.add(c);
			}
		}
		returnObject.setQueryBean(channel);
		returnObject.setPage(page);
		returnObject.setData(rets);
		return returnObject;
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
	@RequestMapping("/tree")
	public String tree(HttpServletRequest request, Model model,
			CmsChannel channel) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/cms/channel/tree";
	}

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tree/pre")
	public String tree2(HttpServletRequest request, Model model,
			CmsChannel channel) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/cms/channel/tree2";
	}

	/**
	 * 导出Excle格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param channel
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,
			HttpServletResponse response, Model model, CmsChannel channel)
			throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = channelService.findDataExportExcel(null, listurl, page,
				CmsChannel.class, channel);
		String fileName = "channel" + GlobalStatic.excelext;
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
		return "/cms/channel/channelLook";
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
			CmsChannel channel = channelService.findById(id);

			returnObject.setData(channel);
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
			CmsChannel channel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		try {
			String id = channel.getId();
			String pid = channel.getPid();
			if (StringUtils.isBlank(id)) {
				channel.setId(null);
			}
			if (StringUtils.isBlank(pid)) {
				channel.setPid(null);
			}

			String siteId = request.getParameter("siteId");
			CmsSite site = channelService.findById(siteId, CmsSite.class);
			List<CmsSiteChannel> listsc = new ArrayList<CmsSiteChannel>();
			String[] ostypes = { "pc", "weixin", "mobile" };
			for (String os : ostypes) {
				CmsSiteChannel sc = new CmsSiteChannel();
				String position = request.getParameter(os + "position");
				if (StringUtils.isBlank(position)) {
					continue;
				}
				sc.setSiteId(siteId);
				sc.setChanneltype(Integer.parseInt(request.getParameter(os
						+ "channeltype")));
				String sort = request.getParameter(os + "sort");
				if (StringUtils.isNotBlank(sort)) {
					sc.setSort(Integer.parseInt(sort));
				} else {
					sc.setSort(1);
				}
				sc.setOstype(os);
				sc.setPosition(position);
				sc.setState(1);
				listsc.add(sc);
			}

			// 站点url
			List<CmsLink> listlink = new ArrayList<CmsLink>();
			for (String os : ostypes) {
				String ftlId = request.getParameter(os);
				String nodeId = request.getParameter(os + "2");
				String linkurl = request.getParameter(os + "link");
				String opentype = request.getParameter(os + "opentype");
//				if (StringUtils.isBlank(ftlId)) {
//					if(StringUtils.isBlank(linkurl)){
//					    continue;  
//					}
//				} 
				CmsLink link = new CmsLink();
				link.setLink(linkurl);
				link.setOpentype(opentype);
				link.setOstype(os);
				link.setModelType("channel");
				link.setFtlId(ftlId);
				link.setNodeftlId(nodeId);
				listlink.add(link);
			}
			String url = request.getParameter("filepath");
			if(url!=null){
			CmsPicture p = new CmsPicture();
			p.setModelType("content");
			p.setSiteId(siteId);
			p.setFilepath(url);
			p.setIsmain(0);
			p.setSort(1);
			p.setCreateDate(new Date());
			p.setState(1);
			channel.setPic(p);
			}
			// 栏目下文章主题
			channelService.saveOrUpdateSiteChannel(site, channel, listsc,
					listlink);

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
	 * 保存或修改站点栏目
	 * 
	 */
	@RequestMapping("/updateChannelSite")
	public @ResponseBody ReturnDatas saveChannelSite(Model model,
			CmsSiteChannel sc, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		try {

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/cms/channel/channelCru";
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
				channelService.deleteById(id, CmsChannel.class);
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
			channelService.deleteByIds(ids, CmsChannel.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}

	@RequestMapping("/ajax/findChannel")
	public @ResponseBody ReturnDatas findChannel(HttpServletRequest request,
			Model model) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		// Page page = newPage(request);
		Page page = null;
		String siteId = request.getParameter("siteId");
		// ==执行分页查询
		List<CmsChannel> datas = channelService.findChannel(siteId);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 查询站点栏目
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajax/findSiteChannel")
	public @ResponseBody ReturnDatas findSiteChannel(
			HttpServletRequest request, Model model, CmsSite site)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String siteId = request.getParameter("siteId");
		String channelId = request.getParameter("channelId");
		CmsSiteChannel qb = new CmsSiteChannel();
		qb.setSiteId(siteId);
		qb.setChannelId(channelId);
		List<CmsSiteChannel> datas = siteChannelService.findListDataByFinder(
				null, null, CmsSiteChannel.class, qb);
		if (CollectionUtils.isNotEmpty(datas)) {
			for (CmsSiteChannel sc : datas) {
				CmsLink linkqb = new CmsLink();
				linkqb.setBusinessId(sc.getChannelId());
				linkqb.setOstype(sc.getOstype());
				List<CmsLink> links = linkService.findListDataByFinder(null,
						page, CmsLink.class, linkqb);
				if (CollectionUtils.isNotEmpty(links)) {
					//外部链接设置link
					if ("y".equalsIgnoreCase(links.get(0).getIsoutlink())) {
						sc.setLinkurl(links.get(0).getLink());
					} 
				}
			}
		}
		returnObject.setQueryBean(site);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

}
