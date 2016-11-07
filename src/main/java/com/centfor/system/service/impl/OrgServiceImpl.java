package com.centfor.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.entity.Org;
import com.centfor.system.service.BaseSuperCMSServiceImpl;
import com.centfor.system.service.IOrgService;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:02:58
 * @see com.centfor.bbz.service.impl.Org
 */
@Service("orgService")
public class OrgServiceImpl extends BaseSuperCMSServiceImpl implements IOrgService {

   
    @Override
	public String  saveOrg(Org entity) throws Exception{
	       return super.save(entity).toString();
	}

    @Override
	public String  saveorupdateOrg(Org entity) throws Exception{
	       return super.saveorupdate(entity).toString();
	}
	
	@Override
    public Integer updateOrg(Org entity) throws Exception{
	return super.update(entity);
    }
    @Override
	public Org findOrgById(Object id) throws Exception{
	 return super.findById(id,Org.class);
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
        	
        	 finder=Finder.getSelectFinder(Org.class).append(" WHERE state=:state  order by sortno asc ");
        	 finder.setParam("state", "是");
			 return super.queryForList(finder, clazz);
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
	public List<Org> findTreeOrg() throws Exception {
		Finder finder=Finder.getSelectFinder(Org.class).append(" WHERE state=0 ");
		
		List<Org> list=super.queryForList(finder, Org.class);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<Org> wrapList=new ArrayList<Org>();
		diguiwrapList(list, wrapList, null);
		
		return wrapList;
	}
	
	private List<Org> diguiwrapList(List<Org> from,List<Org> tolist,String parentId){
		if(CollectionUtils.isEmpty(from)){
			return null;
		}
		
		for(int i=0;i<from.size();i++){
			Org m=from.get(i);
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
				List<Org> leaf=new ArrayList<Org>();
				m.setLeafOrg(leaf);
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
