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
 * @version  2014-10-21 09:36:16
 * @see com.centfor.cms.entity.CmsReThemeGroup
 */
@Table(name="cms_re_theme_group")
public class CmsReThemeGroup  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CmsReThemeGroup";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_THEMEID = "主题Id";
	public static final String ALIAS_THEMEGROUPID = "主题组Id";
    */
	//date formats
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 主题Id
	 */
	private java.lang.String themeId;
	/**
	 * 主题组Id
	 */
	private java.lang.String themeGroupId;
	/**
	 * 系统平台
	 */
	private String ostype;
	//columns END 数据库字段结束
	
	//concstructor

	
	public CmsReThemeGroup(){
	}

	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public CmsReThemeGroup(
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
     @WhereSQL(sql="id=:CmsReThemeGroup_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setThemeId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.themeId = value;
	}
	
     @WhereSQL(sql="themeId=:CmsReThemeGroup_themeId")
	public java.lang.String getThemeId() {
		return this.themeId;
	}
	public void setThemeGroupId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.themeGroupId = value;
	}
	
     @WhereSQL(sql="themeGroupId=:CmsReThemeGroup_themeGroupId")
	public java.lang.String getThemeGroupId() {
		return this.themeGroupId;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("主题Id[").append(getThemeId()).append("],")
			.append("主题组Id[").append(getThemeGroupId()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsReThemeGroup == false) return false;
		if(this == obj) return true;
		CmsReThemeGroup other = (CmsReThemeGroup)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
