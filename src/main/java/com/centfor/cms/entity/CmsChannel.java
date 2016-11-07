package com.centfor.cms.entity;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.centfor.frame.annotation.WhereSQL;
import com.centfor.frame.entity.BaseEntity;
import com.centfor.system.entity.Picture;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2014-10-21 09:36:14
 * @see com.centfor.cms.entity.CmsChannel
 */
@Table(name = "cms_channel")
public class CmsChannel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// alias
	/*
	 * public static final String TABLE_ALIAS = "栏目表"; public static final
	 * String ALIAS_ID = "id"; public static final String ALIAS_PID = "上级Id";
	 * public static final String ALIAS_NAME = "名称"; public static final String
	 * ALIAS_TITLE = "标题"; public static final String ALIAS_KEYWORDS = "关键字";
	 * public static final String ALIAS_DESCRIPTION = "描述"; public static final
	 * String ALIAS_LOOKCOUNT = "打开次数";
	 */
	// date formats

	// columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 上级Id
	 */
	private java.lang.String pid;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 标题
	 */
	private java.lang.String title;
	/**
	 * 关键字
	 */
	private java.lang.String keywords;
	/**
	 * 描述
	 */
	private java.lang.String description;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * 栏目分类
	 */
	private String channelSort;
	/**
	 * 栏目图标
	 */
	private String channelIcon;
	
	/***
	 * 栏目内容
	 */
	private String channelContent;
	/**
	 * 要求登录
	 */
	private String loginReq;
	// columns END 数据库字段结束
	// 使用该栏目的所有站点

	private List<CmsSite> channelSites;

	private List<CmsChannel> leafChannel;

	// 查询条件
	private String siteId;
	private String ostype;
	private String channelType;
	private String linkUrl;
	private String opentype;
	private String position;
	private CmsChannel parentChannel;
	private CmsPicture pic;

	// concstructor




	public CmsChannel() {
	}

	public String getChannelSort() {
		return channelSort;
	}

	public void setChannelSort(String channelSort) {
		this.channelSort = channelSort;
	}

	public CmsChannel(java.lang.String id) {
		this.id = id;
	}
	@WhereSQL(sql = "channelContent=:CmsChannel_channelContent")
	public String getChannelContent() {
		return channelContent;
	}

	public void setChannelContent(String channelContent) {
		this.channelContent = channelContent;
	}

	// get and set
	public void setId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.id = value;
	}
	@WhereSQL(sql = "channelIcon=:CmsChannel_channelIcon")
	public String getChannelIcon() {
		return channelIcon;
	}

	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}

	@Id
	@WhereSQL(sql = "id=:CmsChannel_id")
	public java.lang.String getId() {
		return this.id;
	}
    @Transient
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
    @Transient
	public String getOpentype() {
		return opentype;
	}

	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}

	@Transient
	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public void setPid(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.pid = value;
	}
    @Transient
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	@Transient
	public CmsChannel getParentChannel() {
		return parentChannel;
	}

	public void setParentChannel(CmsChannel parentChannel) {
		this.parentChannel = parentChannel;
	}

	
	@WhereSQL(sql = "pid=:CmsChannel_pid")
	public java.lang.String getPid() {
		return this.pid;
	}

	public void setName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.name = value;
	}

	@Transient
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@WhereSQL(sql = "name=:CmsChannel_name")
	public java.lang.String getName() {
		return this.name;
	}

	public void setTitle(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.title = value;
	}

	@WhereSQL(sql = "title=:CmsChannel_title")
	public java.lang.String getTitle() {
		return this.title;
	}

	public void setKeywords(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.keywords = value;
	}

	
	@WhereSQL(sql = "loginReq=:CmsChannel_loginReq")
	public String getLoginReq() {
		return loginReq;
	}

	public void setLoginReq(String loginReq) {
		this.loginReq = loginReq;
	}

	@WhereSQL(sql = "keywords=:CmsChannel_keywords")
	public java.lang.String getKeywords() {
		return this.keywords;
	}

	public void setDescription(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.description = value;
	}

	@WhereSQL(sql = "description=:CmsChannel_description")
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}

	@WhereSQL(sql = "lookcount=:CmsChannel_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}

	public String toString() {
		return new StringBuffer().append("id[").append(getId()).append("],")
				.append("上级Id[").append(getPid()).append("],").append("名称[")
				.append(getName()).append("],").append("标题[")
				.append(getTitle()).append("],").append("关键字[")
				.append(getKeywords()).append("],").append("描述[")
				.append(getDescription()).append("],").append("打开次数[")
				.append(getLookcount()).append("],").toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof CmsChannel == false)
			return false;
		if (this == obj)
			return true;
		CmsChannel other = (CmsChannel) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	@Transient
	public List<CmsChannel> getLeafChannel() {
		return leafChannel;
	}

	public void setLeafChannel(List<CmsChannel> leafChannel) {
		this.leafChannel = leafChannel;
	}

	@Transient
	public List<CmsSite> getChannelSites() {
		return channelSites;
	}

	public void setChannelSites(List<CmsSite> channelSites) {
		this.channelSites = channelSites;
	}
	@Transient
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	@Transient
	public CmsPicture getPic() {
		return pic;
	}

	public void setPic(CmsPicture pic) {
		this.pic = pic;
	}
	
}
