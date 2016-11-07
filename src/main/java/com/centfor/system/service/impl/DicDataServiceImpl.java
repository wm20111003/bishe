package com.centfor.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.centfor.frame.entity.IBaseEntity;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.GlobalStatic;
import com.centfor.frame.util.Page;
import com.centfor.system.entity.DicBusiness;
import com.centfor.system.entity.DicData;
import com.centfor.system.service.BaseSuperCMSServiceImpl;
import com.centfor.system.service.IDicDataService;
import com.centfor.system.service.ISystemAutoIdService;


/**
 * TODO 在此加入类描述
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version  2013-07-31 15:56:45
 * @see com.centfor.bbz.service.impl.DicData
 */
@Service("dicDataService")
public class DicDataServiceImpl extends BaseSuperCMSServiceImpl implements IDicDataService {

	@Resource
	private ISystemAutoIdService systemAutoIdService;
    @Override
	public String  save(Object entity ) throws Exception{
	      DicData dicData=(DicData) entity;
	       return (String) super.save(dicData);
	}
    @Override
    @CacheEvict(value = GlobalStatic.cacheKey, key = "'findListDicData'+#pathtypekey")
	public String  saveorupdateDicData(DicData dicData,String pathtypekey) throws Exception{
    	if(StringUtils.isBlank(pathtypekey)||dicData==null){
    		return null;
    	}
    	String typeKey=dicData.getTypekey();
    	if(!pathtypekey.equals(typeKey)){
    		return null;
    	}
    	
    	String id=dicData.getId();
    	if(StringUtils.isBlank(id)){
    		 id=null;
    	     dicData.setId(id);
    	     super.save(dicData);
    	     return id;
    	}
    	return super.update(dicData).toString();
    	
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 DicData dicData=(DicData) entity;
	return super.update(dicData);
    }
    @Override
	public DicData findDicDataById(Object id) throws Exception{
	 return super.findById(id,DicData.class);
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
        	if(finder==null){
        	 finder=Finder.getSelectFinder(DicData.class).append(" WHERE 1=1 ");
        	}
        	if(o!=null){
        		getFinderWhereByQueryBean(finder, o);
        	}
        	
        	
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	

	@Override
	public DicData findDicDataById(String id,String pathtypekey) throws Exception {
		if(StringUtils.isBlank(id)||StringUtils.isBlank(pathtypekey)){
			return null;
		}
		Finder finder=Finder.getSelectFinder(DicData.class).append("  where id=:id and  typekey=:typekey   order by name ");
		finder.setParam("typekey",pathtypekey).setParam("id", id);
		DicData dicData=super.queryForObject(finder, DicData.class);
		return dicData;
	}

	@Override
	@Cacheable(value = GlobalStatic.cacheKey, key = "'findListDicData'+#pathtypekey")
	public List<DicData> findListDicData(String pathtypekey) throws Exception {
		if(StringUtils.isBlank(pathtypekey)){
			return null;
		}
		Finder finder=Finder.getSelectFinder(DicData.class).append(" WHERE typekey=:typekey order by sort asc ");
		finder.setParam("typekey", pathtypekey);
		return super.queryForList(finder, DicData.class);
	}

	@Override
	@CacheEvict(value = GlobalStatic.cacheKey, key = "'findListDicData'+#pathtypekey")
	public void deleteDicDataById(String id, String pathtypekey)
			throws Exception {
		
		if(StringUtils.isBlank(id)||StringUtils.isBlank(pathtypekey)){
			return;
		}
		
		Finder finder=Finder.getDeleteFinder(DicData.class).append(" where id=:id and  typekey=:typekey  ");
		finder.setParam("typekey",pathtypekey).setParam("id", id);
		super.update(finder);
		
		
		
	}

	@Override
	@CacheEvict(value = GlobalStatic.cacheKey, key = "'findListDicData'+#pathtypekey")
	public void deleteDicDataByIds(List<String> ids, String pathtypekey)
			throws Exception {
		if(CollectionUtils.isEmpty(ids)||StringUtils.isBlank(pathtypekey)){
			return;
		}
		
		Finder finder=Finder.getDeleteFinder(DicData.class).append(" where id in(:ids) and  typekey=:typekey  ");
		finder.setParam("typekey",pathtypekey).setParam("ids", ids);
		super.update(finder);
		
		
	}
	@Override
	public String findCacheNameById(String id, String pathtypekey) throws Exception {
		List<DicData> findListDicData = findListDicData(pathtypekey);
		if(CollectionUtils.isEmpty(findListDicData)||StringUtils.isBlank(id)){
			return null;
		}
		for(DicData dicData:findListDicData){
			if(dicData.getId().equals(id)){
				return dicData.getName();
			}
		}
		
		
		return null;
	}
	@Override
	public List<DicBusiness> findBusinessById(String id, String pathtypekey,Page page)
			throws Exception {
		if(StringUtils.isBlank(pathtypekey)||StringUtils.isBlank(id)){
			return null;
		}
		Finder finder=Finder.getSelectFinder(DicBusiness.class,"dicb.*,dic.pid pid,dic.name name,dic.code code ").append(" dicb, ").append(Finder.getTableName(DicData.class)).append(" dic ").append(" WHERE dic.id=dicb.dicId and  dicb.typeKey=:typeKey and dicb.businessId=:businessId order by dicb.sort asc ");
		finder.setParam("typeKey", pathtypekey).setParam("businessId", id);
		 List<DicBusiness> queryForList = super.queryForList(finder, DicBusiness.class, page);
		
			 return queryForList;
		 

		 
	}
	@Override
	public List<DicData> findAjaxTreeJson(String pid, String pathtypekey)
			throws Exception {
	
		
		List<DicData> list = findListDicDataByPid(pathtypekey,  pid);
		
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		List<DicData> wrapList=new ArrayList<DicData>();
		diguiwrapList(list, wrapList, null);
		
		return wrapList;
	}
	
	
	
	private List<DicData> diguiwrapList(List<DicData> from,List<DicData> tolist,String parentId){
		if(CollectionUtils.isEmpty(from)){
			return null;
		}
		
		for(int i=0;i<from.size();i++){
			DicData m=from.get(i);
			if(m==null){
				//from.remove(i);
			//	i=i-1;
				continue;
			}
		
			String pid=m.getPid();
			if((pid==null)&&(parentId!=null)){
				continue;
			}
		
			if((parentId==m.getPid())||(m.getPid().equals(parentId))){
				List<DicData> leaf=new ArrayList<DicData>();
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
	@Override
	public List<DicData> findListDicDataByPid(String pathtypekey, String pid)
			throws Exception {
		
		if(StringUtils.isBlank(pathtypekey)){
			return null;
		}
		
		Finder finder=Finder.getSelectFinder(DicData.class).append(" WHERE typeKey=:typeKey ");
		finder.setParam("typeKey", pathtypekey);
		if(StringUtils.isNotBlank(pid)){
			finder.append(" and pid=:pid ").setParam("pid", pid);
		}else{
			finder.append(" and pid is null ");
		}
		finder.append(" order by sort asc ");
		
		
		List<DicData> list = super.queryForList(finder, DicData.class);
		
		
		
		
		
		return list;
	}
	
	

}
