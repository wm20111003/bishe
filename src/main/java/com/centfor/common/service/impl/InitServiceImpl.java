package com.centfor.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;

import com.centfor.cms.entity.CmsChannel;
import com.centfor.cms.service.ICmsTableindexService;
import com.centfor.common.service.InitService;
import com.centfor.frame.util.Finder;
import com.centfor.system.service.BaseSuperCMSServiceImpl;

public class InitServiceImpl extends BaseSuperCMSServiceImpl implements
		InitService {
	@Resource
	private ICmsTableindexService tableindexService;

	private List<String> _initSqls = new ArrayList<String>();

	public void set_initSqls(List<String> _initSqls) {
		this._initSqls = _initSqls;
	}

	@Override
	public boolean saveInit(String siteId, String userCode) {
		try {
			if (!CollectionUtils.isEmpty(_initSqls)) {
				for (String sql : _initSqls) {
					String channelId = tableindexService
							.saveNewId(CmsChannel.class);
					sql = StringUtils.replace(sql, "#USERCODE", userCode);
					sql = StringUtils.replace(sql, "#SITEID", siteId);
					sql = StringUtils.replace(sql, "#CHANNELID", channelId);
					if (StringUtils.isNotBlank(sql)) {
						Finder finder = new Finder(sql);

						super.update(finder);
					}
				}
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
