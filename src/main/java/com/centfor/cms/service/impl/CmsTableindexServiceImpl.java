package com.centfor.cms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsTableindex;
import com.centfor.cms.service.ICmsTableindexService;
import com.centfor.frame.util.Finder;
import com.centfor.system.service.BaseSuperCMSServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2014-11-18 15:51:46
 * @see com.centfor.cms.service.impl.CmsTableindex
 */
@Service("cmsTableindexService")
public class CmsTableindexServiceImpl extends BaseSuperCMSServiceImpl implements ICmsTableindexService {


   
	@Override
	public String saveNewId(Class clazz) throws Exception {
		if(clazz==null){
			return "表名不能为空!";
		}
		
		String tableName=Finder.getTableName(clazz);
		
		if(StringUtils.isBlank(tableName)){
			return "表名不能为空!";
		}
		
		Finder finder=Finder.getSelectFinder(CmsTableindex.class,"maxindex,code");
		finder.append(" where tablename =:tablename ").setParam("tablename", tableName);
		CmsTableindex ms=super.queryForObject(finder, CmsTableindex.class);
		if(ms!=null){
			ms.setMaxindex(ms.getMaxindex()+1);
			Finder f=Finder.getUpdateFinder(CmsTableindex.class, " maxindex=:maxindex ").append(" WHERE tablename =:tablename ").setParam("maxindex", ms.getMaxindex()).setParam("tablename", tableName);
			super.update(f);
		}else{
			return "表名输入有误!";
		}
		return ms.getCode()+ms.getMaxindex();
	}
}
