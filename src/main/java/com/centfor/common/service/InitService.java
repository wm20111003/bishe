package com.centfor.common.service;

import com.centfor.system.service.IBaseSuperCMSService;

public interface InitService extends IBaseSuperCMSService {

	boolean saveInit(String siteId, String userCode);

}
