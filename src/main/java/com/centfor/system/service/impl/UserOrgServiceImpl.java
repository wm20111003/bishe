package com.centfor.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.frame.util.Finder;
import com.centfor.system.entity.Org;
import com.centfor.system.entity.User;
import com.centfor.system.entity.UserOrg;
import com.centfor.system.service.BaseSuperCMSServiceImpl;
import com.centfor.system.service.IUserOrgService;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 15:28:18
 * @see com.centfor.bbz.service.impl.TuserOrg
 */
@Service("userOrgService")
public class UserOrgServiceImpl extends BaseSuperCMSServiceImpl implements IUserOrgService {

	@Override
	public List<User> findUserByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		Finder finder=new Finder("SELECT u.* FROM ").append(Finder.getTableName(User.class)).append("  u,").append(Finder.getTableName(UserOrg.class)).append(" re where re.userId=u.id and re.orgId=:orgId");
		
		
		
		
		finder.setParam("orgId", orgId);
		return super.queryForList(finder,User.class);
	}

	@Override
	public List<User> findAllUserByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		//Finder f_code=new Finder("SELECT comcode FROM t_org where id=:orgId ");
		Finder f_code=Finder.getSelectFinder(Org.class, "comcode").append(" where id=:orgId ");
		f_code.setParam("orgId", orgId);
		String comcode=super.queryForObject(f_code, String.class);
		
		Finder finder=new Finder("SELECT u.* FROM ").append(Finder.getTableName(User.class)).append("  u,").append(Finder.getTableName(UserOrg.class)).append(" re,").append(Finder.getTableName(Org.class)).append(" org WHERE org.id=re.orgId and u.id=re.userId and org.comcode like :comcode");
		finder.setParam("comcode", comcode+"%");
		return super.queryForList(finder,User.class);
	}

	@Override
	public List<Org> findOrgByUserId(String userId) throws Exception {
		if(StringUtils.isBlank(userId)){
			return null;
		}
		Finder finder=new Finder("SELECT org.* FROM  ").append(Finder.getTableName(UserOrg.class)).append(" re ,").append(Finder.getTableName(Org.class)).append(" org  WHERE re.userId=:userId and org.id=re.orgId  ");
		finder.setParam("userId", userId);
		return super.queryForList(finder, Org.class);
	}

	@Override
	public User findManagerByOrgId(String orgId) throws Exception {
		Finder finder=new Finder("SELECT u.* FROM ").append(Finder.getTableName(User.class)).append(" u,").append(Finder.getTableName(UserOrg.class)).append(" re  WHERE re.orgId=:orgId and u.id=re.userId  ");
		finder.setParam("orgId", orgId);
		return super.queryForObject(finder, User.class);
	}

   

		
}
