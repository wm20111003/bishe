package  com.centfor.system.web;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.system.entity.Menu;
import com.centfor.system.entity.Org;
import com.centfor.system.service.IMenuService;
import com.centfor.system.service.IUserRoleMenuService;



/**
 * 
 * @Title: MenuController.java
 * @Package com.centfor.bbz.web
 * @Description: o
 * @author ZhangNan
 * @date 2013-7-11 下午9:36:31
 * @version V1.0
 *
 */
@Controller
@RequestMapping(value="/system/menu")
public class MenuController  extends BaseController {
	
	@Resource
	private IMenuService menuService;
	
	@Resource
	private IUserRoleMenuService userRoleMenuService;
	
	private String listurl="/system/menu/menuList";  //菜单列表路径
	
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Menu menu) throws Exception {
		ReturnDatas returnObject = listjson(request, model, menu);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model, Menu menu) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
	
		//Page page = newPage(request);
		
		List<Menu> datas = menuService.findListDataByFinder(null, null, Menu.class, menu);
		returnObject.setQueryBean(menu);
		//returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
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
	@RequestMapping("/tree")
	public String tree(HttpServletRequest request, Model model,Org org) 
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData("/system/menu/list/json");
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/menu/tree";
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
	public String treepre(HttpServletRequest request, Model model,Org org) 
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/menu/tree2";
	}
	
	

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/menu/menuLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			Menu menu = menuService.findMenuById(id);
			returnObject.setData(menu);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}

	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody ReturnDatas saveorupdate(Menu menu, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id=menu.getId();
			String pid=menu.getPid();
			String pageurl=menu.getPageurl();
			if(StringUtils.isBlank(id)){
				menu.setId(null);
			}	
			if(StringUtils.isBlank(pid)){
				menu.setPid(null);
			}
			if(StringUtils.isBlank(pageurl)){
				menu.setPageurl(null);
			}
			menuService.saveorupdateMenu(menu);
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
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/menu/menuCru";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody
	ReturnDatas destroy(HttpServletRequest request) throws Exception {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				menuService.deleteMenuRoleByMenuId(id);
				return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
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
	public @ResponseBody
	ReturnDatas delMultiRecords(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_FAIL);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			menuService.deleteByIds(ids, Menu.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_ALL_SUCCESS);
	}
	
	/**
	 * 
	 * @Title: menu
	 * @Description: 菜单列表
	 * @return
	 * @return Map
	 * @throws
	 */
	@RequestMapping("/leftMenu")
	public @ResponseBody ReturnDatas leftMenu(){
		//获取当前登录人
		String userId=SessionUser.getUserId();
		if(StringUtils.isBlank(userId)){
			return null;
		}
		
		try {
		     List<Menu> listMenu=userRoleMenuService.findMenuAndLeafByUserId(userId);
			return new ReturnDatas(ReturnDatas.SUCCESS, null,listMenu);
		
		} catch (Exception e) {
			logger.error("获取导航菜单异常", e);
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.SELECT_WARING);
		}
	
	}
	
	
	
	
	
	
}