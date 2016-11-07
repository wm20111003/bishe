package  com.centfor.school.web;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.ThreadContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.school.entity.SpMember;
import com.centfor.school.entity.WorkCoursename;
import com.centfor.school.entity.WorkMark;
import com.centfor.school.service.ISpMemberService;
import com.centfor.school.service.IWorkCoursenameService;
import com.centfor.school.service.IWorkMarkService;

import freemarker.template.utility.NumberUtil;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2016-03-29 11:40:01
 * @see com.centfor.school.web.WorkMark
 */
@Controller
@RequestMapping(value="/front/workmark")
public class WorkMarkController  extends BaseController {
	@Resource
	private IWorkMarkService workMarkService;
	@Resource
	private IWorkCoursenameService workCoursenameService;
	@Resource
	private ISpMemberService spMemberService;
	private String listurl="/school/workmark/workmarkList";
	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param workMark
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,WorkMark workMark) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, workMark);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param workMark
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,WorkMark workMark) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		page.setPageSize(10);
		// ==执行分页查询
		List<WorkMark> datas=workMarkService.findListDataByFinder(null,page,WorkMark.class,workMark);
		for(int i=0;i<datas.size();i++){
			WorkCoursename workCourseName = workCoursenameService.findById(datas.get(i).getCourseNameId(), WorkCoursename.class);
			datas.get(i).setCourseName(workCourseName.getCourseName());
			datas.get(i).setCourseType(workCourseName.getCourseType());
			SpMember spMember = spMemberService.findById(datas.get(i).getUserId(), SpMember.class);
			datas.get(i).setMemberName(spMember.getName());
		}
		returnObject.setQueryBean(workMark);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,WorkMark workMark) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = workMarkService.findDataExportExcel(null,listurl, page,WorkMark.class,workMark);
		String fileName="workMark"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
		return;
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/school/workmark/workmarkLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
		  WorkMark workMark = workMarkService.findWorkMarkById(id);
		  WorkCoursename workCourseName = workCoursenameService.findById(workMark.getCourseNameId(), WorkCoursename.class);
		  workMark.setCourseName(workCourseName.getCourseName());
		  workMark.setCourseType(workCourseName.getCourseType());
		  SpMember spMember = spMemberService.findById(workMark.getUserId(), SpMember.class);
		  workMark.setMemberName(spMember.getName());
		   returnObject.setData(workMark);
		}else{
		returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
		
	}
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/lookmark/json")
	public @ResponseBody
	ReturnDatas lookmark(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String item=request.getParameter("item");
		String userId = (String) ThreadContext.getSubject().getSession().getAttribute("userId");
		//判断是否登录
		if(StringUtils.isNotBlank(userId)){
			SpMember spMember =spMemberService.findById(userId, SpMember.class);
			List<WorkCoursename> workCoursenames = workCoursenameService.findWorkCourseNameByItemAndMajorName(item, spMember.getMajorName());
			for(int i=0;i<workCoursenames.size();i++){
				String courseNameId = workCoursenames.get(i).getId();
				WorkMark workMark =workMarkService.findWorkMarkByUserIdCourseNameId(userId, courseNameId);
				if(workMark != null){
					workCoursenames.get(i).setMark(workMark.getMark());
					workCoursenames.get(i).setGpa(workMark.getGpa());
				}
			}
			returnObject.setData(workCoursenames);
		}else{
			List<WorkCoursename> workCoursenames = workCoursenameService.findWorkCourseNameByItem(item);
			returnObject.setData(workCoursenames);
		}
		return returnObject;
		
	}
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas saveorupdate(Model model,WorkMark workMark,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			java.lang.String id =workMark.getId();
			String userId = workMark.getUserId();
			String courseNameId = workMark.getCourseNameId();
			WorkMark workmarks = workMarkService.findWorkMarkByUserIdCourseNameId(userId, courseNameId);
			if(workmarks != null){
				id = workmarks.getId();
			}
			if(StringUtils.isBlank(id)){
			  workMark.setId(null);
			}
			workMarkService.saveorupdate(workMark);
			
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
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/school/workmark/workmarkCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ReturnDatas delete(HttpServletRequest request) throws Exception {

			// 执行删除
		try {
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
				workMarkService.deleteById(id,WorkMark.class);
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
	public @ResponseBody
	ReturnDatas deleteMore(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if(StringUtils.isBlank(records)){
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
			workMarkService.deleteByIds(ids,WorkMark.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}

}
