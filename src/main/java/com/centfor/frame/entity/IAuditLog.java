package com.centfor.frame.entity;

import java.util.Date;


/**
 * 公共 的记录日志接口,主要是为了处理多库情况下的日志表的Id类型不同
 * @copyright {@link 9iu.org}
 * @author centfor<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see com.centfor.frame.entity.IBaseEntity
 * @param <T>
 */
public interface IAuditLog extends IBaseEntity {

	public void setOperationType(String value);

	public String getOperationType();

	public void setOperatorName(String value);

	public String getOperatorName();

	public void setPreValue(String value);

	public String getPreValue();

	public void setCurValue(String value);

	public java.lang.String getCurValue();

	public void setOperationTime(Date value);

	public java.util.Date getOperationTime();

	public void setOperationClass(String value);

	public java.lang.String getOperationClass();

	public void setOperationClassId(String value);

	public java.lang.String getOperationClassId();
	
	public String getExt();
	public void setExt(String ext);
	
	
}
