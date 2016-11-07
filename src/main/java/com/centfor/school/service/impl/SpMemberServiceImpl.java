package com.centfor.school.service.impl;

import java.io.File;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.school.entity.SpMember;
import com.centfor.school.service.ISpMemberService;
import com.centfor.frame.entity.IBaseEntity;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-01-19 10:01:00
 * @see com.centfor.shop.service.impl.SpMember
 */
@Service("spMemberService")
public class SpMemberServiceImpl extends BaseSuperCMSServiceImpl implements ISpMemberService {
   
	
    @Override
	public String  save(Object entity ) throws Exception{
	      SpMember spMember=(SpMember) entity;
	       return super.save(spMember).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      SpMember spMember=(SpMember) entity;
		 return super.saveorupdate(spMember).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 SpMember spMember=(SpMember) entity;
	return super.update(spMember);
    }
    @Override
	public SpMember findSpMemberById(Object id) throws Exception{
	 return super.findById(id,SpMember.class);
	}
	
/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}

	@Override
	public SpMember finderMemberByAccountAndState(String siteId,String account, String state)
			throws Exception {
		// TODO Auto-generated method stub
		Finder finder=Finder.getSelectFinder(SpMember.class);
		finder.append(" where account=:account ").setParam("account", account);
		if(StringUtils.isNotBlank(siteId)){
			finder.append(" and siteId=:siteId ").setParam("siteId", siteId);
		}
		if(StringUtils.isNotBlank(state)){
			finder.append("and state=:state ").setParam("state", state);
		}
		List<SpMember> list=queryForList(finder, SpMember.class);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
