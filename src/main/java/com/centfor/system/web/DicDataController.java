package  com.centfor.system.web;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.system.entity.DicBusiness;
import com.centfor.system.entity.DicData;
import com.centfor.system.service.IDicDataService;


/**
 * TODO 在此加入类描述
 * @copyright {@link bbz}
 * @author 9iu.dicData<Auto generate>
 * @version  2013-07-31 15:56:45
 * @see com.centfor.bbz.web.DicData
 */
@Controller
@RequestMapping(value="/system/dicdata/{pathtypekey}")
public class DicDataController  extends BaseController {
	@Resource
	private IDicDataService dicDataService;
	
	private String listurl="/system/dicdata/dicdataList";
	
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param dicData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@PathVariable String pathtypekey,HttpServletRequest request, Model model,DicData dicData) 
			throws Exception {
		ReturnDatas returnObject = listjson(pathtypekey,request, model, dicData);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param dicData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(@PathVariable String pathtypekey,HttpServletRequest request, Model model,DicData dicData) throws Exception{
		dicData.setTypekey(pathtypekey);
		List<DicData> datas=dicDataService.findListDicData( pathtypekey );
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(datas);
		Map<String,String> map=new HashMap<String,String>();
		map.put("typekey", pathtypekey);
		returnObject.setMap(map);
		return returnObject;
	}
	
	
	
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param dicData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajax/findbypid")
	public @ResponseBody
	ReturnDatas findbypid(@PathVariable String pathtypekey,HttpServletRequest request, Model model,DicData dicData) throws Exception{
		String pid=request.getParameter("pid");
		if(StringUtils.isBlank(pid)){
			pid=null;
		}
		List<DicData> datas=dicDataService.findListDicDataByPid( pathtypekey,pid);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(datas);
		return returnObject;
	}
	
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param dicData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajax/tree/json")
	public @ResponseBody
	ReturnDatas ajaxtreejson(@PathVariable String pathtypekey,HttpServletRequest request, Model model,DicData dicData) throws Exception{
		dicData.setTypekey(pathtypekey);
		List<DicData> datas=dicDataService.findAjaxTreeJson(null, pathtypekey);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(datas);
		return returnObject;
	}
	
	
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param dicData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajax/business")
	public @ResponseBody
	ReturnDatas ajaxBusiness(@PathVariable String pathtypekey,HttpServletRequest request, Model model) throws Exception{
		
	   String businessId=request.getParameter("businessId");	
	   if(StringUtils.isBlank(businessId)){
		   return ReturnDatas.getSuccessReturnDatas();
	   }
	    List<DicBusiness> datas = dicDataService.findBusinessById(businessId, pathtypekey, null);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(datas);
		return returnObject;
	}
	
	
	
	
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param dicData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tree")
	public String tree(@PathVariable String pathtypekey,HttpServletRequest request, Model model,DicData dicData) 
			throws Exception {
		if(dicData!=null){
			dicData.setTypekey(pathtypekey);
		}
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(dicData);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/dicdata/tree";
	}
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param dicData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tree/pre")
	public String tree2(@PathVariable String pathtypekey,HttpServletRequest request, Model model,DicData dicData) 
			throws Exception {
		if(dicData!=null){
			dicData.setTypekey(pathtypekey);
		}
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(dicData);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/dicdata/tree2";
	}
	
	

	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(@PathVariable String pathtypekey,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		
		ReturnDatas returnObject = lookjson(pathtypekey,model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/dicData/dicDataLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(@PathVariable String pathtypekey,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
		  DicData dicData = dicDataService.findDicDataById(id,pathtypekey);
		  returnObject.setData(dicData);
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
	ReturnDatas saveorupdate(@PathVariable String pathtypekey,Model model,DicData dicData,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
			try {
				String id=dicData.getId();
				String pid=dicData.getPid();
				if(StringUtils.isBlank(id)){
					dicData.setId(null);
				}
				if(StringUtils.isBlank(pid)){
					dicData.setPid(null);
				}
		    dicData.setTypekey(pathtypekey);
			dicDataService.saveorupdateDicData(dicData,pathtypekey);
			
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
	public String edit(@PathVariable String pathtypekey,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(pathtypekey,model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/dicData/dicDataCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ReturnDatas destroy(@PathVariable String pathtypekey,HttpServletRequest request) throws Exception {

			// 执行删除
		try {
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
				dicDataService.deleteDicDataById(id,pathtypekey);
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
	ReturnDatas delMultiRecords(@PathVariable String pathtypekey,HttpServletRequest request, Model model) {
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
			dicDataService.deleteDicDataByIds(ids,pathtypekey);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}


	

	
	
	
	
}
