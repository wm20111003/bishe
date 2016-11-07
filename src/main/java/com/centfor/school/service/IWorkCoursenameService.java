package com.centfor.school.service;

import java.util.List;

import com.centfor.school.entity.WorkCoursename;
import com.centfor.system.service.IBaseSuperCMSService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2016-03-29 11:40:44
 * @see com.centfor.school.service.WorkCoursename
 */
public interface IWorkCoursenameService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	WorkCoursename findWorkCoursenameById(Object id) throws Exception;
	/**
	 * 通过学期查询课程表
	 * @param item
	 * @return
	 * @throws Exception
	 */
	List<WorkCoursename> findWorkCourseNameByItem(String item) throws Exception;
	/**
	 * 通过课程id删除课程和相应课程的成绩
	 * @param id
	 * @throws Exception
	 */
	void deleteWorkCourseNameAndMarkById(String id) throws Exception;
	/**
	 * 通过专业和学期查课程表和成绩表
	 * @param item
	 * @param majorName
	 * @return
	 * @throws Exception
	 */
	List<WorkCoursename> findWorkCourseNameByItemAndMajorName(String item,String majorName) throws Exception;
}
