package com.centfor.cms.entity;

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
 * @see com.centfor.cms.entity.CmsTheme
 */
@Table(name="cms_theme")
public class CmsTheme  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CmsTheme";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_DESCRIPTION = "备注";
	public static final String ALIAS_FTLFILE = "渲染使用的模板路径";
	public static final String ALIAS_IMGFILE = "缩略图路径路径";
	public static final String ALIAS_MODELTYPE = "site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)";
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
	 * 备注
	 */
	private java.lang.String description;
	/**
	 * 渲染使用的模板路径
	 */
	private java.lang.String ftlfile;
	/**
	 * 缩略图路径路径
	 */
	private java.lang.String imgfile;
	/**
	 * site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)
	 */
	private java.lang.String modelType;
	/**
	 * 使用次数
	 */
	private java.lang.Integer usecount;
	/**
	 * pc,pad,weixin,mobile,app 五个平台的linkURL
	 */
	private java.lang.String ostype;
	/**
	 * 状态 0关闭,1开启
	 */
	private java.lang.Integer state;
	//columns END 数据库字段结束
		
	private String qgroup;//查询条件 组
	//concstructor

	public CmsTheme(){
	}
	
	public CmsTheme(
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
	
    @Transient
	public String getQgroup() {
		return qgroup;
	}

	public void setQgroup(String qgroup) {
		this.qgroup = qgroup;
	}


	@Id
     @WhereSQL(sql="id=:CmsTheme_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name like %:CmsTheme_name%")
	public java.lang.String getName() {
		return this.name;
	}
	public void setDescription(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.description = value;
	}
	
     @WhereSQL(sql="description=:CmsTheme_description")
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setFtlfile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ftlfile = value;
	}
	
     @WhereSQL(sql="ftlfile=:CmsTheme_ftlfile")
	public java.lang.String getFtlfile() {
		return this.ftlfile;
	}
	public void setImgfile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.imgfile = value;
	}
	
     @WhereSQL(sql="imgfile=:CmsTheme_imgfile")
	public java.lang.String getImgfile() {
		return this.imgfile;
	}
	public void setModelType(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.modelType = value;
	}
	
     @WhereSQL(sql="modelType=:CmsTheme_modelType")
	public java.lang.String getModelType() {
		return this.modelType;
	}
	public void setUsecount(java.lang.Integer value) {
		this.usecount = value;
	}
	
     @WhereSQL(sql="usecount=:CmsTheme_usecount")
	public java.lang.Integer getUsecount() {
		return this.usecount;
	}
	public void setOstype(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ostype = value;
	}
	
     @WhereSQL(sql="ostype=:CmsTheme_ostype")
	public java.lang.String getOstype() {
		return this.ostype;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:CmsTheme_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("备注[").append(getDescription()).append("],")
			.append("渲染使用的模板路径[").append(getFtlfile()).append("],")
			.append("缩略图路径路径[").append(getImgfile()).append("],")
			.append("site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)[").append(getModelType()).append("],")
			.append("使用次数[").append(getUsecount()).append("],")
			.append("pc,pad,weixin,mobile,app 五个平台的linkURL[").append(getOstype()).append("],")
			.append("状态 0关闭,1开启[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsTheme == false) return false;
		if(this == obj) return true;
		CmsTheme other = (CmsTheme)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
