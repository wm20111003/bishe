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
 * @see com.centfor.cms.entity.CmsPropertyvalue
 */
@Table(name="cms_propertyvalue")
public class CmsPropertyvalue  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "属性表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PROPERTYID = "propertyId";
	public static final String ALIAS_PVALUE = "pvalue";
	public static final String ALIAS_SITEID = "siteId";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_STATE = "0失效,1有效";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * propertyId
	 */
	private java.lang.String propertyId;
	/**
	 * pvalue
	 */
	private java.lang.String pvalue;
	/**
	 * siteId
	 */
	private java.lang.String siteId;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 0失效,1有效
	 */
	private java.lang.Integer state;
	//columns END 数据库字段结束
	
	//concstructor

	public CmsPropertyvalue(){
	}

	public CmsPropertyvalue(
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
     @WhereSQL(sql="id=:CmsPropertyvalue_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setPropertyId(java.lang.String value) {
		this.propertyId = value;
	}
	
     @WhereSQL(sql="propertyId=:CmsPropertyvalue_propertyId")
	public java.lang.String getPropertyId() {
		return this.propertyId;
	}
	public void setPvalue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.pvalue = value;
	}
	
     @WhereSQL(sql="pvalue=:CmsPropertyvalue_pvalue")
	public java.lang.String getPvalue() {
		return this.pvalue;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsPropertyvalue_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsPropertyvalue_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
     @WhereSQL(sql="sort=:CmsPropertyvalue_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:CmsPropertyvalue_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("propertyId[").append(getPropertyId()).append("],")
			.append("pvalue[").append(getPvalue()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("排序[").append(getSort()).append("],")
			.append("0失效,1有效[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsPropertyvalue == false) return false;
		if(this == obj) return true;
		CmsPropertyvalue other = (CmsPropertyvalue)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
