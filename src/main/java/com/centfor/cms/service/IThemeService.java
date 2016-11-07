package com.centfor.cms.service;

import java.util.List;

import com.centfor.cms.entity.CmsTheme;
import com.centfor.system.service.IBaseSuperCMSService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.User
 */
public interface IThemeService extends IBaseSuperCMSService {

   /**
    * 复制公共主题到私人目录，并返回url
    * @param themeId
    * @param siteId
    * @param ostype
    * @return
    * @throws Exception
    */
	String saveCopyTheme(String themeId,String userId,String siteId) throws Exception;
	/**
	 * 查询主题在站点中的url，没有返回null
	 * @param themeId
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	String findThemeUrlInSite(String themeId,String siteId)throws Exception;
	/**
	 * 批量查询  不能超过2000
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<CmsTheme> findByIds(List<String> ids)throws Exception;
	
	String saveTheme(CmsTheme en)throws Exception;
}
