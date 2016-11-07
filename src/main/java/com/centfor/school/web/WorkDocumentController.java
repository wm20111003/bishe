package  com.centfor.school.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.centfor.school.entity.UploadFilePathVO;
import com.centfor.school.entity.WorkDocument;
import com.centfor.school.service.IWorkDocumentService;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.PropertiesUtils;
import com.centfor.frame.util.ReturnDatas;



/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-04-29 16:06:13
 * @see com.centfor.school.web.WorkDocument
 */
@Controller
@RequestMapping(value="/front/workdocument")
public class WorkDocumentController  extends BaseController {
	@Resource
	private IWorkDocumentService workDocumentService;
	
	private String listurl="/school/workdocument/workdocumentList";
	
	private static String _SYSDICT = "";
	
	private static String _UPLOAD_FILE = "";
	
	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
	}
	static {
		PropertiesUtils pu = new PropertiesUtils("db");
		_UPLOAD_FILE = pu.getString("upload.file");
	}
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param workDocument
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,WorkDocument workDocument) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, workDocument);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param workDocument
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,WorkDocument workDocument) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		String _pageIndex = request.getParameter("pageIndex");
		String _pageSize=request.getParameter("pageSize");
		int pageIndex = NumberUtils.toInt(_pageIndex, 1);
		int pageSize=NumberUtils.toInt(_pageSize,10);
		Page page = new Page(pageIndex , pageSize);
		page.setOrder("createDate");
		// ==执行分页查询
		List<WorkDocument> datas=workDocumentService.findListDataByFinder(null,page,WorkDocument.class,workDocument);
			returnObject.setQueryBean(workDocument);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,WorkDocument workDocument) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = workDocumentService.findDataExportExcel(null,listurl, page,WorkDocument.class,workDocument);
		String fileName="workDocument"+GlobalStatic.excelext;
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
		return "/school/workdocument/workdocumentLook";
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
		  WorkDocument workDocument = workDocumentService.findWorkDocumentById(id);
		   returnObject.setData(workDocument);
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
	ReturnDatas saveorupdate(Model model,WorkDocument workDocument,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		
			java.lang.String id =workDocument.getId();
			if(StringUtils.isBlank(id)){
			  workDocument.setId(null);
			}
		
			workDocumentService.saveorupdate(workDocument);
			
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
		return "/school/workdocument/workdocumentCru";
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
				workDocumentService.deleteById(id,WorkDocument.class);
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
			workDocumentService.deleteByIds(ids,WorkDocument.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "/document/upload",method=RequestMethod.POST)
    @ResponseBody
    public boolean uploadHandlerForUploadify(String picHref,HttpServletRequest request)
            throws Exception {
		// 获取相对路径，上传路径
		URL rootPath = request.getSession().getServletContext().getResource("/");//获取项目根路径
		_SYSDICT = request.getSession().getServletContext().getRealPath(_UPLOAD_FILE);
		String userId=(String)request.getSession().getAttribute("userId");
		String userName=(String)request.getSession().getAttribute("account");
		if(StringUtils.isBlank(userId)){
			return false;
		}
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("Filedata");
        /** 写文件前先读出图片原始高宽 **/
        byte[] bytes = multipartFile.getBytes();
        InputStream is = new ByteArrayInputStream(bytes);

        /** 拼成完整的文件保存路径加文件 **/
        String originalFilename = multipartFile.getOriginalFilename(); // 文件全名
       String suffix = StringUtils.substringAfter(originalFilename, "."); // 后缀
       // 文件名转码
       // fileName = Base64.StringToBase64(fileName);
        // fileName = StringUtil.join(fileName, suffix);
        UploadFilePathVO uploadFile = new UploadFilePathVO();
        uploadFile.setRealPath(_SYSDICT+"/"+userId+"/"+originalFilename);
        uploadFile.setRelativePath(_UPLOAD_FILE);
        File file = new File(uploadFile.getRealPath());
        if (!file.exists()) {
        	//文件不存在,则生成文件并保存数据库
			file.mkdirs();
			 WorkDocument doc=new WorkDocument();
		        doc.setUserId(userId);
		        doc.setAuthor(userName);
		        doc.setBak("a16");
		        doc.setCreateDate(new Date());
		        doc.setFtlName(StringUtils.substringBefore(originalFilename, "."));
		        doc.setFtlPath("/"+uploadFile.getRelativePath()+"/"+userId+"/"+originalFilename);
		        doc.setId(null);
		        workDocumentService.save(doc);
		}else{
			//若文件存在则先删除原始文件再生成新文件
			file.delete();
			file.mkdirs();
		}
        multipartFile.transferTo(file); // 转存文件
        return true;
    } 
	@RequestMapping(value ="download/file")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,Model model){
		String realPath=request.getSession().getServletContext().getRealPath(_UPLOAD_FILE);
		String ftlPath=request.getParameter("ftlPath");
		String ftlName="";
		if(StringUtils.isNotBlank(ftlPath)){
			ftlName=StringUtils.substringAfterLast(ftlPath, "/");
		}
		try {
			File file=new File(realPath+"\\"+ftlName);
			downFile(response, file, ftlName, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
