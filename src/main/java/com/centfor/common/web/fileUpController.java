package com.centfor.common.web;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.ExcelUtil;
import com.centfor.frame.util.PropertiesUtils;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.school.entity.WorkCourse;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version 2013-07-29 11:36:46
 * @see com.centfor.bbz.web.Channel
 */
@Controller
@RequestMapping(value = "/common/upFile")
public class fileUpController extends BaseController {
	private static String _UPLOAD_FILE = "";
	private String _SYSDICT = "";
	private static String serverUrl=null;

	private File file;
	static {
		PropertiesUtils pu = new PropertiesUtils("db");
		_UPLOAD_FILE = pu.getString("upload.file");
		
		/*CerpTabMessage tab = new CerpTabMessage("chat");
		serverUrl = tab.getString("serverUrl")+"/"+_UPLOAD_FILE;*/
	}

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 * @param temp123
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadImage")
	public @ResponseBody
	ReturnDatas uploadImage(HttpServletRequest request,
			HttpServletResponse response, MultipartFile _fileUpload)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		if (StringUtils.isBlank(_SYSDICT)) {
			_SYSDICT = request.getSession().getServletContext()
					.getRealPath(_UPLOAD_FILE);
			// 获取当前登录人
			String userId = SessionUser.getShiroUser().getUser().getId();
			_SYSDICT = _SYSDICT + "/" + userId;
			//serverUrl=serverUrl+"/"+userId;
			// 如果文件夹不存在，创建文件夹（每一个用户一个）
			file = new File(_SYSDICT);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		request.getParameter("_fileUpload");
		ExcelUtil sd =new ExcelUtil();
		/** 获取文件后缀 **/
		String suffix = "";
		String originalFilename = _fileUpload.getOriginalFilename();
		if (StringUtils.isNotBlank(originalFilename)) {
			String[] arr = StringUtils.split(originalFilename, ".");
			if (arr != null && arr.length > 1) {
				suffix = arr[arr.length - 1].trim().toLowerCase();
			}
		}
		if (StringUtils.isBlank(suffix)) {
			suffix = "apk";
		}
		/** 复制文件到上传文件 **/
		/*String newName = String.valueOf(new Date().getTime()) + "." + suffix;*/
		FileUtils.copyInputStreamToFile(_fileUpload.getInputStream(), new File(
				_SYSDICT, originalFilename));
		File file = new File(_SYSDICT + "/" + originalFilename);
		FileInputStream fs = new FileInputStream(file);
		sd.read2003ExcelJXL(fs, 6);
		WorkCourse course=new WorkCourse();
		course.setFtlName(StringUtils.substringBefore(originalFilename, "."));
		course.setFtlPath(_UPLOAD_FILE+"/"+SessionUser.getShiroUser().getUser().getId()+"/"+originalFilename);
		returnObject.setData(course);
		returnObject.setStatus(ReturnDatas.SUCCESS);
		return returnObject;
	}
}
