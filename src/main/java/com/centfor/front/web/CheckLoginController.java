package com.centfor.front.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.frame.shiro.MemberUser;
import com.centfor.frame.util.MessageUtils;
import com.centfor.school.entity.SpMember;
import com.centfor.school.service.ISpMemberService;


@Controller
@RequestMapping(value="/front")
public class CheckLoginController {
	@Resource
	private ISpMemberService spMemberService;
	/**
	 * ouyang
	 * 判断用户是否已登录，并返回json数据
	 * 
	 * */
	@RequestMapping(value="/loginCheck")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public @ResponseBody Map checkLogin(HttpServletRequest request,
			HttpServletResponse response, Model model){
		Map map=new HashMap();
		//判断用户是否已登录
		if(MemberUser.getMember()==null||StringUtils.isBlank(MemberUser.getUserId())||StringUtils.isBlank(MemberUser.getMember().getSiteId())){
			map.put("status", "error");
			map.put("message", MessageUtils.PLEASE_LOGIN);
		}else{
			map.put("status", "success");
			map.put("message", MessageUtils.LOGINED);
			SpMember member=null;
			try {
				member = spMemberService.findSpMemberById(MemberUser.getUserId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("member", member);
			
		}
		
		return map;
	}
}
