package com.centfor.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.entity.Menu;
import com.centfor.system.service.BaseSuperCMSServiceImpl;
import com.centfor.system.service.IMenuService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:02:58
 * @see com.centfor.bbz.service.impl.Menu
 */
@Service("menuService")
public class MenuServiceImpl extends BaseSuperCMSServiceImpl implements
		IMenuService {

	@Override
	public String saveMenu(Menu entity) throws Exception {
		return super.save(entity).toString();
	}

	@Override
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public String saveorupdateMenu(Menu entity) throws Exception {
		return super.saveorupdate(entity).toString();
	}

	@Override
	public Integer updateMenu(Menu entity) throws Exception {
		return super.update(entity);
	}

	@Override
	public Menu findMenuById(Object id) throws Exception {
		return super.findById(id, Menu.class);
	}
	
	
	public List<Menu> findListById(Object id) throws Exception {
		List<Menu> menuList=new ArrayList<Menu>();
		Finder finder = Finder.getSelectFinder(Menu.class);
	
		if(id==null || StringUtils.isBlank(id.toString())){
			finder.append(" where pid is :pid");
		}else{
			finder.append(" where pid=:pid ");
		}
		finder.setParam("pid", id);
		List<Menu> list = super.queryForList(finder,Menu.class);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		for(Menu m:list){
			menuList.add(addLeafMenu(m));
		}
		return menuList;
	}

	private Menu addLeafMenu(Menu menu) throws Exception{
		if(menu==null){
			return null;
		}
		String id=menu.getId();
		if(StringUtils.isBlank(id)){
			return null;
		}
		
		Finder finder = Finder.getSelectFinder(Menu.class).append(" where pid=:pid ");
		finder.setParam("pid", id);
		List<Menu> list = super.queryForList(finder,Menu.class);
		if(CollectionUtils.isEmpty(list)){
			return menu;
		}
		menu.setLeaf(list);
		return menu;
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
		finder=Finder.getSelectFinder(Menu.class).append(" WHERE 1=1  order by sort asc ");
		return super.queryForList(finder, clazz);
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * 
	 * @param finder
	 *            为空则只查询 clazz表
	 * @param ftlurl
	 *            类表的模版宏
	 * @param page
	 *            分页对象
	 * @param clazz
	 *            要查询的对象
	 * @param o
	 *            querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl,
			Page page, Class<T> clazz, Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

	@Override
	@Cacheable(value = GlobalStatic.cacheKey, key = "'getNameByPageurl_'+#pageurl")
	public String getNameByPageurl(String pageurl) throws Exception {
		if(StringUtils.isBlank(pageurl)){
			return null;
		}
		Finder finder = Finder.getSelectFinder(Menu.class,"name").append(" WHERE pageurl=:pageurl ");
		finder.setParam("pageurl", pageurl);
		List<String> list = queryForList(finder,String.class);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		return list.toString();
	}
	
	
	/**
	 * 根据菜单id删除操作
	 * 先删除t_role_menuId数据
	 * 再删除t_menu数据，所有与此id相关联的全部删除
	 * @param menuId
	 * @throws Exception
	 */
	@Override
	public void deleteMenuRoleByMenuId(String menuId) throws Exception {
		if(StringUtils.isNotBlank(menuId)){
			Finder finder = new Finder("delete from t_role_menu where menuId=:menuId").setParam("menuId", menuId);
			Menu menu = super.findById(menuId, Menu.class);
			if(menu != null){
				super.update(finder);
				super.deleteByEntity(menu);
				List<Menu> list = new ArrayList<Menu>();
				Menu menu2 = new Menu();
				menu2.setPid(menuId);
				list=super.findListDataByFinder(null, null, Menu.class, menu2);
				if(CollectionUtils.isNotEmpty(list)){
					for(Menu me:list){
						deleteMenuRoleByMenuId(me.getId());
					}
				}
				
			}
		}
	}


}
