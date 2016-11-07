package com.centfor.school.service;

import java.util.List;

import com.centfor.school.entity.WorkMark;
import com.centfor.system.service.IBaseSuperCMSService;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2016-03-29 11:40:01
 * @see com.centfor.school.service.WorkMark
 */
public interface IWorkMarkService extends IBaseSuperCMSService {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	WorkMark findWorkMarkById(Object id) throws Exception;
	
	/**
	 * 根据userId 和 courseNameId 判断数据中同一个人有没有相同的课程
	 * @param userId
	 * @param courseNameId
	 * @return
	 * @throws Exception
	 */
	WorkMark findWorkMarkByUserIdCourseNameId(String userId,String courseNameId) throws Exception;
	
	
}
