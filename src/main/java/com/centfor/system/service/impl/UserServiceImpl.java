package com.centfor.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.MessageUtils;
import com.centfor.frame.util.Page;
import com.centfor.frame.util.ReturnDatas;
import com.centfor.system.entity.Org;
import com.centfor.system.entity.Role;
import com.centfor.system.entity.User;
import com.centfor.system.entity.UserOrg;
import com.centfor.system.entity.UserRole;
import com.centfor.system.service.BaseSuperCMSServiceImpl;
import com.centfor.system.service.IUserOrgService;
import com.centfor.system.service.IUserRoleMenuService;
import com.centfor.system.service.IUserService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.User
 */
@Service("userService")
public class UserServiceImpl extends BaseSuperCMSServiceImpl implements
		IUserService {

	@Resource
	private IUserOrgService userOrgService;

	@Resource
	private IUserRoleMenuService userRoleMenuService;

	@Override
	public String saveUser(User entity) throws Exception {
		String id = super.save(entity).toString();
		updateUserInfo(entity);
		return id;
	}

	@Override
	@CacheEvict(value = GlobalStatic.qxCacheKey, allEntries = true)
	public Integer updateUser(User entity) throws Exception {
		Integer update = super.update(entity, true);
		updateUserInfo(entity);
		return update;
	}

	private void updateUserInfo(User user) throws Exception {
		String userId = user.getId();
		Finder f_del = Finder.getDeleteFinder(UserOrg.class).append(
				" WHERE userId=:userId ");
		f_del.setParam("userId", userId);
		super.update(f_del);

		Finder f_del_role = Finder.getDeleteFinder(UserRole.class).append(
				" WHERE userId=:userId ");
		f_del_role.setParam("userId", userId);
		super.update(f_del_role);

		List<Org> list = user.getUserOrgs();
		List<UserOrg> listuo = new ArrayList<UserOrg>();
		if (list != null) {
			for (Org org : list) {
				UserOrg uo = new UserOrg();
				uo.setUserId(userId);
				uo.setOrgId(org.getId());
				listuo.add(uo);
			}
		}
		if (CollectionUtils.isEmpty(listuo)) {
			return;
		}

		super.save(listuo);

		List<Role> listRole = user.getUserRoles();
		List<UserRole> listur = new ArrayList<UserRole>();
		for (Role role : listRole) {
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(role.getId());
			listur.add(ur);
		}
		if (CollectionUtils.isEmpty(listur)) {
			return;
		}

		super.save(listur);
	}

	@Override
	public User findUserById(Object id) throws Exception {

		User u = super.findById(id, User.class);
		String userId = u.getId();
		List<Org> listOrg = userOrgService.findOrgByUserId(userId);
		u.setUserOrgs(listOrg);
		List<Role> roleByUserId = userRoleMenuService.findRoleByUserId(userId);
		u.setUserRoles(roleByUserId);

		return u;
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
		User user = (User) o;
		// ==执行分页查询
		// user.setFrameTableAlias("tu");
		// finder=Finder.getSelectFinder(User.class,"tu.*,tg.name as gradeName ").append(" tu,").append(Finder.getTableName(DicData.class)).append(" tg WHERE tu.gradeId=tg.id and tg.typekey='grade' ");

		finder = Finder.getSelectFinder(User.class).append(" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, user);
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);

		return queryForList;
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
	@Caching(evict = {
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findRoleByUserId_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'getRolesAsString_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findUserByRoleId_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findAllRoleAndMenu'") })
	public void updateRoleUser(String userId, String roleId) throws Exception {
		// 删除
		// Finder finder=new
		// Finder("delete from t_user_role where userId=:userId");
		Finder finder = Finder.getDeleteFinder(UserRole.class).append(
				" WHERE userId=:userId");

		finder.setParam("userId", userId);
		this.update(finder);
		// 添加
		String[] roleIds = roleId.split(",");
		// finder=new
		// Finder("insert into t_user_role(id,userId,roleId) values(:id,:userId,:roleId)");
		// finder.setParam("userId", userId);

		List<UserRole> list = new ArrayList<UserRole>();

		for (String str : roleIds) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(str);
			list.add(ur);
		}

		if (!CollectionUtils.isEmpty(list)) {
			super.save(list);
		}

	}

	@Override
	public List<User> findLikeUser(String str, Page page) throws Exception {
		Finder finder = Finder.getSelectFinder(User.class)
				.append(" where state='1' and name like :name ")
				.setParam("name", "%" + str + "%");
		return queryForList(finder, User.class, page);
	}

	@Override
	public boolean findUserExsit(String account, String userId)
			throws Exception {
		boolean b = false;
		Finder finder = Finder.getSelectFinder(User.class)
				.append(" where account=:account ")
				.setParam("account", account);
		if (StringUtils.isNotBlank(userId)) {
			finder.append(" and id !=:id ").setParam("id", userId);
			List<User> list = super.queryForList(finder, User.class);
			if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
				b = true;
			}
		} else {
			List<User> list2 = super.queryForList(finder, User.class);
			if (list2.size() > 0) {
				b = true;
			}
		}
		return b;
	}

	@Override
	public List<User> findUserByRole(String roleId) throws Exception {
		Finder finder = Finder.getSelectFinder(User.class, "u.id,u.name ")
				.append(" as u,").append(Finder.getTableName(UserRole.class))
				.append(" as mid ")
				.append(" where mid.userId=u.id and u.state='1' ")
				.append("and mid.roleId=:roleId").setParam("roleId", roleId)
				.append(" group by u.id,u.name");
		return super.queryForList(finder, User.class);
	}

	@Override
	public boolean changePassword(String id, String password_old,
			String password_new) throws Exception {
		boolean flg = false;
		Finder finder = Finder.getSelectFinder(User.class)
				.append(" where id=:id ").setParam("id", id)
				.append(" and password =:password")
				.setParam("password", password_old);
		List<User> lists = super.queryForList(finder, User.class);
		if (lists.size() == 1) {
			finder = Finder.getUpdateFinder(User.class, "password =:password")
					.setParam("password", password_new).append(" where id=:id")
					.setParam("id", id);
			super.update(finder);
			flg = true;
		}
		return flg;
	}

	@Override
	public List<User> findUserBySiteId(String siteId) throws Exception {
		Finder finder = Finder.getSelectFinder(User.class)
				.append(" where siteId=:siteId ").setParam("siteId", siteId);
		List<User> lists = super.queryForList(finder, User.class);
		return lists;
	}

	/**
	 * @author lyf
	 * @time 2015.02.04
	 * @param id
	 * @return 响应信息
	 * @throws Exception
	 * 只有admin才能调用此方法
	 */
	@Override
	public ReturnDatas adminDelete(String id) throws Exception {
		if(StringUtils.isNotBlank(id)){
			
			//角色表删除
			UserRole role = new UserRole();
			role.setUserId(id);
			super.deleteByEntity(role);
			
			//部门表删除
			UserOrg org = new UserOrg();
			org.setUserId(id);
			super.deleteByEntity(org);
			
			//user表删除
			User user = new User();
			user.setId(id);
			super.deleteByEntity(user);
			return new ReturnDatas(ReturnDatas.SUCCESS,MessageUtils.DELETE_SUCCESS);
			
		}
		return new ReturnDatas(ReturnDatas.WARNING,MessageUtils.DELETE_WARNING);
	}

}
