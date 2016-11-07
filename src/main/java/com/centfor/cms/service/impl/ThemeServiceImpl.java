package com.centfor.cms.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsLink;
import com.centfor.cms.entity.CmsReThemeGroup;
import com.centfor.cms.entity.CmsTheme;
import com.centfor.cms.service.ICmsTableindexService;
import com.centfor.cms.service.IThemeService;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.entity.User;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.User
 */
@Service("themeService")
public class ThemeServiceImpl extends BaseSuperCMSServiceImpl implements
		IThemeService {
	@Resource
	private ICmsTableindexService cmsTableindexService;
	
	@Override
	public String saveTheme(CmsTheme en) throws Exception {
		en.setId(cmsTableindexService.saveNewId(CmsTheme.class));
		String id=super.save(en).toString();  
		return id;
	}

	@Override
	public String findThemeUrlInSite(String themeId, String siteId)
			throws Exception {
		Finder finder = new Finder(" select ftlfile from  "
				+ Finder.getTableName(CmsLink.class)
				+ " where ftlId=:themeId and siteid=:siteid group by ftlfile");
		finder.setParam("themeId", themeId).setParam("siteid", siteId);
		String ftlfile = super.queryForObject(finder, String.class);
		return ftlfile;
	}

	@Override
	public String saveCopyTheme(String themeId, String userId, String siteId)
			throws Exception {
		User user = super.findById(userId, User.class);
		CmsTheme theme = super.findById(themeId, CmsTheme.class);
		String rootpath = System.getProperty("user.dir");
		String staticdir = rootpath + "/src/main/webapp" + "/userstatic/";
		String pagedir = rootpath + "/src/main/webapp"
				+ "/WEB-INF/freemarker/userpage/";
		// 用户站点目录
		String usersitepath = user.getAccount() + "/" + siteId + "/"+themeId;
		File userstaticdir = new File(staticdir + usersitepath); 
		File userpagedir = new File(pagedir + usersitepath); 
		//公共目录静态
		File commonstaticdir = new File(staticdir+user.getAccount() + "/" + siteId + "/common"); 
		//公共目录页面
		File commonpagedir = new File(pagedir+user.getAccount() + "/" + siteId +"/common"); 
		

		// 为用户站点新建文件夹
		if (!(userstaticdir.isDirectory())) { 
			userstaticdir.mkdir();
		}
		if (!(userpagedir.isDirectory())) {
			userpagedir.mkdir();
		}
		//公共
		if (!(commonstaticdir.isDirectory())) { 
			commonstaticdir.mkdir();  
		}
		if (!(commonpagedir.isDirectory())) { 
			commonpagedir.mkdir();  
		}
		//上传模板路径
		String ftlfile = theme.getFtlfile();
		String srcstatic = rootpath + ftlfile + "/static/";
		String srcpage = rootpath + ftlfile + "/page/";
		String commonstatic = rootpath + ftlfile + "/common/static/";
		String commonpage = rootpath + ftlfile + "/common/page/";
		//拷贝公共样式到common
		File commonstaticFile=new File(commonstatic);  
		if (commonstaticFile.isDirectory()) { 
			FileUtils.copyDirectory(commonstaticFile, commonstaticdir);    
		}
		//拷贝公共页面到common
		File commonpageFile=new File(commonpage);  
		if (commonpageFile.isDirectory()) { 
			FileUtils.copyDirectory(commonpageFile,commonpagedir);    
		}
		// 拷贝主题到用户的私有目录
		FileUtils.copyDirectory(new File(srcstatic), userstaticdir);
		FileUtils.copyDirectory(new File(srcpage), userpagedir); 
		return "/userpage/" + usersitepath + "/t";
	}
	

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		CmsTheme qb = (CmsTheme) o;
		finder = Finder.getSelectFinder(CmsTheme.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, qb);
		super.getFinderOrderBy(finder, page);

		if (qb != null) {
			if (StringUtils.isNotBlank(qb.getQgroup())) {
				String midtab = Finder.getTableName(CmsReThemeGroup.class);
				finder.append(" and id in (select themeId from " + midtab
						+ " where themeGroupId=:qgroup ) ");
				finder.setParam("qgroup", qb.getQgroup());
			}
		}

		List<T> queryForList = super.queryForList(finder, clazz, page);

		return queryForList;
	}

	@Override
	public List<CmsTheme> findByIds(List<String> ids) throws Exception {
		if(CollectionUtils.isEmpty(ids)){
			return null;
		}
		Finder finder = Finder.getSelectFinder(CmsTheme.class)
				.append(" where state=1 and id in (:ids) ")
				.setParam("ids", ids);
		return super.queryForList(finder, CmsTheme.class);
	}

}
