package com.centfor.cms.entity;

import javax.annotation.Resource;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
 * @see com.centfor.cms.entity.CmsSiteChannel
 */
@Table(name="cms_site_channel")
public class CmsSiteChannel  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CmsSiteChannel";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SITEID = "网站ID";
	public static final String ALIAS_CHANNELID = "channelId";
	public static final String ALIAS_OSTYPE = "pc,pad,weixin,mobile,app 五个平台";
	public static final String ALIAS_POSITION = "渲染位置,(上中下底)";
	public static final String ALIAS_CHANNELTYPE = "栏目类型分为 导航菜单(0) 内容类似标签(1) ";
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
	 * 网站ID
	 */
	private java.lang.String siteId;
	/**
	 * channelId
	 */
	private java.lang.String channelId;
	/**
	 * pc,pad,weixin,mobile,app 五个平台
	 */
	private java.lang.String ostype;
	/**
	 * 渲染位置,(上中下底)
	 */
	private java.lang.String position;
	/**
	 * 栏目类型分为 导航菜单(0) 内容类似标签(1) 
	 */
	private java.lang.Integer channeltype;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 0失效,1有效
	 */
	private java.lang.Integer state;
	
	//栏目link
	private String linkurl;
	
	//columns END 数据库字段结束
	
	//concstructor

	public CmsSiteChannel(){
	}

	public CmsSiteChannel(
		java.lang.String id
	){
		this.id = id;
	}
    @Transient 
	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	//get and set
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:CmsSiteChannel_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsSiteChannel_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setChannelId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.channelId = value;
	}
	
     @WhereSQL(sql="channelId=:CmsSiteChannel_channelId")
	public java.lang.String getChannelId() {
		return this.channelId;
	}
	public void setOstype(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ostype = value;
	}
	
     @WhereSQL(sql="ostype=:CmsSiteChannel_ostype")
	public java.lang.String getOstype() {
		return this.ostype;
	}
	public void setPosition(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.position = value;
	}
	
     @WhereSQL(sql="position=:CmsSiteChannel_position")
	public java.lang.String getPosition() {
		return this.position;
	}
	public void setChanneltype(java.lang.Integer value) {
		this.channeltype = value;
	}
	
     @WhereSQL(sql="channeltype=:CmsSiteChannel_channeltype")
	public java.lang.Integer getChanneltype() {
		return this.channeltype;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
     @WhereSQL(sql="sort=:CmsSiteChannel_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:CmsSiteChannel_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("网站ID[").append(getSiteId()).append("],")
			.append("channelId[").append(getChannelId()).append("],")
			.append("pc,pad,weixin,mobile,app 五个平台[").append(getOstype()).append("],")
			.append("渲染位置,(上中下底)[").append(getPosition()).append("],")
			.append("栏目类型分为 导航菜单(0) 内容类似标签(1) [").append(getChanneltype()).append("],")
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
		if(obj instanceof CmsSiteChannel == false) return false;
		if(this == obj) return true;
		CmsSiteChannel other = (CmsSiteChannel)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
