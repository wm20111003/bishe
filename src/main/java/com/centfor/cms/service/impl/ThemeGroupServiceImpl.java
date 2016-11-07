package com.centfor.cms.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.centfor.cms.entity.CmsReThemeGroup;
import com.centfor.cms.entity.CmsTheme;
import com.centfor.cms.entity.CmsThemeGroup;
import com.centfor.cms.service.IThemeGroupService;
import com.centfor.frame.util.Finder;
import com.centfor.frame.util.Page;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see com.centfor.bbz.service.impl.User
 */
@Service("themeGroupService")
public class ThemeGroupServiceImpl extends BaseSuperCMSServiceImpl implements
		IThemeGroupService {

	@Override
	public void saveOrUpdateThemeGroup(CmsThemeGroup entity,
			List<CmsReThemeGroup> listre) throws Exception {
		String groupId = entity.getId();
		if (StringUtils.isNotBlank(entity.getId())) {
			// 删除旧的关联
			super.update(entity);
			deleteThemeRe(entity.getId());
		} else {
			entity.setId(null);
			groupId = super.save(entity).toString();
		}
		if (listre != null && listre.size() > 0) {
			for (CmsReThemeGroup re : listre) {
				re.setThemeGroupId(groupId);
			}
		}
		super.save(listre);

	}

	@Override
	public void deleteThemeRe(String groupId) throws Exception {
		String tablename = Finder.getTableName(CmsReThemeGroup.class);
		Finder finder = new Finder(" delete from " + tablename
				+ "  where themeGroupId= :group ");
		finder.setParam("group", groupId);
		super.update(finder);
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		CmsThemeGroup qb = (CmsThemeGroup) o;
		finder = Finder.getSelectFinder(CmsThemeGroup.class).append(
				" WHERE 1=1 ");

		super.getFinderWhereByQueryBean(finder, qb);
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);

		return queryForList;
	}

	@Override
	public List<CmsReThemeGroup> findReTheme(CmsReThemeGroup qb, Page page)
			throws Exception {
		Finder finder = Finder.getSelectFinder(CmsReThemeGroup.class).append(
				" WHERE 1=1 ");
		super.getFinderWhereByQueryBean(finder, qb);
		super.getFinderOrderBy(finder, page);
		List<CmsReThemeGroup> queryForList = super.queryForList(finder,
				CmsReThemeGroup.class, page);
		return queryForList;
	}

	@Override
	public List<CmsTheme> findListThemeByGroup(String groupId,String modelType) throws Exception {
		if(StringUtils.isBlank(groupId)){
			return null;
		}
		Finder finder = new Finder("");
		finder.append(" select t.* from "+Finder.getTableName(CmsReThemeGroup.class)+" as re,"+Finder.getTableName(CmsTheme.class)+" as t"
				+ " where re.themeId=t.id and t.state=1 and re.themeGroupId=:group ");
		finder.setParam("group", groupId);
		if(StringUtils.isNotBlank(modelType)){
			finder.append(" and t.modelType=:modelType").setParam("modelType", modelType);
		}
		return queryForList(finder, CmsTheme.class);
	}

}
