package com.centfor.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.centfor.frame.annotation.WhereSQL;
import com.centfor.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2014-09-12 22:52:27
 * @see com.centfor.system.entity.Forgetpasswd
 */
@Table(name="bbz_forgetpasswd")
public class Forgetpasswd  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "忘记密码";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_MEMBERID = "用户Id ";
	public static final String ALIAS_ENDDATE = "失效时间";
	public static final String ALIAS_STATE = "0失效,1可用";
    */
	//date formats
	//public static final String FORMAT_ENDDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 用户Id 
	 */
	private java.lang.String account;
	/**
	 * 失效时间
	 */
	private java.util.Date endDate;
	/**
	 * 0失效,1可用
	 */
	private java.lang.Integer state;
	//columns END 数据库字段结束
	
	//concstructor

	public Forgetpasswd(){
	}

	public Forgetpasswd(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Forgetpasswd_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setAccount(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.account = value;
	}
	
     @WhereSQL(sql="memberId=:Forgetpasswd_memberId")
	public java.lang.String getAccount() {
		return this.account;
	}
		/*
	public String getendDateString() {
		return DateUtils.convertDate2String(FORMAT_ENDDATE, getendDate());
	}
	public void setendDateString(String value) throws ParseException{
		setendDate(DateUtils.convertString2Date(FORMAT_ENDDATE,value));
	}*/
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
     @WhereSQL(sql="endDate=:Forgetpasswd_endDate")
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:Forgetpasswd_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("用户Id [").append(getAccount()).append("],")
			.append("失效时间[").append(getEndDate()).append("],")
			.append("0失效,1可用[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Forgetpasswd == false) return false;
		if(this == obj) return true;
		Forgetpasswd other = (Forgetpasswd)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
