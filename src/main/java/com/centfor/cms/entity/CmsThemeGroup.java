package com.centfor.cms.entity;

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
 * @version  2014-10-21 09:36:17
 * @see com.centfor.cms.entity.CmsThemeGroup
 */
@Table(name="cms_theme_group")
public class CmsThemeGroup  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CmsThemeGroup";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_USECOUNT = "使用次数";
	public static final String ALIAS_OSTYPE = "pc,pad,weixin,mobile,app 五个平台的linkURL";
	public static final String ALIAS_STATE = "状态 0关闭,1开启";
    */
	//date formats
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 使用次数
	 */
	private java.lang.Integer usecount;

	/**
	 * 状态 0关闭,1开启
	 */
	private java.lang.Integer state;
	//columns END 数据库字段结束
	
	//concstructor

	public CmsThemeGroup(){
	}

	public CmsThemeGroup(
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
     @WhereSQL(sql="id=:CmsThemeGroup_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name like %:CmsThemeGroup_name%")
	public java.lang.String getName() {
		return this.name;
	}
	public void setUsecount(java.lang.Integer value) {
		this.usecount = value;
	}
	
     @WhereSQL(sql="usecount=:CmsThemeGroup_usecount")
	public java.lang.Integer getUsecount() {
		return this.usecount;
	}

	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:CmsThemeGroup_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("使用次数[").append(getUsecount()).append("],")
			.append("pc,pad,weixin,mobile,app 五个平台的linkURL[")
			.append("状态 0关闭,1开启[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsThemeGroup == false) return false;
		if(this == obj) return true;
		CmsThemeGroup other = (CmsThemeGroup)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
