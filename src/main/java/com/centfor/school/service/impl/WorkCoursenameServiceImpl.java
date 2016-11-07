package com.centfor.school.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.school.entity.WorkCoursename;
import com.centfor.school.service.IWorkCoursenameService;
import com.centfor.system.service.BaseSuperCMSServiceImpl;
import com.centfor.frame.entity.IBaseEntity;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2016-03-29 11:40:44
 * @see com.centfor.school.service.impl.WorkCoursename
 */
@Service("workCoursenameService")
public class WorkCoursenameServiceImpl extends BaseSuperCMSServiceImpl implements IWorkCoursenameService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      WorkCoursename workCoursename=(WorkCoursename) entity;
	       return super.save(workCoursename).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      WorkCoursename workCoursename=(WorkCoursename) entity;
		 return super.saveorupdate(workCoursename).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 WorkCoursename workCoursename=(WorkCoursename) entity;
	return super.update(workCoursename);
    }
    @Override
	public WorkCoursename findWorkCoursenameById(Object id) throws Exception{
	 return super.findById(id,WorkCoursename.class);
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
	public List<WorkCoursename> findWorkCourseNameByItem(String item)
			throws Exception {
		// TODO Auto-generated method stub
		Finder finder =Finder.getSelectFinder(WorkCoursename.class);
		finder.append("where schoolItem =:schoolItem");
		finder.setParam("schoolItem", item);
		return super.queryForList(finder, WorkCoursename.class);
	}

	@Override
	public void deleteWorkCourseNameAndMarkById(String id) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(id)){
			Finder finder = new Finder("delete from work_coursename where id =:id").setParam("id", id);
			Finder finder2 = new Finder("delete from work_mark where courseNameId =:courseNameId").setParam("courseNameId", id);
			super.update(finder);
			super.update(finder2);
		}
	}

	@Override
	public List<WorkCoursename> findWorkCourseNameByItemAndMajorName(String item, String majorName) throws Exception {
		// TODO Auto-generated method stub
		Finder finder =null;
		if(StringUtils.isNotBlank(item) && StringUtils.isNotBlank(majorName)){
			finder = new Finder("select * from work_coursename where schoolItem =:schoolItem and majorName like :majorName");
			finder.setParam("schoolItem", item).setParam("majorName", "%"+majorName+"%");
		}
		return super.queryForList(finder, WorkCoursename.class);
	}

}
