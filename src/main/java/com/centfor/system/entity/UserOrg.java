package com.centfor.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.centfor.frame.annotation.WhereSQL;
import com.centfor.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see com.centfor.system.entity.UserOrg
 */
@Table(name="t_user_org")
public class UserOrg  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "UserOrg";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_USERID = "用户编号";
	public static final String ALIAS_ORGID = "机构编号";
	public static final String ALIAS_MANAGER = "0.不是1.是";
    */
	//date formats
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 用户编号
	 */
	private java.lang.String userId;
	/**
	 * 机构编号
	 */
	private java.lang.String orgId;
	/**
	 * 0.不是1.是
	 */
	private java.lang.Integer manager;
	//columns END 数据库字段结束
	
	//concstructor

	public UserOrg(){
	}

	public UserOrg(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:UserOrg_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:UserOrg_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setOrgId(java.lang.String value) {
		this.orgId = value;
	}
	
     @WhereSQL(sql="orgId=:UserOrg_orgId")
	public java.lang.String getOrgId() {
		return this.orgId;
	}
	public void setManager(java.lang.Integer value) {
		this.manager = value;
	}
	
     @WhereSQL(sql="manager=:UserOrg_manager")
	public java.lang.Integer getManager() {
		return this.manager;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("编号[").append(getId()).append("],")
			.append("用户编号[").append(getUserId()).append("],")
			.append("机构编号[").append(getOrgId()).append("],")
			.append("0.不是1.是[").append(getManager()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserOrg == false) return false;
		if(this == obj) return true;
		UserOrg other = (UserOrg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
