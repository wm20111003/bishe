package  com.centfor.school.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.shiro.MemberUser;
import com.centfor.frame.util.DateUtils;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MD5Utils;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.PropertiesUtils;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.front.entity.BaseUpload;
import com.centfor.school.entity.SpMember;
import com.centfor.school.service.ISpMemberService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-01-19 10:01:00
 * @see com.centfor.shop.web.SpMember
 */
@Controller
@RequestMapping(value="/front/spmember")
public class SpMemberController  extends BaseController {
	@Resource
	private ISpMemberService spMemberService;
	
	private String listurl="/school/spmember/spmemberList";
	
	private static String _UPLOAD_FILE = "";
	
	private static String _SYSDICT = "";
	static {
		PropertiesUtils pu = new PropertiesUtils("db");
		_UPLOAD_FILE = pu.getString("upload.file");
	} 
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param spMember
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,SpMember spMember) 
			throws Exception {
		//获取登录人的所属站点Id
		//String siteId=SessionUser.getShiroUser().getUser().getSiteId();
		//spMember.setSiteId(siteId);
		//获得该站点下面的会员
		ReturnDatas returnObject = listjson(request, model, spMember);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param spMember
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,SpMember spMember) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		String _pageIndex = request.getParameter("pageIndex");
		String _pageSize=request.getParameter("pageSize");
		int pageIndex = NumberUtils.toInt(_pageIndex, 1);
		int pageSize=NumberUtils.toInt(_pageSize,10);
		Page page = new Page(pageIndex , pageSize);
		// ==执行分页查询
		List<SpMember> datas=spMemberService.findListDataByFinder(null,page,SpMember.class,spMember);
		returnObject.setQueryBean(spMember);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
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
		  SpMember spMember = spMemberService.findSpMemberById(id);
		   returnObject.setData(spMember);
		}else{
		returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas saveorupdate(Model model,SpMember spMember,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		String id = request.getParameter("id");
		String fileUrl = request.getParameter("filePath");
		try {
			if(StringUtils.isBlank(id)){
				spMember.setId(null);
			}
			if(StringUtils.isNotBlank(fileUrl)){
				spMember.setHeadimgsrc(fileUrl);
			}
			if(spMember!=null){
			  spMemberService.saveorupdate(spMember);
			}
			
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			e.printStackTrace();
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
		return "/school/spmember/spmemberCru";
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
				spMemberService.deleteById(id,SpMember.class);
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
	 * 人员头像上传Controller
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadImg")
	public @ResponseBody ReturnDatas upload(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		BaseUpload br = new BaseUpload();
		try {
			br.upload(file,"/"+ _UPLOAD_FILE+"/user/", request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		returnObject.setData(br);
		returnObject.setMessage("上传成功");
		return returnObject;
	}
	/**
	 * 进入修改密码页面
	 */
	@RequestMapping(value = "/editPwd/pre")
	public String editPwdpre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		String id=request.getParameter("id");
		/*lyf：修改*/
		SpMember memb= spMemberService.findById(id, SpMember.class);
		if(StringUtils.isNotBlank(id) && memb != null){
			String name = memb.getName();
			model.addAttribute("name", name);
		}
		model.addAttribute("id", id);
		return "school/spmember/spmemberPwd";
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/editPwd")
	public @ResponseBody ReturnDatas  editPwd(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		String id=request.getParameter("id");
		SpMember member=spMemberService.findById(id, SpMember.class);
		if(member==null){
			ReturnDatas returnDatas=ReturnDatas.getErrorReturnDatas();
			returnDatas.setMessage("用户不存在");
			return returnDatas;
		}else{
			/*lyf:修改 2015-03-29*/
			String password_new=request.getParameter("password_new");
			if(StringUtils.isNotBlank(password_new)){				
				String password_new_md5 = MD5Utils.encoderByMd5With32Bit(password_new);
				member.setPassword(password_new_md5);
				spMemberService.update(member);
				return new ReturnDatas(ReturnDatas.SUCCESS,MessageUtils.PASSWORD_EDIT_SUCCESS);
			}else{
				ReturnDatas returnDatas=ReturnDatas.getErrorReturnDatas();
				returnDatas.setMessage("密码不能为空");
				return returnDatas;
			}
		}
	}
	/**
	 * 是否可用         
	 */
	@RequestMapping(value = "/isValid")
	public @ResponseBody ReturnDatas  isValid(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		String id=request.getParameter("id");
		SpMember member=spMemberService.findById(id, SpMember.class);
		if(member==null){
			ReturnDatas returnDatas=ReturnDatas.getErrorReturnDatas();
			returnDatas.setMessage("用户不存在");
			return returnDatas;
		}else{
			String state=request.getParameter("state");
			if(StringUtils.isNotBlank(state)&&("是".equals(state)||"否".equals(state))){				
				ReturnDatas returnDatas=ReturnDatas.getSuccessReturnDatas();
				if("否".equals(state)){
					member.setState("是");
				}else{
					member.setState("否");
				}
				spMemberService.update(member);
				return returnDatas;
			}else{
				ReturnDatas returnDatas=ReturnDatas.getErrorReturnDatas();
				returnDatas.setMessage("状态参数错误");
				return returnDatas;
			}
		}
	}
	/**
	 * 进入会员crm统计图表页面
	 */
	@RequestMapping(value = "/crm/pre")
	public String CRMpre(Model model,HttpServletRequest request,SpMember spMember)  throws Exception{
		ReturnDatas returnObject =crmjson(model,request,spMember);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/shop/memberCount/memberCRM";
	}
	/**
	 * 
	 * crm json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param spMemberJifen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/crm/json")
	public @ResponseBody
	ReturnDatas crmjson( Model model,HttpServletRequest request,SpMember spMember) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		if(SessionUser.getShiroUser()==null){
			returnObject.setMessage(ReturnDatas.ERROR);
			return returnObject;
		}
		List<SpMember> list=new ArrayList<SpMember>();
		//7天中每天的会员增加量数组
		int[] counts=new int[7];
		//7天的时间对象集合
		List<Date> dates=new ArrayList<Date>();
		//得到现在时间
		Date now=new Date();
		//今天零时时间
		Date today=todayDate(now);
		
		SpMember member=new SpMember();
		member.setSiteId(SessionUser.getShiroUser().getUser().getSiteId());
		
		//重新设置开始和结束时间
				Date end=today;
				Date start=preDate(end);
		//循环得到后六天的增加量，并加入数组
		for(int i=0;i<7;i++){
			member.setStartDate(start);
			list=spMemberService.queryForListByEntity(member, null);
			//添加增量入数组
			counts[i]=list.size();
			//添加时间对象到集合
			dates.add(start);
			if(i<6){
				end=start;
				start=preDate(end);
			}
		}
		returnObject.setQueryBean(dates);
		returnObject.setData(counts);
		return returnObject;
	}
	//得到前一天0点0分0时的时间
	@SuppressWarnings("deprecation")
	public Date preDate(Date date){
		int d=date.getDate();
		int m=date.getMonth();
		int y=date.getYear();
		Date pre=new Date(y,m,d-1);
		return pre;
		
	}
	//得到今天0点0分0时的时间
	@SuppressWarnings("deprecation")
	public Date todayDate(Date date){
		int d=date.getDate();
		int m=date.getMonth();
		int y=date.getYear();
		Date today=new Date(y,m,d);
		return today;
		
	}
}
