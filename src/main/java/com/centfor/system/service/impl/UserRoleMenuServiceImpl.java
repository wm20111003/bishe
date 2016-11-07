package com.centfor.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.system.entity.Menu;
import com.centfor.system.entity.Role;
import com.centfor.system.entity.RoleMenu;
import com.centfor.system.entity.User;
import com.centfor.system.entity.UserRole;
import com.centfor.system.service.BaseSuperCMSServiceImpl;
import com.centfor.system.service.IUserRoleMenuService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.UserRole
 */
@Service("userRoleMenuService")
public class UserRoleMenuServiceImpl extends BaseSuperCMSServiceImpl implements
		IUserRoleMenuService {
	@Resource
	private CacheManager shiroCacheManager;

	@Override
	@Cacheable(value = GlobalStatic.qxCacheKey, key = "'findRoleByUserId_'+#userId")
	public List<Role> findRoleByUserId(String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		Finder finder = new Finder(
				"SELECT r.* from ").append(Finder.getTableName(Role.class)).append(" r,").append(Finder.getTableName(UserRole.class)).append("  re where re.userId=:userId and re.roleId=r.id order by r.id");
		finder.setParam("userId", userId);
		return super.queryForList(finder, Role.class);
	}
	@Override
	//@Cacheable(value = GlobalStatic.qxCacheKey, key = "'getRolesAsString_'+#userId")
	public Set<String> getRolesAsString(String userId)throws Exception {
		List<Role> list = findRoleByUserId(userId);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		Set<String> set=new HashSet<String>();
		for(Role r:list){
			set.add(r.getCode());
		}
		return set;
	}
	@Override
	//@Cacheable(value = GlobalStatic.qxCacheKey, key = "'getPermissionsAsString_'+#userId")
	public  Set<String> getPermissionsAsString(String userId) throws Exception {
		List<Menu> setMenu = findAllMenuByUserId(userId);
		if(CollectionUtils.isEmpty(setMenu)){
			return null;
		}
		Set<String> set=new HashSet<String>();
		for(Menu m:setMenu){
			if(StringUtils.isBlank(m.getPageurl())){
				continue;
			}
			set.add(m.getPageurl());
		}
		return set;
	}

	@Override
	@Cacheable(value = GlobalStatic.qxCacheKey, key = "'findMenuByRoleId_'+#roleId")
	public List<Menu> findMenuByRoleId(String roleId) throws Exception {
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		Finder finder = new Finder(
				"SELECT m.* from ").append(Finder.getTableName(Menu.class)).append(" m,").append(Finder.getTableName(RoleMenu.class)).append("  re where re.roleId=:roleId and re.menuId=m.id order by m.id ");
		finder.setParam("roleId", roleId);
		return super.queryForList(finder, Menu.class);
	}

	@Override
	@Cacheable(value = GlobalStatic.qxCacheKey, key = "'findUserByRoleId_'+#roleId")
	public List<User> findUserByRoleId(String roleId) throws Exception {
		if (StringUtils.isBlank(roleId)) {
			return null;
		}

		Finder finder = new Finder(
				"SELECT u.* from ").append(Finder.getTableName(User.class)).append(" u,").append(Finder.getTableName(UserRole.class)).append("  re where re.roleId=:roleId and re.userId=u.id");
		finder.setParam("roleId", roleId);
		return super.queryForList(finder, User.class);
	}

	@Override

	public List<Menu> findMenuByUserId(String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		
		Finder finder=getMenuFinderByUserId(userId, 1);
	
		return super.queryForList(finder, Menu.class);
	}
	
	private Finder getMenuFinderByUserId(String userId,Integer menutype) throws Exception{
		
		Finder finder = new Finder(
				"SELECT m.* from ").append(Finder.getTableName(Menu.class)).append(" m,").append(Finder.getTableName(RoleMenu.class)).append("  rm,").append(Finder.getTableName(UserRole.class)).append("  ur where ur.userId=:userId and ur.roleId=rm.roleId and m.id=rm.menuId  and m.state=:state ");
		if(menutype!=null){
			finder.append(" and m.type=:menutype ").setParam("menutype", menutype);
		}
		finder.append(" order by m.sort asc ");
		finder.setParam("userId", userId).setParam("state", "是");
		
		return finder;
		
	}
	
	
	
	
	public List<Menu> findAllMenuByUserId(String userId) throws Exception {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		
		Finder finder=getMenuFinderByUserId(userId, null);
		return super.queryForList(finder, Menu.class);
	}
	@Override
	@Cacheable(value = GlobalStatic.qxCacheKey, key = "'findRoleAndMenu_'+#roleId")
	public Role findRoleAndMenu(String roleId) throws Exception {
		if (StringUtils.isBlank(roleId)) {
			return null;
		}
		Role role = super.findById(roleId, Role.class);
		if (role == null) {
			return null;
		}
		List<Menu> menus = findMenuByRoleId(roleId);
		role.setMenus(menus);
		return role;
	}



	@Override
	public User findLoginUser(String account, String password) throws Exception {
		if (StringUtils.isBlank(account)) {
			return null;
		}
		//Finder finder = new Finder("SELECT * FROM t_user WHERE  account=:account ");
		Finder finder = Finder.getSelectFinder(User.class).append(" WHERE  account=:account ");
		finder.setParam("account", account);
		if (StringUtils.isNotBlank(password)) {
			finder.append(" and password=:password ").setParam("password",
					password);
		}
		return super.queryForObject(finder, User.class);
	}

	@Override
	@Cacheable(value = GlobalStatic.qxCacheKey, key = "'findAllRoleAndMenu'")
	public List<Role> findAllRoleAndMenu() throws Exception {
		//Finder f_role = new Finder("SELECT * FROM t_role where state=:state ");
		Finder f_role = Finder.getSelectFinder(Role.class).append(" WHERE   state=:state ");
		f_role.setParam("state", "是");
		List<Role> listRole = super.queryForList(f_role, Role.class);
		if (CollectionUtils.isEmpty(listRole)) {
			return null;
		}
		for (Role r : listRole) {
			List<Menu> menus = findMenuByRoleId(r.getId());
			r.setMenus(menus);
		}
		return listRole;
	}

	@Override
	public Menu findMenuAndLeaf(String menuId) throws Exception {
		if (StringUtils.isBlank(menuId)) {
			return null;
		}
		Menu menu = super.findById(menuId, Menu.class);
		addLeafMenu(menu);
		return menu;
	}
	
	
	private Menu addLeafMenu(Menu menu) throws Exception{
		if(menu==null){
			return null;
		}
		String id=menu.getId();
		if(StringUtils.isBlank(id)){
			return null;
		}
		
		//Finder finder=new Finder("SELECT * FROM t_menu where pid=:pid ");
		Finder finder = Finder.getSelectFinder(Menu.class).append(" WHERE  pid=:pid");
		finder.setParam("pid", id);
		  List<Menu> list = super.queryForList(finder,Menu.class);
		if(CollectionUtils.isEmpty(list)){
			return menu;
		}
		menu.setLeaf(list);
		
		for(Menu m:list){
			addLeafMenu(m);
		}
		
		return menu;
	}
	
	@Override
	//@Caching(evict={@CacheEvict(value = GlobalStatic.qxCacheKey,key = "'findMenuByRoleId_'+#roleId"),@CacheEvict(value = GlobalStatic.qxCacheKey,key = "'findRoleAndMenu_'+#roleId"),@CacheEvict(value = GlobalStatic.qxCacheKey,key = "'findAllRoleAndMenu'")})
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public void updateRoleMenu(String roleId,String[] menus) throws Exception {
		
		shiroCacheManager.getCache(GlobalStatic.authorizationCacheName).clear();

		        Finder finder=Finder.getDeleteFinder(RoleMenu.class).append("  where roleId=:roleId ");
				finder.setParam("roleId", roleId);
				this.update(finder);
				if(menus==null||menus.length<1){
					return ;
				}

				List<RoleMenu> list=new ArrayList<RoleMenu>();
				for(String menuId:menus){
					RoleMenu rm=new RoleMenu();
					rm.setRoleId(roleId);
					rm.setMenuId(menuId);
					list.add(rm);
				}
				
				if(!CollectionUtils.isEmpty(list)){
					super.save(list);
				}
				
				
				
	}
	@Override
	@Cacheable(value = GlobalStatic.qxCacheKey, key = "'findMenuAndLeafByUserId'+#userId")
	public List<Menu> findMenuAndLeafByUserId(String userId) throws Exception {
		List<Menu> list=findMenuByUserId(userId);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<Menu> wrapList=new ArrayList<Menu>();
		diguiwrapList(list, wrapList, null);
		
		return wrapList;
	}
	
	private List<Menu> diguiwrapList(List<Menu> from,List<Menu> tolist,String parentId){
		if(CollectionUtils.isEmpty(from)){
			return null;
		}
		
		for(int i=0;i<from.size();i++){
			Menu m=from.get(i);
			if(m==null||(m.getType()-0==0)){
				//from.remove(i);
			//	i=i-1;
				continue;
			}
		
			String pid=m.getPid();
			if((pid==null)&&(parentId!=null)){
				continue;
			}
		
			if((parentId==m.getPid())||(m.getPid().equals(parentId))){
				List<Menu> leaf=new ArrayList<Menu>();
				m.setLeaf(leaf);
				tolist.add(m);
				//from.remove(i);
			//	i=i-1;
			  diguiwrapList(from, leaf, m.getId());
				if(CollectionUtils.isEmpty(leaf)){
					continue;
				}
				
			}
			
			
		}
		
		return tolist;

	}
	
	
	

}
