package com.centfor.system.service;

import java.util.List;

import com.centfor.system.entity.Org;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:02:58
 * @see com.centfor.bbz.service.Org
 */
public interface IOrgService extends IBaseSuperCMSService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveOrg(Org entity) throws Exception;
	/**
	 * 修改或者保存,根据id是否为空判断
	 * @param entity
	 * @return
	 * @throws Exception
	 */
    String saveorupdateOrg(Org entity) throws Exception;
	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateOrg(Org entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Org findOrgById(Object id) throws Exception;
	
	/**
	 * 查找Org的树形结构
	 * @return
	 * @throws Exception
	 */
	List<Org> findTreeOrg()throws Exception;
	
	
}
