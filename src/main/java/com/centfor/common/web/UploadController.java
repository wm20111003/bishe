package com.centfor.common.web;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.centfor.common.dto.FileDto;
import com.centfor.frame.common.SessionUser;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.ImageUtils;
import com.centfor.frame.util.PropertiesUtils;
import com.mysql.fabric.xmlrpc.base.Struct;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version 2013-07-29 11:36:46
 * @see com.centfor.bbz.web.Channel
 */
@Controller
@RequestMapping(value = "/common/upload")
public class UploadController extends BaseController {
	private ObjectMapper objectMapper = null;
	private static String _UPLOAD_FILE = "";
	private String _SYSDICT = "";
	private String _dircUrl = "/images/folder-64.gif";
	private static String _PROJECTNAME = "";

	private String _path = "";

	private File file;
	static {
		PropertiesUtils pu = new PropertiesUtils("db");
		_UPLOAD_FILE = pu.getString("upload.file");
	}

	/** binder用于bean属性的设置 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
	}

	@ModelAttribute("")
	public void init(HttpServletRequest request) throws Exception {
		// 获取相对路径，上传路径
			_SYSDICT = request.getSession().getServletContext()
					.getRealPath(_UPLOAD_FILE);
			// 获取当前登录人
			String userId = SessionUser.getUserId();
			_SYSDICT = _SYSDICT + "/" + userId;
			// 如果文件夹不存在，创建文件夹（每一个用户一个）
			file = new File(_SYSDICT);
			if (!file.exists()) {
				file.mkdirs();
			}
		if (StringUtils.isBlank(_PROJECTNAME)) {
			_PROJECTNAME = request.getServletContext().getContextPath();
		}
		if (file == null) {
			file = new File(_SYSDICT);
		}

		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		if (StringUtils.isBlank(_path)) {
			_path = _UPLOAD_FILE + "/" + SessionUser.getUserId();
			if (_path.startsWith("/")) {
				_path = _path.substring(1);
			}
		}
	}

	/**
	 * 列出所有文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findDirc")
	public void findDirc(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 显示方式
		String showType = request.getParameter("showType");
		// 排序方式
		String orderBy = request.getParameter("orderBy");
		// 获取文件路径
		String dircPath = request.getParameter("dircPath");

		dircPath = StringUtils.replace(dircPath, "\\", "/");
		String imgUrl = "";
		if (StringUtils.isBlank(dircPath)) {
			imgUrl = _UPLOAD_FILE + "/" + SessionUser.getUserId();
		} else {
			// StringUtils.replace(dircPath, "//", "/");
			String[] ttt = dircPath.split(_path);
			if (ttt.length > 1) {
				if (ttt[1].startsWith("/")) {
					imgUrl = _path + ttt[1];
				} else {
					imgUrl = _path + "/" + ttt[1];
				}
			} else {
				dircPath = _SYSDICT;
				imgUrl = _UPLOAD_FILE + "/" + SessionUser.getUserId();
			}
		}
		// imgUrl = StringUtils.replace(imgUrl, "//", "/");
		// 列出所有文件
		Map<String, Object> map = new HashMap<String, Object>();
		File[] files = null;
		if (StringUtils.isBlank(dircPath)) {
			files = file.listFiles();
			map.put("parentName", "");

			map.put("currName", "");
		} else {

			File dirc = new File(dircPath);
			files = dirc.listFiles();
			map.put("parentName", StringUtils.replace(dirc.getParentFile()
					.getPath(), "\\", "/"));
			map.put("currName", StringUtils.replace(dirc.getPath(), "\\", "/"));

			/*
			 * map.put("parentName", StringUtils.replace(dirc.getParentFile()
			 * .getPath(), "//", "/")); map.put("currName",
			 * StringUtils.replace(dirc.getPath(), "//", "/"));
			 */
		}

		List<FileDto> fileDtos = new ArrayList<FileDto>();
		if (files != null) {
			for (File f : files) {
				FileDto fd = new FileDto();
				fd.setFileName(f.getName());
				Long t = f.lastModified();
				Date createDate = new Date(t);
				fd.setCreateDate(createDate);
				if (f.isDirectory()) {
					fd.setIsDir(1);
					fd.setImgUrl(_dircUrl);
				} else {
					fd.setIsDir(0);
					fd.setImgUrl(imgUrl + "/" + fd.getFileName());
				}
				fd.setAbsolutePath(f.getAbsolutePath());
				fd.setPath(f.getPath());
				fd.setParentName(dircPath);
				fd.setPath(StringUtils.replace(f.getPath(), "\\", "/"));
				/*
				 * fd.setAbsolutePath(StringUtils.replace(fd.getAbsolutePath(),
				 * "\\", "/"));
				 */
				/*//获取文件名
				 String str=fd.getFileName(); 
				 //如果文件名中不包含字符则加入到显示列表中。
				 if(str.indexOf("_small")==-1){ 
					 fileDtos.add(fd);
				 }*/
			}
		}

		map.put("showType", showType);
		map.put("orderBy", orderBy);
		map.put("files", sortBy(orderBy, fileDtos));

		String p = objectMapper.writeValueAsString(map);
		sendJson(response, p);
	}

	/**
	 * 创建文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/removeDirc")
	public void removeDirc(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取路径
		String dircPath = request.getParameter("dircPath");
		if (dircPath != null) {
			File file = new File(dircPath);
			file.delete();
		}
		sendJson(response, "删除成功");
	}

	/**
	 * 创建文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/createDirc")
	public void createDirc(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取路径
		String dircPath = request.getParameter("dircPath");
		// 获取文件名
		String dircName = request.getParameter("dircName");
		if (StringUtils.isNotBlank(dircPath)) {
		} else {
			dircPath = _SYSDICT;
		}

		dircPath = dircPath + "/" + dircName;

		File file = new File(dircPath);

		if (!file.exists()) {
			file.mkdir();
			sendJson(response, "");
		} else {
			sendJson(response, "目录已经存在");
		}
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
	public void uploadImage(HttpServletRequest request,
			HttpServletResponse response, MultipartFile _fileUpload)
			throws Exception {
		// 获取路径
		String dircPath = request.getParameter("dircPath");
		String imgUrl = "";
		if (StringUtils.isBlank(dircPath)) {
			imgUrl = _UPLOAD_FILE + "/" + SessionUser.getUserId();
		} else {
			// StringUtils.replace(dircPath, "//", "/");
			String[] ttt = dircPath.split(_path);
			if (ttt.length > 1) {
				if (ttt[1].startsWith("/")) {
					imgUrl = _path + ttt[1];
				} else {
					imgUrl = _path + "/" + ttt[1];
				}
			} else {
				dircPath = _SYSDICT;
				imgUrl = _UPLOAD_FILE + "/" + SessionUser.getUserId();
			}
		}
		if (StringUtils.isBlank(dircPath)) {
			dircPath = _SYSDICT;
		}
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
			suffix = "jpg";
		}
		
		/** 复制文件到上传文件 **/
		String newName = String.valueOf(new Date().getTime()) + "." + suffix;
		FileUtils.copyInputStreamToFile(_fileUpload.getInputStream(), new File(
				dircPath, newName));
		File f = new File(dircPath + "/" + newName);
		FileDto fd = new FileDto();
		fd.setFileName(f.getName());
		Long t = f.lastModified();
		Date createDate = new Date(t);
		fd.setCreateDate(createDate);
		if (f.isDirectory()) {
			fd.setIsDir(1);
			fd.setImgUrl(_dircUrl);
		} else {
			fd.setIsDir(0);
			fd.setImgUrl(imgUrl + "/" + fd.getFileName());
		}
		fd.setAbsolutePath(f.getAbsolutePath());
		fd.setPath(f.getPath());
		fd.setParentName(dircPath);
		fd.setPath(StringUtils.replace(f.getPath(), "\\", "/"));
		fd.setAbsolutePath(StringUtils.replace(fd.getAbsolutePath(), "\\", "/"));
		
		//获取源文件路径 --设置宽高
		//缩放 ---定义新的文件命名规则
	/*	String sltName = String.valueOf(new Date().getTime()) + "_small." + suffix;
		String dest=dircPath+"/"+sltName;
		ImageUtils.resize(fd.getPath(), dest, 300, 300, false);*/
		
		// 列出所有文件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imgUrl", fd.getImgUrl());
		JSONObject j = new JSONObject();
		j.put("imgUrl", fd.getImgUrl());
		// String p = objectMapper.writeValueAsString(map);
		sendJson(response, j.toString());
	}

	public void sendJson(HttpServletResponse response, String json) {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(json);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			out.flush();
			out.close();
		}
	}

	@RequestMapping("/uploadDemo")
	public String test(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/demo/uploadDemo";
	}

	private List<FileDto> sortBy(String sortBy, List<FileDto> dtos) {
		return dtos;
	}
}
