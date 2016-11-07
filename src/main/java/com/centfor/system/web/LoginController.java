package com.centfor.system.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.shiro.ShiroUser;
import com.centfor.frame.util.CaptchaUtils;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.frame.util.SecUtils;
import com.centfor.system.entity.User;
import com.centfor.system.service.IUserService;
@Controller
@RequestMapping(value="/system")
public class LoginController extends BaseController  {
	@Resource
	private IUserService userService;
	private String indexUrl="/index";
	
	
	/**
	 * 首页的映射
	 * @param model
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/index")
		public String index(Model model) throws Exception {
			model.addAttribute("siteName", "我是中文站点");
				return indexUrl;
			
		}
		
		@RequestMapping(value = "/login",method=RequestMethod.GET)
		public String login(Model model,HttpServletRequest request) throws Exception {
			if(SecurityUtils.getSubject().isAuthenticated()){
				return redirect+"/system"+indexUrl;
			}
			//默认赋值message,避免freemarker尝试从session取值,造成异常
			model.addAttribute("message", "");
			return "/login";
		}
		@RequestMapping(value = "/login",method=RequestMethod.POST)
		public String loginPost(User currUser,HttpSession session,Model model,HttpServletRequest request) throws Exception {
			Subject user = SecurityUtils.getSubject();
			
			//验证码校验
//			  String code = (String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
//			  if(StringUtils.isNotBlank(code)){
//				  code=code.toLowerCase().toString();
//			  }
//			String submitCode = WebUtils.getCleanParam(request, GlobalStatic.DEFAULT_CAPTCHA_PARAM);
//			  if(StringUtils.isNotBlank(submitCode)){
//				  submitCode=submitCode.toLowerCase().toString();
//			  }
//			if (StringUtils.isBlank(submitCode) ||StringUtils.isBlank(code)||!code.equals(submitCode)) {
//				model.addAttribute("message", "验证码错误!");
//				return "/login";
//	        }
	        
			
			UsernamePasswordToken token = new UsernamePasswordToken(currUser.getAccount(),currUser.getPassword());
			
			String rememberme=request.getParameter("rememberme");
			if(StringUtils.isNotBlank(rememberme)){
			token.setRememberMe(true);
			}else{
				token.setRememberMe(false);
			}
			
			try {
				//会调用 shiroDbRealm 的认证方法 com.centfor.frame.shiro.ShiroDbRealm.doGetAuthenticationInfo(AuthenticationToken)
				user.login(token);
				user.getSession().setAttribute("systemLogin", true);
			} catch (UnknownAccountException uae) {
				model.addAttribute("message", "账号不存在!");
				return "/login";
			} catch (IncorrectCredentialsException ice) {
				model.addAttribute("message", "密码错误!");
				return "/login";
			} catch (LockedAccountException lae) {
				model.addAttribute("message", "账号被锁定!");
				return "/login";
			} catch (Exception e) {
				model.addAttribute("message", "未知错误,请联系管理员.");
				return "/login";
			}
		
			//String sessionId = session.getId();
			
			//Cache<Object, Object> cache = shiroCacheManager.getCache(GlobalStatic.authenticationCacheName);
			//cache.put(GlobalStatic.authenticationCacheName+"-"+currUser.getAccount(), sessionId);
			
			/*
			Cache<String, Object> cache = shiroCacheManager.getCache(GlobalStatic.shiroActiveSessionCacheName);
			Serializable oldSessionId = (Serializable) cache.get(currUser.getAccount());
			if(oldSessionId!=null){
				Subject subject=new Subject.Builder().sessionId(oldSessionId).buildSubject();
				subject.logout();
			}
			cache.put(currUser.getAccount(), session.getId());
			*/
			
			return redirect+"/system"+indexUrl;
		}
		
		/**
		 * 没有权限
		 * @param model
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/unauth")
		public String unauth(Model model) throws Exception {
			if(SecurityUtils.getSubject().isAuthenticated()==false){
				return redirect+"/login";
			}
				return "/unauth";
			
		}
		
		/**
		 * 退出
		 * @param request
		 */
		@RequestMapping(value="/logout")
	    public void logout(HttpServletRequest request){
	        Subject subject = SecurityUtils.getSubject();
	        if (subject != null) {           
	            subject.logout();
	        }
	        //request.getSession().invalidate();
	    }
		//updatePassword
		
		/**
		 * @author lyf
		 * 进入修改密码页面
		 */
		@RequestMapping(value = "/update/pre")
		public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
			return "/modifyPassword"; 
		}
		
		/**
		 * @author lyf
		 * 修改密码操作
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/updatePassword")
		public @ResponseBody ReturnDatas updatePassword(HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
			String password = request.getParameter("password");
			String new_password = request.getParameter("new_password");
			User user = SessionUser.getShiroUser().getUser();
			//数据库中登陆用户原密码
			String old_pwd = user.getPassword();
			//页面输入的原密码
			String md5_password = SecUtils.encoderByMd5With32Bit(password);
			if(old_pwd.equals(md5_password)){
				//原密码正确，更新新密码
				user.setPassword(SecUtils.encoderByMd5With32Bit(new_password));
				userService.update(user);
				returnObject.setMessage(MessageUtils.PASSWORD_EDIT_SUCCESS);
			}
			else{
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage(MessageUtils.PASSWORD_ERROR);
			}
			return returnObject;
		}
		/**
		 * 自动登录,无需账号密码,测试代码,默认注释，根据实际修改
		 * @param model
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		//@RequestMapping(value = "/auto/login")
		public String autologin(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
			ShiroUser shiroUser=new ShiroUser();
			shiroUser.setId("admin");
			shiroUser.setName("admin");
			shiroUser.setAccount("admin");
			 SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, GlobalStatic.authorizingRealmName);
             WebSubject.Builder builder = new WebSubject.Builder(request,response);
             builder.principals(principals);
             builder.authenticated(true);
             WebSubject subject = builder.buildWebSubject();
             ThreadContext.bind(subject);
         	return redirect+"/system"+indexUrl;
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
		
}
