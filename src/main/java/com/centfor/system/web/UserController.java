package com.centfor.system.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.shiro.ShiroUser;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.frame.util.SecUtils;
import com.centfor.system.entity.Org;
import com.centfor.system.entity.Role;
import com.centfor.system.entity.User;
import com.centfor.system.entity.UserRole;
import com.centfor.system.service.IUserRoleService;
import com.centfor.system.service.IUserService;

/**
 * 用户管理Controller,PC和手机浏览器用ACE自适应,APP提供JSON格式的数据接口
 * 
 * @copyright {@link 9iu.org}
 * @author 9iu.org<Auto generate>
 * @version 2014-06-26 11:36:47
 * @see com.centfor.bbz.web.User
 */
@Controller
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {
	@Resource
	private IUserService userService;
	@Resource
	private IUserRoleService userRoleService;

	private String listurl = "/system/user/userList";

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
	public String list(HttpServletRequest request, Model model, User user)
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, user);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	/**
	 * @author lyf
	 * @time  2015.02.04
	 * 根据登陆用户，判断用户角色
	 * true/false
	 */
	boolean judgeRole() throws Exception{
		boolean flag = false;
		User user = SessionUser.getShiroUser().getUser();
		String user_id = user.getId();
		UserRole role = new UserRole();
		role.setUserId(user_id);
		List<UserRole> roles = null;
		roles = userRoleService.findListDataByFinder(null, null, UserRole.class, role);
		if(CollectionUtils.isNotEmpty(roles)){
			String roleName = roles.get(0).getRoleId();
			if(roleName.equalsIgnoreCase("admin")){
				flag = true;
			}
		}
	return flag;
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
	public @ResponseBody ReturnDatas listjson(HttpServletRequest request,
			Model model, User user) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		boolean flag =  judgeRole();
		if(flag == false){
			//登陆用户为非admin用户，根据siteId,查看本店铺人员
			User user2 = SessionUser.getShiroUser().getUser();
			String siteId = user2.getSiteId();
			user.setSiteId(siteId);
		}
		List<User> datas = userService.findListDataByFinder(null, page,
				User.class, user);
		returnObject.setQueryBean(user);
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
	 * @param user
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,
			HttpServletResponse response, Model model, User user)
			throws Exception {
		// ==构造分页请求
		Page page = newPage(request);
		File file = userService.findDataExportExcel(null, listurl, page,
				User.class, user);
		String fileName = "user" + GlobalStatic.excelext;
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
		return "/system/user/userLook";
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
			User user = userService.findUserById(id);
			returnObject.setData(user);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}

	/**
	 * 用户名惟一校验,返回json格式数据
	 * 
	 */
	@RequestMapping("/ajax/checkuser")
	public @ResponseBody Map<String, String> checkuserName(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String account = request.getParameter("param");
		String id = request.getParameter("id");
		Map<String, String> map = new HashMap<String, String>();
		String info = "ok";
		String status = "y";
		boolean b = userService.findUserExsit(account, id);
		if (b) {
			info = "账号已经存在，请重新填写！";
			status = "n";
		}
		map.put("info", info);
		map.put("status", status);
		return map;
	}
	
	@RequestMapping("/updatePassword")
	public @ResponseBody ReturnDatas updatePassword(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String new_password = request.getParameter("new_password");
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(password)){
			User user = userService.findUserById(id);
			//数据库原md5加密后的密码
			String md5_pwd = user.getPassword();
			//页面输入的原密码
			String md5_password = SecUtils.encoderByMd5With32Bit(password);
			//将加密后的页面输入的原密码 与数据库中原密码比较
			if(md5_password.equals(md5_pwd)){
				//原密码正确，更新新密码
				user.setPassword(SecUtils.encoderByMd5With32Bit(new_password));
				userService.update(user);
				returnObject.setMessage(MessageUtils.PASSWORD_EDIT_SUCCESS);
			}
			else{
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage(MessageUtils.PASSWORD_ERROR);

			}
		}
		return returnObject;
	}
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas saveorupdate(User user,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		try {
			String id = user.getId();

			//修改界面，去除了密码选项
			String password = user.getPassword();
			if (StringUtils.isBlank(password)) {
				user.setPassword(null);
			} else {
				user.setPassword(SecUtils.encoderByMd5With32Bit(password));
			}
			user.setState("是");

			String str_orgIds = request.getParameter("orgIds");
			String[] orgIds = null;
			List<Org> listOrg = null;

			if (StringUtils.isNotBlank(str_orgIds)) {
				orgIds = str_orgIds.split(",");
			}
			if (orgIds != null && orgIds.length > 0) {
				Set<String> set = new HashSet<String>();
				for (String s : orgIds) {
					if (StringUtils.isBlank(s)) {
						continue;
					}
					set.add(s);
				}
				listOrg = new ArrayList<Org>();
				for (String s2 : set) {
					Org org = new Org();
					org.setId(s2);
					listOrg.add(org);
				}

			}

			user.setUserOrgs(listOrg);

			String[] roleIds = request.getParameterValues("roleIds");
			List<Role> listRole = null;
			if (roleIds != null && roleIds.length > 0) {
				Set<String> set = new HashSet<String>();
				for (String s : roleIds) {
					if (StringUtils.isBlank(s)) {
						continue;
					}
					set.add(s);
				}
				listRole = new ArrayList<Role>();
				for (String s2 : set) {
					Role role = new Role();
					role.setId(s2);
					listRole.add(role);
				}

			}

			user.setUserRoles(listRole);

			if (StringUtils.isBlank(id)) {
				
				//添加新用户默认密码为123456
				//user.setPassword(SecUtils.encoderByMd5With32Bit("123456"));
				user.setId(null);
				userService.saveUser(user);
				returnObject.setMessage(MessageUtils.ADD_SUCCESS);
				// userService.save(user);
				// 当创建新用户时，自动生成该用户文件夹

//				String account = user.getAccount();
//				String path1 = request.getSession().getServletContext()
//						.getRealPath("userstatic");
//				File root1 = new File(path1);
//				File app_file = new File(root1, account);
//				app_file.mkdir();
//				String path2 = request.getSession().getServletContext()
//						.getRealPath("WEB-INF/freemarker/userpage");
//				File root2 = new File(path2);
//				File freemarker_file = new File(root2, account);
//				freemarker_file.mkdir();

			} else {
				//通过id找到数据库中原用户信息
				User old_user= userService.findUserById(id);
				//取出加密后的原密码
				String old_pwd = old_user.getPassword();
				//赋给更改后的数据
				user.setPassword(old_pwd);
				userService.updateUser(user);
				returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
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
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/user/userCru";
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre2")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/user/modifyPassword";
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
				//判断登陆用户角色
				boolean flag =  judgeRole();
				if(flag){
					//admin，可以删除
					return userService.adminDelete(id);
				}
				else{
					//非admin用户，不能删除
					userService.deleteById(id, User.class);
				}
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
			userService.deleteByIds(ids, User.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}

	@RequestMapping(value = "/ajax/finduser")
	public @ResponseBody List<Map<String, Object>> findProductType(Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		String quser = request.getParameter("q");
		Page page = new Page(1);
		List<User> lst = userService.findLikeUser(quser, page);
		if (CollectionUtils.isNotEmpty(lst)) {
			for (User p : lst) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", p.getId());
				map.put("text", p.getName());
				listmap.add(map);
			}
		}
		return listmap;
	}



}
