package com.centfor.front.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.frame.util.ReturnDatas;
import com.centfor.front.UpdataInfo;

@Controller
@RequestMapping(value="/front/version")
public class CheckVersionController extends FrontBaseController{
	/**
	 * ouyang
	 * 检查Android版本，并返回json数据
	 * 
	 * */
	@RequestMapping(value="/check/android")
	public @ResponseBody UpdataInfo checkVersion(HttpServletRequest request,
			HttpServletResponse response, Model model){
		UpdataInfo updataInfo =new UpdataInfo();
		updataInfo.versionCode=1;
		return updataInfo;
	}
}
