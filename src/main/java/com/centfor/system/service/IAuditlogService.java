package com.centfor.system.service;

import com.centfor.system.entity.AuditLog;

/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-04-02 10:17:31
 * @see com.centfor.bbz.service.AuditLog
 */
public interface IAuditlogService extends IBaseSuperCMSService {
	String saveAuditlog(AuditLog entity) throws Exception;
    String saveorupdateAuditlog(AuditLog entity) throws Exception;
	Integer updateAuditlog(AuditLog entity) throws Exception;
	AuditLog findAuditlogById(Object id) throws Exception;
	
}
