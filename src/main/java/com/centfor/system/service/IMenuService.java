package com.centfor.system.service;

import java.util.List;

import com.centfor.system.entity.Menu;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:02:58
 * @see com.centfor.bbz.service.Menu
 */
public interface IMenuService extends IBaseSuperCMSService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveMenu(Menu entity) throws Exception;
	/**
	 * 修改或者保存,根据id是否为空判断
	 * @param entity
	 * @return
	 * @throws Exception
	 */
    String saveorupdateMenu(Menu entity) throws Exception;
	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateMenu(Menu entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Menu findMenuById(Object id) throws Exception;
	
	
	/**
	 * 
	 * @Title: findListById
	 * @Description: 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 * @return List<Menu>
	 * @throws
	 */
	List<Menu> findListById(Object id) throws Exception;
	/**
	 * 根据pageurl查询菜单名称
	 * @param pageurl
	 * @return
	 * @throws Exception
	 */
	String getNameByPageurl(String pageurl) throws Exception;
	
	/**
	 * 根据菜单id删除操作
	 * 先删除t_role_menuId数据
	 * 再删除t_menu数据，所有与此id相关联的全部删除
	 * @param menuId
	 * @throws Exception
	 */
	void deleteMenuRoleByMenuId(String menuId) throws Exception;
}
