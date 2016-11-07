package com.centfor.system.service.impl;

import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;
import com.centfor.system.entity.Forgetpasswd;
import com.centfor.system.service.IForgetpasswdService;
import com.centfor.frame.entity.IBaseEntity;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2014-09-12 22:52:27
 * @see com.centfor.system.service.impl.Forgetpasswd
 */
@Service("forgetpasswdService")
public class ForgetpasswdServiceImpl extends BaseSuperCMSServiceImpl implements IForgetpasswdService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      Forgetpasswd forgetpasswd=(Forgetpasswd) entity;
	       return super.save(forgetpasswd).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      Forgetpasswd forgetpasswd=(Forgetpasswd) entity;
		 return super.saveorupdate(forgetpasswd).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 Forgetpasswd forgetpasswd=(Forgetpasswd) entity;
	return super.update(forgetpasswd);
    }
    @Override
	public Forgetpasswd findForgetpasswdById(Object id) throws Exception{
	 return super.findById(id,Forgetpasswd.class);
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

}
