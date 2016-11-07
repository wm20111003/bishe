package com.centfor.system.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.druid.support.json.JSONUtils;
import com.centfor.frame.controller.BaseController;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.frame.util.ZipUtils;

@Controller
@RequestMapping(value = "/upload")
public class UploadTheme extends BaseController {

	@RequestMapping(value = "/theme")
	public @ResponseBody ReturnDatas uploadTheme(
			@RequestParam("uploadfile") MultipartFile uploadimgfile,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		File destFile = null;
		String fn = uploadimgfile.getOriginalFilename();
		String url = request.getParameter("url");
		if (StringUtils.isBlank(url)) {
			url = "/upload/theme/";
		}
		if ((!url.startsWith("/"))) {
			return ReturnDatas.getErrorReturnDatas();
		}

		try {
			String uploadDirPath = System.getProperty("user.dir");
			File dir = new File(uploadDirPath + url);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String ext = fn.substring(fn.lastIndexOf("."));

			String newfilename = new Date().getTime() + "_"
					+ new Random().nextInt(120000);

			String filePath = uploadDirPath + url + newfilename + ext;
			destFile = new File(filePath);

			FileCopyUtils.copy(uploadimgfile.getBytes(), destFile);

			// 解压文件
			File targetDir = new File(uploadDirPath + url + newfilename);
			ZipUtils.unZip(filePath, targetDir.getPath(), true);

			Map<String, String> map = new HashMap<String, String>();
			// 模板路径
			map.put("ftlfile", url + newfilename);
			// 模板缩略图
			map.put("imgfile", "/upload/getimg?url=" + url + newfilename
					+ "/static/images/t.jpg");

			returnObject.setMap(map);

		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		return returnObject;
	}

	@RequestMapping(value = "/image")
	public @ResponseBody ReturnDatas uploadImage(
			@RequestParam("uploadfile") MultipartFile uploadimgfile,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		File destFile = null;
		String fn = uploadimgfile.getOriginalFilename();
		String url = request.getParameter("url");
		if (StringUtils.isBlank(url)) {
			url = "/upload/images/";
		}
		if ((!url.startsWith("/"))) {
			return ReturnDatas.getErrorReturnDatas();
		}

		try {
			String uploadDirPath = System.getProperty("user.dir");
			File dir = new File(uploadDirPath + url);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			try {
				String ext = fn.substring(fn.lastIndexOf("."));
				fn = new Date().getTime() + "_" + new Random().nextInt(120000)
						+ ext;
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}
			String filePath = uploadDirPath + url + fn;
			destFile = new File(filePath);
			FileCopyUtils.copy(uploadimgfile.getBytes(), destFile);

			returnObject.setData("/upload/getimg?url=" + url + fn);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		return returnObject;
	}

	@RequestMapping(value = "/getimg")
	public void getimg(@RequestParam("url") String url,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(url) || (!url.startsWith("/"))) {
			return;
		}
		response.setContentType("image/*");
		String uploadDirPath = System.getProperty("user.dir");
		File file = new File(uploadDirPath + url);
		if(!file.isFile()){
			return; 
		}
		OutputStream output = response.getOutputStream();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		BufferedOutputStream bos = new BufferedOutputStream(output);
		byte data[] = new byte[2048];// 缓冲字节数
		int size = 0;
		size = bis.read(data);
		while (size != -1) {
			bos.write(data, 0, size);
			size = bis.read(data);
		}
		bis.close();
		bos.flush();
		bos.close();
		output.close();
	}
	
	/**
	 * 列取文件的东西
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/file_json_manager")
	public void file_json_manager(HttpServletRequest request,HttpServletResponse response)throws Exception{
		/**
		 * KindEditor JSP
		 *
		 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
		 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
		 *
		 */
		
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath =request.getSession().getServletContext().getRealPath("/uploads")+"/images/"; 
				//pageContext.getServletContext().getRealPath("/") + "attached/";
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = request.getContextPath() + "/uploads/images/"; 
		
		//图片扩展名
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		//错误信息收集
		String tips="";
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		/*
		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
				tips+="{'message':'错误的文件夹名!'}";
				out.println(tips);
				
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}*/
		//根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String type=request.getParameter("type");
		if(StringUtils.isNotBlank(type)){
			rootPath += type + "/";
			rootUrl += type + "/";
		}
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			tips+="{'message':'错误文件路径书写方式!'}";
			out.println(tips);
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			tips+="{'message':'最后一个字符不是斜杠！'}";
			out.println(tips);
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			tips+="{'message':'目录不存在或者不是目录！'}";
			out.println(tips);
		}

		//遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

//		if ("size".equals(order)) {
//			Collections.sort(fileList, new SizeComparator());
//		} else if ("type".equals(order)) {
//			Collections.sort(fileList, new TypeComparator());
//		} else {
//			Collections.sort(fileList, new NameComparator());
//		}
		Map map=new HashMap();
		map.put("moveup_dir_path", moveupDirPath);
		map.put("current_dir_path", currentDirPath);
		map.put("current_url", currentUrl);
		map.put("total_count", fileList.size());
		map.put("file_list", fileList);
		out.println(JSONUtils.toJSONString(map));
	}
	/**
	 * 上传文件
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/upload_json")
	public void keditupload(HttpServletResponse response,MultipartHttpServletRequest request)throws Exception{
		//文件保存目录路径
		String savePath = request.getSession().getServletContext().getRealPath("/uploads") + "/images/";
		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/uploads/images/"; 
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		//extMap.put("flash", "swf,flv");
		//extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		//extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		//最大文件大小
		long maxSize = 10240*1024;//1M 
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		if(!ServletFileUpload.isMultipartContent(request)){
			out.println(getError("请选择文件。"));
			return;
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			out.println(getError("上传目录不存在。"));
			return;
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			out.println(getError("上传目录没有写权限。"));
			return;
		}
		/*
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		
		if(!extMap.containsKey(dirName)){
			out.println(getError("目录名不正确。"));
			return;
		}
		*/
		//创建文件夹
		//savePath += dirName + "/";
		//saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		String _path=request.getParameter("type");
		if(StringUtils.isNotBlank(_path)){
			savePath+=_path+"/";
			saveUrl += _path + "/";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setHeaderEncoding("UTF-8");
		
		//List items = upload.parseRequest(request);
		Map<String, MultipartFile> fileMap = request.getFileMap();
		Iterator itr =request.getFileNames(); 
				//items.iterator();
		while (itr.hasNext()) {
			MultipartFile multipartFile = fileMap.get(itr.next());
			//FileItem item = (FileItem) itr.next();
			String fileName = multipartFile.getOriginalFilename();
			long fileSize = multipartFile.getSize();
			if (!multipartFile.isEmpty()) {
				//检查文件大小
				if(fileSize > maxSize){
					out.println(getError("上传文件大小"+(double)((int)((fileSize/1048576.0)*100)/100.00)+"M,超过限制。"));
					return;
				}
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!Arrays.<String>asList(extMap.get("image").split(",")).contains(fileExt)){
					out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("image") + "格式。"));
					return;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try{
					File uploadedFile = new File(savePath, newFileName);
					FileCopyUtils.copy(multipartFile.getBytes(), uploadedFile);
				}catch(Exception e){
					out.println(getError("上传文件失败。"));
					return;
				}
				Map obj=new HashMap();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				out.println(JSONUtils.toJSONString(obj));
			}
		}
		
	} 
	private String getError(String message) {
		Map obj=new HashMap();
		obj.put("error", 1);
		obj.put("message", message);
		return JSONUtils.toJSONString(obj);
	}

}
