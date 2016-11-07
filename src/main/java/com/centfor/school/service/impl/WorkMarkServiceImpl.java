package com.centfor.school.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.centfor.school.entity.WorkMark;
import com.centfor.school.service.IWorkMarkService;
import com.centfor.frame.entity.IBaseEntity;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2016-03-29 11:40:01
 * @see com.centfor.school.service.impl.WorkMark
 */
@Service("workMarkService")
public class WorkMarkServiceImpl extends BaseSuperCMSServiceImpl implements IWorkMarkService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      WorkMark workMark=(WorkMark) entity;
	       return super.save(workMark).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      WorkMark workMark=(WorkMark) entity;
		 return super.saveorupdate(workMark).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 WorkMark workMark=(WorkMark) entity;
	return super.update(workMark);
    }
    @Override
	public WorkMark findWorkMarkById(Object id) throws Exception{
	 return super.findById(id,WorkMark.class);
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
	public WorkMark findWorkMarkByUserIdCourseNameId(String userId,
			String courseNameId) throws Exception {
		// TODO Auto-generated method stub
		Finder finder = Finder.getSelectFinder(WorkMark.class).append("where userId =:userId and courseNameId=:courseNameId");
		finder.setParam("userId", userId).setParam("courseNameId", courseNameId);
		
		return super.queryForObject(finder, WorkMark.class);
	}

}
