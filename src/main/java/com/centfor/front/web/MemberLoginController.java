package com.centfor.front.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsSite;
import com.centfor.cms.service.IChannelService;
import com.centfor.cms.service.ISiteService;
import com.centfor.frame.shiro.MemberUser;
import com.centfor.frame.util.CaptchaUtils;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MD5Utils;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.school.entity.SpMember;
import com.centfor.school.service.ISpMemberService;
import com.centfor.system.entity.User;

@Controller
@RequestMapping("/front/member")
public class MemberLoginController extends FrontBaseController{
	@Resource
	ISpMemberService spMemberService;
	@Resource
	IChannelService channelService;
	@Resource
	private ISiteService siteService;
	
	public static Map<String, String> map = new HashMap<String, String>();
	/**
	 * 前台登录返回json
	 * @param response
	 * @param request
	 * @param spMember
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login" ,method=RequestMethod.POST)
	public  @ResponseBody ReturnDatas login(HttpServletResponse response,HttpServletRequest request,SpMember spMember) throws Exception{
		String siteId = request.getParameter("siteId");
		String ostype = request.getParameter("ostype");
		//清理session
		cleanUserSession(response, request);
		ReturnDatas returnDatas=ReturnDatas.getErrorReturnDatas();
		SpMember member=spMemberService.finderMemberByAccountAndState(spMember.getSiteId(),spMember.getAccount(), null);
		if(member==null){
			returnDatas.setMessage("用户不存在");
			return returnDatas;
		}
		if(!"是".equals(member.getState())){
			returnDatas.setMessage("账号不可用");
			return returnDatas;
		}
		String password=MD5Utils.encoderByMd5With32Bit(spMember.getPassword());
		if(!member.getPassword().equalsIgnoreCase(password)){
			returnDatas.setMessage("密码错误");
			return returnDatas;
		}
		//添加session信息
		addUserSession(response, request, spMember);
		String redirectUrl=request.getParameter("redirectUrl");
		if(StringUtils.isBlank(redirectUrl)){
			//根据标签获取的链接
			CmsLink link = channelService.findLinkByChannelSort(siteId, "shouye", ostype);
			if (link!=null) {
				redirectUrl=  link.getLink();
			}
			if (redirectUrl == null || StringUtils.isBlank(redirectUrl)) {
				redirectUrl= "/userpage/404";
			}
		}
		ThreadContext.getSubject().getSession().setAttribute("spMember", member);
		ThreadContext.getSubject().getSession().setAttribute("userId", member.getId());
		ThreadContext.getSubject().getSession().setAttribute("account", member.getAccount());
		ThreadContext.getSubject().getSession().setAttribute("gradeId", member.getGradeId());
		redirectUrl=redirectUrl.replace("@", "&");
		ReturnDatas returnObject=new ReturnDatas(ReturnDatas.SUCCESS, "登录成功",redirectUrl);
		returnObject.setQueryBean(request.getSession().getId());
		return returnObject;
	}
	/**
	 * 
	 */
	@RequestMapping("/tuichu")
	public String tuichu(HttpServletRequest request, Model model)
			throws Exception {
		request.getSession().invalidate();
		String linkurl = request.getParameter("linkurl");
		return "redirect:" + linkurl;
	}
	/**
	 * 返回页面
	 * @param response
	 * @param request
	 * @param spMember
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/memberLogin" ,method=RequestMethod.POST)
	public  String memberLogin(HttpServletResponse response,HttpServletRequest request,SpMember spMember,Model model) throws Exception{
		//清理session
		cleanUserSession(response, request);
		ReturnDatas returnDatas=ReturnDatas.getErrorReturnDatas();
		String redirectUrl=request.getParameter("redirectUrl");
		SpMember member=spMemberService.finderMemberByAccountAndState(spMember.getSiteId(),spMember.getAccount(), null);
		if(member==null){
			returnDatas.setMessage("用户不存在");
			model.addAttribute("returnDatas", returnDatas);
			if(StringUtils.isNotBlank(redirectUrl)){
				return "redirect："+redirectUrl;
			}
			return "";		
		}
		if(!"是".equals(member.getState())){
			returnDatas.setMessage("账号不可用");
			model.addAttribute("returnDatas", returnDatas);
			if(StringUtils.isNotBlank(redirectUrl)){
				return "redirect："+redirectUrl;
			}
			return "";		
		}
		String password=MD5Utils.encoderByMd5With32Bit(spMember.getPassword());
		if(!member.getPassword().equalsIgnoreCase(password)){
			returnDatas.setMessage("密码错误");
			model.addAttribute("returnDatas", returnDatas);
			if(StringUtils.isNotBlank(redirectUrl)){
				return "redirect："+redirectUrl;
			}
			return "";		
		}
		//添加session信息
		addUserSession(response, request, spMember);
		model.addAttribute("returnDatas", new ReturnDatas(ReturnDatas.SUCCESS, "登录成功"));
		redirectUrl=redirectUrl.replace("@", "&");
		if(StringUtils.isNotBlank(redirectUrl)){
			return "redirect："+redirectUrl;
		}
		return "";		
	}
	
	//添加用户信息到sesseion
	public void addUserSession(HttpServletResponse response,HttpServletRequest request,SpMember spMember){
		request.getSession().setAttribute("spMember", spMember);
		request.getSession().setAttribute("userId", spMember.getId());
		request.getSession().setAttribute("account", spMember.getAccount());
		request.getSession().setAttribute("gradeId", spMember.getGradeId());
		
		//lyf:得到服务器路径
		String path = request.getContextPath(); //这个获取的是WebTest
		String basePath = request.getScheme()+"://"+ request.getServerName() + ":" + request.getServerPort() + path;
		request.getSession().setAttribute("basePath", basePath);
	}
	//清理session信息 
	public void cleanUserSession(HttpServletResponse response,HttpServletRequest request){
		request.getSession().removeAttribute("spMember");
		request.getSession().removeAttribute("userId");
		request.getSession().removeAttribute("account");
		request.getSession().removeAttribute("gradeId");
		request.getSession().removeAttribute("basePath");
	}
	/**
	 * 会员注册
	 * @param request
	 * @param model
	 * @param spMember
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/register/json" ,method=RequestMethod.POST)
	public @ResponseBody ReturnDatas registerJson(HttpServletRequest request, Model model,SpMember spMember,HttpServletResponse response) throws Exception{
		
		ReturnDatas returnObject=ReturnDatas.getErrorReturnDatas();
		String siteId=spMember.getSiteId();
		
		String account =spMember.getAccount();
		
		SpMember mem=spMemberService.finderMemberByAccountAndState(siteId, account, null);
		if(mem!=null){
			returnObject.setMessage("该账户已注册！");
			return returnObject;
		}

		String pwd=spMember.getPassword();
		String password=MD5Utils.encoderByMd5With32Bit(pwd);
		spMember.setPassword(password);
		spMember.setState("是");
		spMember.setSiteId("a16");
		//注册时间
		spMember.setCreateDate(new Date());
		try {
			//修改用户
			spMemberService.save(spMember);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnObject.setStatus("error");
			returnObject.setMessage("注册失败！");
		}
		returnObject.setStatus(ReturnDatas.SUCCESS);
		returnObject.setQueryBean(spMember);
		returnObject.setData(spMember);
		return returnObject;
	}
	@RequestMapping("/ajaxFindMemberUserId")
	public @ResponseBody ReturnDatas ajaxFindMemberUserId(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		String userId = MemberUser.getUserId();

		if (StringUtils.isNotBlank(userId)) {
			returnObject.setData(userId);
			returnObject.setStatus(ReturnDatas.SUCCESS);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}
	/**
	 * 发送短信验证码
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/sendMsg")
	public @ResponseBody Map sendMsg(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		Map map=new HashMap();
		map.put("status", "error");
		//获取当前session，若无session则建一个新的session
		HttpSession session=request.getSession(true);
		
		//判断图文验证码知否正确
		String code=(String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		String yzm=request.getParameter("yzm");
		if(!code.equalsIgnoreCase(yzm)){
			map.put("status","error");
			map.put("message", "验证码错误");
			return map;
		}
		
		String siteId=request.getParameter("siteId");
		String phone = request.getParameter("mobile");
		String type=request.getParameter("type");
		SpMember mem=spMemberService.finderMemberByAccountAndState(siteId, phone, null);
		//若请求来自忘记密码页面，判断是否有此账号
		if(StringUtils.isNotBlank(type)&&type.equalsIgnoreCase("forgetPwd")){
			if(mem==null){
				map.put("status","error");
				map.put("message", "该用户尚未注册,请注册");
				return map;
			}
		}else if(StringUtils.isNotBlank(type)&&type.equalsIgnoreCase("register")){
			//若请求来自注册页面，判断是否有此账号
			if(mem!=null){
				map.put("status","error");
				map.put("message", "该用户已注册");
				return map;
			}
		}
		//发送短信验证码接口的返回值
		String ret="";
		
		//添加session时间戳（1分钟内只能执行一次）
		synchronized(session){
			Object timetoken=session.getAttribute("timetoken");
			long timeobject= 0;
			//若有时间戳判断是否已过1分钟
			if(timetoken!=null){
				timeobject=(Long)timetoken;
				if(System.currentTimeMillis()<(timeobject+1000*60)){
					map.put("status","error");
					map.put("message", "验证码已返回");
					return map;
				}
			}
			//若无时间戳，则添加时间戳
			session.setAttribute("timetoken", System.currentTimeMillis());
			//验证电话电话号
			boolean flag=checkMobile(phone);
			if(!flag){
				map.put("status","error");
				map.put("message", "手机号错误");
				return map;
			}
			
			try {
				// 获取6位随机码
				String random = getRandom(6, 1, 9);
				//发送短信验证码
				//ret = messageConfigService.sendMsg(siteId, phone, random);
				map.put("yzm", random);
				map.put("re", ret);
				map.put("status","success");
				//将验证码放入session中
				session.setAttribute("random", random);
				//保存失效时间(一分钟)
				Date date=new Date();
				Date endDate=new Date(date.getTime()+1000*60);
				session.setAttribute("timeout", endDate);
				map.put("timeout", endDate.getTime());
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
		}
		return map;
	}
	//验证电话号
	public boolean checkMobile(String mobile){
		boolean flag=false;
		Pattern regex=Pattern.compile("^1[3458]\\d{9}$");
		Matcher matcher=regex.matcher(mobile);
		flag=matcher.find();
		return flag;
	}
	/**
	 * 忘记密码：跳转到修改密码页面
	 * */
	@RequestMapping(value = "/forgetpwd/passEdit/pre")
	public String passEditPre(HttpServletRequest request, Model model) throws Exception {
		
		String mobile=request.getParameter("mobile");
		String siteId=request.getParameter("siteId");
		CmsSite site = siteService.findSiteById(siteId);
		User user = siteService.findById(site.getUserId(), User.class);
		model.addAttribute("siteId", siteId);
		model.addAttribute("site", site);
		model.addAttribute("userAccount", user.getAccount());
		
		model.addAttribute("mobile", mobile);
		return "/userpage/yiliao/a7/common/setpwd";
	}
	/**
	 * 忘记密码：修改用户密码
	 * */
	@RequestMapping(value = "/forgetpwd/passEdit")
	public @ResponseBody
	ReturnDatas forgetSetPassEdit(Model model,  HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String password = request.getParameter("password");
		String mobile=request.getParameter("mobile");
		String siteId=request.getParameter("siteId");
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		//判断是否已通过短信验证码验证
		HttpSession session=request.getSession(true);
		String flag=(String) session.getAttribute("through");
		if(StringUtils.isBlank(flag)||!flag.equalsIgnoreCase("true")){
			returnObject.setStatus("error");
			returnObject.setMessage("未通过验证！");
			return returnObject;
		}
		//删除through
		session.removeAttribute("through");
		SpMember mem=spMemberService.finderMemberByAccountAndState(siteId, mobile, null);
		if(mem==null){
			returnObject.setStatus("error");
			returnObject.setMessage("该账户尚未注册");
			return returnObject;
		}
		if(StringUtils.isBlank(password)){
			returnObject.setStatus("errorpwd");
			returnObject.setMessage("密码不符合规范，修改失败");
			return returnObject;
		}
		String newpass=MD5Utils.encoderByMd5With32Bit(password);
		mem.setPassword(newpass);
		spMemberService.update(mem);
		returnObject.setMessage("修改成功");
		return returnObject;
	}
	/**
	 * 验证短信验证码
	 * */
	@RequestMapping(value = "/forgetpwd/pass")
	public @ResponseBody
	ReturnDatas smsCodePass(Model model,  HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		HttpSession session=request.getSession(true);
		//判断图文验证码知否正确
		String code=(String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		String yzm=request.getParameter("yzm");
		if(!code.equalsIgnoreCase(yzm)){
			returnObject.setStatus(ReturnDatas.SUCCESS);
			returnObject.setMessage("图文验证码错误");
			return returnObject;
			}
		//判断验证码
		String random=request.getParameter("random");
		if(!random.equals(request.getSession().getAttribute("random"))){
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("短信验证码错误");
			return returnObject;
		}
		//判断时间
		Date now=new Date();
		Date timeout=(Date)request.getSession().getAttribute("timeout");
		if(timeout.before(now)){
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("短信验证码已失效");
			return returnObject;
		}
		
		// 判断验证码正确 并且没过失效时间 成功提交
		session.setAttribute("through", "true");
		return returnObject;
	}
	/**
	 * 修改登录密码
	 * */
	@RequestMapping("/update/LoginPwd")
	public @ResponseBody ReturnDatas findPwd(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String userId = MemberUser.getUserId();
		
		if (StringUtils.isNotBlank(userId)) {
			SpMember user = spMemberService.findSpMemberById(userId);
			String pwd=user.getPassword();
			//使用MD5加密
			String oldMd5=MD5Utils.encoderByMd5With32Bit(oldPwd);
			//判断当前密码是否一致
			if(!oldMd5.equalsIgnoreCase(pwd)){
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage("当前密码不正确");
				return returnObject;
			}
			user.setPassword(MD5Utils.encoderByMd5With32Bit(newPwd));
			returnObject.setMessage("初始化密码成功");
			
			
		}else{
			returnObject.setStatus("nologin");
			returnObject.setMessage("没有登录");
			
		}
		return returnObject;
	}

	private String getRandom(int len, int start, int end) {
		String ret = "";
		for (int i = 1; i <= len; i++) {
			int n = RandomUtils.nextInt(start, end);
			ret += n;
		}
		return ret;
	}
	/**
	 * 生成验证码
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getCaptcha")
	public void getCaptcha(HttpSession session,HttpServletResponse response) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		CaptchaUtils tool = new CaptchaUtils();
		StringBuffer code = new StringBuffer();
		BufferedImage image = tool.genRandomCodeImage(code);
		session.removeAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		session.setAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM, code.toString());

		// 将内存中的图片通过流动形式输出到客户端
		ImageIO.write(image, "JPEG", response.getOutputStream());
		return;
	}
	/**
	 * 检验验证码
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/checkCaptcha")
	public @ResponseBody ReturnDatas checkCaptcha(HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException {
		ReturnDatas returnObject = ReturnDatas.getErrorReturnDatas();
		String code=(String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		String yzm=request.getParameter("yzm");
		if(code.equalsIgnoreCase(yzm)){
			returnObject.setStatus(ReturnDatas.SUCCESS);
			returnObject.setMessage("图文验证码错误");
		}
		return returnObject;
	}
	/**
	 * 退出
	 * @return 
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/exit")
	public @ResponseBody ReturnDatas exitLogin(HttpSession session,HttpServletResponse response,HttpServletRequest request) throws IOException {
		ReturnDatas returnObject = ReturnDatas.getErrorReturnDatas();
		String exit=request.getParameter("exit");
		if(StringUtils.isNotBlank(exit)&&exit.equals("true")){
			cleanUserSession(response,request);
			returnObject.setStatus("success");
			returnObject.setMessage("已退出！");
		}
		return returnObject;
	}
	
}
