package com.centfor.school.service;

import com.centfor.school.entity.WorkCourse;
import com.centfor.system.service.IBaseSuperCMSService;;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-05-04 20:42:29
 * @see com.centfor.school.service.WorkCourse
 */
public interface IWorkCourseService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	WorkCourse findWorkCourseById(Object id) throws Exception;
	
	
	
}
