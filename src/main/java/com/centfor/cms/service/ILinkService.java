package com.centfor.cms.service;

import java.util.List;

import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.IBaseSuperCMSService;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see com.centfor.bbz.service.CmsSite
 */
public interface ILinkService extends IBaseSuperCMSService {
	/**
	 * 删除Link bussinessId 为空直接返回 ostype可以为空
	 * @param bussinessId
	 * @param ostype
	 * @throws Exception
	 */
    void deleteLink(String bussinessId,String ostype)throws Exception;
    /**
     * 查询业务下的主题id
     * @param bussinessId
     * @return
     * @throws Exception
     */
    List<String> findLinkThemeIds(String bussinessId)throws Exception;
    /**
     * 查询业务下子业务的主题id
     * @param bussinessId
     * @return
     * @throws Exception
     */
    List<String> findNodeLinkThemeIds(String bussinessId)throws Exception;
   
    /**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 全部查询（state=0,1）
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
    <T> List<T> findAllListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception;
    /**
     * 查询站点下需要登录的businessId
     * @param siteId
     * @return
     * @throws Exception
     */
	List<String> findLoginReqBusiness()throws Exception;
}
