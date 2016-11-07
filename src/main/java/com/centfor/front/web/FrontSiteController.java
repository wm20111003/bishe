package com.centfor.front.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.service.ISiteService;
import com.centfor.frame.util.ReturnDatas;
@Controller
@RequestMapping(value = "/front/site")
public class FrontSiteController {
	@Resource
	private ISiteService siteService;
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody ReturnDatas lookjson(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String province=request.getParameter("province");
		if (StringUtils.isNotBlank(province)) {
			CmsSite site =new CmsSite();
			site.setProvince(province);
			//根据省份取得店铺
			List<CmsSite> list=siteService.queryForListByEntity(site, null);
			if(list==null||list.size()==0){
				site.setProvincePinyin(province);
				list=siteService.queryForListByEntity(site, null);
				if(list==null||list.size()==0){
					returnObject.setData(null);
				}else{
					//选取第一家店铺
					returnObject.setData(list.get(0));
					String siteId=list.get(0).getId();
					HttpSession session =request.getSession();
					session.setAttribute("siteId", siteId);
				}
			}else{
				
				//选取第一家店铺
				returnObject.setData(list.get(0));
				String siteId=list.get(0).getId();
				HttpSession session =request.getSession();
				session.setAttribute("siteId", siteId);
			}
		} else {
			returnObject.setData(null);
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

}
