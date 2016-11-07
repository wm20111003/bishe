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
 * @see com.centfor.cms.entity.CmsLink
 */
@Table(name="cms_link")
public class CmsLink  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CmsLink";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_DEFAULTLINK = "默认URL地址";
	public static final String ALIAS_LINK = "使用的URL";
	public static final String ALIAS_SITEID = "网站ID";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_LOOKCOUNT = "打开次数";
	public static final String ALIAS_OSTYPE = "pc,pad,weixin,mobile,app 五个平台";
	public static final String ALIAS_MODELTYPE = "site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)";
	public static final String ALIAS_FTLFILE = "当前渲染使用的模板路径";
	public static final String ALIAS_NODEFTLFILE = "子内容使用的ftl模板文件";
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
	 * name
	 */
	private java.lang.String name;
	/**
	 * 默认URL地址
	 */
	private java.lang.String defaultLink;
	/**
	 * 使用的URL
	 */
	private java.lang.String link;
	/**
	 * 网站ID
	 */
	private java.lang.String siteId;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * pc,pad,weixin,mobile,app 五个平台
	 */
	private java.lang.String ostype;
	/**
	 * site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)
	 */
	private java.lang.String modelType;
	/**
	 * 当前渲染使用的模板路径
	 */
	private java.lang.String ftlfile;
	/**
	 * 子内容使用的ftl模板文件
	 */
	private java.lang.String nodeftlfile;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 0失效,1有效
	 */
	private java.lang.Integer state;
	/**
	 * 是否外部链接
	 */
	private String isoutlink;
	/**
	 * 打开方式
	 */
	private String opentype;
	private String ftlId;
	private String nodeftlId;
	
	//columns END 数据库字段结束
	
	//concstructor

	public CmsLink(){
	}

	public CmsLink(
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
	
	public String getFtlId() {
		return ftlId;
	}

	public void setFtlId(String ftlId) {
		this.ftlId = ftlId;
	}

	public String getNodeftlId() {
		return nodeftlId;
	}

	public void setNodeftlId(String nodeftlId) {
		this.nodeftlId = nodeftlId;
	}

	public String getIsoutlink() {
		return isoutlink;
	}

	public void setIsoutlink(String isoutlink) {
		this.isoutlink = isoutlink;
	}

	public String getOpentype() {
		return opentype;
	}

	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}

	@Id
     @WhereSQL(sql="id=:CmsLink_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:CmsLink_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setDefaultLink(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.defaultLink = value;
	}
	
     @WhereSQL(sql="defaultLink=:CmsLink_defaultLink")
	public java.lang.String getDefaultLink() {
		return this.defaultLink;
	}
	public void setLink(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.link = value;
	}
	
     @WhereSQL(sql="link=:CmsLink_link")
	public java.lang.String getLink() {
		return this.link;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsLink_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsLink_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}
	
     @WhereSQL(sql="lookcount=:CmsLink_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}
	public void setOstype(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ostype = value;
	}
	
     @WhereSQL(sql="ostype=:CmsLink_ostype")
	public java.lang.String getOstype() {
		return this.ostype;
	}
	public void setModelType(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.modelType = value;
	}
	
     @WhereSQL(sql="modelType=:CmsLink_modelType")
	public java.lang.String getModelType() {
		return this.modelType;
	}
	public void setFtlfile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ftlfile = value;
	}
	
     @WhereSQL(sql="ftlfile=:CmsLink_ftlfile")
	public java.lang.String getFtlfile() {
		return this.ftlfile;
	}
	public void setNodeftlfile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.nodeftlfile = value;
	}
	
     @WhereSQL(sql="nodeftlfile=:CmsLink_nodeftlfile")
	public java.lang.String getNodeftlfile() {
		return this.nodeftlfile;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
     @WhereSQL(sql="sort=:CmsLink_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:CmsLink_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("默认URL地址[").append(getDefaultLink()).append("],")
			.append("使用的URL[").append(getLink()).append("],")
			.append("网站ID[").append(getSiteId()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("打开次数[").append(getLookcount()).append("],")
			.append("pc,pad,weixin,mobile,app 五个平台[").append(getOstype()).append("],")
			.append("site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)[").append(getModelType()).append("],")
			.append("当前渲染使用的模板路径[").append(getFtlfile()).append("],")
			.append("子内容使用的ftl模板文件[").append(getNodeftlfile()).append("],")
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
		if(obj instanceof CmsLink == false) return false;
		if(this == obj) return true;
		CmsLink other = (CmsLink)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
