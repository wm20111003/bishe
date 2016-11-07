package com.centfor.school.service;

import com.centfor.school.entity.SpMember;
import com.centfor.system.service.IBaseSuperCMSService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-01-19 10:01:00
 * @see com.centfor.shop.service.SpMember
 */
public interface ISpMemberService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SpMember findSpMemberById(Object id) throws Exception;
	
	/**
	 * 根据会员的账户名称和状态获取会员信息
	 * @param siteId 站点Id
	 * @param account 会员账户名称
	 * @param state 会员状态 （是、否） 也可以为空
	 * @return
	 * @throws Exception
	 */
	SpMember finderMemberByAccountAndState(String siteId,String account,String state)throws Exception;

}
