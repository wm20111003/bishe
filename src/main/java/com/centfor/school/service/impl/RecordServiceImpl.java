package com.centfor.school.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.centfor.school.entity.Record;
import com.centfor.school.service.IRecordService;
import com.centfor.frame.entity.IBaseEntity;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-04-20 21:49:19
 * @see com.centfor.school.service.impl.Record
 */
@Service("recordService")
public class RecordServiceImpl extends BaseSuperCMSServiceImpl implements IRecordService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      Record record=(Record) entity;
	       return super.save(record).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      Record record=(Record) entity;
		 return super.saveorupdate(record).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 Record record=(Record) entity;
	return super.update(record);
    }
    @Override
	public Record findRecordById(Object id) throws Exception{
	 return super.findById(id,Record.class);
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
	public Record findRecordByUserId(String UserId) throws Exception {
		Finder finder=Finder.getSelectFinder(Record.class).append("where userId=:userId").setParam("userId", UserId);
		return super.queryForObject(finder, Record.class);
	}

}
