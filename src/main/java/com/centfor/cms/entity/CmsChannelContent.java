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
 * @version  2014-10-21 09:36:15
 * @see com.centfor.cms.entity.CmsChannelContent
 */
@Table(name="cms_channel_content")
public class CmsChannelContent  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CmsChannelContent";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SITEID = "siteId";
	public static final String ALIAS_CHANNELID = "channelId";
	public static final String ALIAS_CONTENTID = "contentId";
	public static final String ALIAS_OSTYPE = "pc,pad,weixin,app 可用于细化数据归属";
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
	 * siteId
	 */
	private java.lang.String siteId;
	/**
	 * channelId
	 */
	private java.lang.String channelId;
	/**
	 * contentId
	 */
	private java.lang.String contentId;
	/**
	 * pc,pad,weixin,app 可用于细化数据归属
	 */
	private java.lang.String ostype;
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

	public CmsChannelContent(){
	}

	public CmsChannelContent(
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
     @WhereSQL(sql="id=:CmsChannelContent_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsChannelContent_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setChannelId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.channelId = value;
	}
	
     @WhereSQL(sql="channelId=:CmsChannelContent_channelId")
	public java.lang.String getChannelId() {
		return this.channelId;
	}
	public void setContentId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.contentId = value;
	}
	
     @WhereSQL(sql="contentId=:CmsChannelContent_contentId")
	public java.lang.String getContentId() {
		return this.contentId;
	}
	public void setOstype(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ostype = value;
	}
	
     @WhereSQL(sql="ostype=:CmsChannelContent_ostype")
	public java.lang.String getOstype() {
		return this.ostype;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
     @WhereSQL(sql="sort=:CmsChannelContent_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:CmsChannelContent_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("channelId[").append(getChannelId()).append("],")
			.append("contentId[").append(getContentId()).append("],")
			.append("pc,pad,weixin,app 可用于细化数据归属[").append(getOstype()).append("],")
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
		if(obj instanceof CmsChannelContent == false) return false;
		if(this == obj) return true;
		CmsChannelContent other = (CmsChannelContent)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
