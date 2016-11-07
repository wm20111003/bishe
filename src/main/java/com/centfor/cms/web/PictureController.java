package com.centfor.cms.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsPicture;
import com.centfor.cms.service.ILinkService;
import com.centfor.cms.service.IPictureService;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;

/**
 * 用户管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link 9iu.org}
 * @author 9iu.org<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see com.centfor.bbz.web.picture
 */
@Controller
@RequestMapping(value = "/cms/picture")
public class PictureController extends BaseController {
	@Resource
	private IPictureService pictureService;
	@Resource
	private ILinkService linkService;

	private String listurl = "/cms/picture/pictureList";

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param picture
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,
			CmsPicture picture) throws Exception {
		ReturnDatas returnObject = listjson(request, model, picture);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param picture
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody ReturnDatas listjson(HttpServletRequest request,
			Model model, CmsPicture picture) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		List<CmsPicture> datas = pictureService.findListDataByFinder(null,
				page, CmsPicture.class, picture);
		returnObject.setQueryBean(picture);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param picture
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/pic")
	public @ResponseBody ReturnDatas listpic(HttpServletRequest request,
			Model model, CmsPicture picture) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		List<CmsPicture> datas = pictureService.findPictureBySiteId(null, page,
				CmsPicture.class, picture);
		returnObject.setQueryBean(picture);
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
	 * @param picture
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,
			HttpServletResponse response, Model model, CmsPicture picture)
			throws Exception {
		// ==构造分页请求
		Page page = newPage(request);
		File file = pictureService.findDataExportExcel(null, listurl, page,
				CmsPicture.class, picture);
		String fileName = "picture" + GlobalStatic.excelext;
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
		return "/system/picture/pictureLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody ReturnDatas lookjson(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			CmsPicture picture = pictureService.findPictureById(id);
			//获取链接表数据
			CmsLink cl=new CmsLink();
			cl.setBusinessId(id);
			List<CmsLink> list = linkService.findAllListDataByFinder(null, page, CmsLink.class, cl);
			picture.setPictureLinks(list);
			returnObject.setData(picture);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

	/**
	 * 新增/修改 操作,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas saveorupdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			//保存图片
			CmsPicture pic = new CmsPicture();
			String id=request.getParameter("id");
			String siteId = request.getParameter("siteId");
			String filepath=request.getParameter("filepath");
			pic.setModelType("ppt");
			pic.setSiteId(siteId);
			pic.setFilepath(filepath);
			pic.setSort(1);
			pic.setCreateDate(new Date());
			pic.setBusinessId(siteId);
			pic.setState(1);
			//关联的链接
			List<CmsLink> list = new ArrayList<CmsLink>();
			//生成link表里该图片下的链接
			for(int i=1;i<6;i++){
				String ostype=request.getParameter("ostype"+i);
				String link=request.getParameter("link"+i);
				if(StringUtils.isNotBlank(ostype)||StringUtils.isNotBlank(link)){
					CmsLink cl= new CmsLink();
					String opentype=request.getParameter("opentype"+i);
					String ost=request.getParameter("ost"+i);
					cl.setName(link+ostype);
					cl.setDefaultLink(link);
					cl.setLink(link);
					cl.setSiteId(siteId);
					cl.setBusinessId(" ");
					cl.setOstype(ost);
					cl.setModelType("ppt");
					cl.setSort(i);
					cl.setState(1);
					cl.setIsoutlink("n");
					cl.setOpentype(opentype);
					if(StringUtils.isBlank(ostype)){
						cl.setState(0);
					}
					list.add(cl);
				};
			}
			pic.setPictureLinks(list);
			if(StringUtils.isNotBlank(id)){
				pic.setId(id);
				pictureService.updatePicture(pic);
			}else{
				pictureService.savePicture(pic);
			}
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
	/*
	 * @RequestMapping(value = "/update/pre") public String edit(Model model,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { ReturnDatas returnObject = lookjson(model, request,
	 * response); model.addAttribute(GlobalStatic.returnDatas, returnObject);
	 * return "/system/picture/pictureCru"; }
	 */

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
				pictureService.deletePictureById(id);
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
			pictureService.deleteByIds(ids, CmsPicture.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}

}
