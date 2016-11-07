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
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2014-10-21 09:36:16
 * @see com.centfor.cms.entity.CmsProperty
 */
@Table(name = "cms_property")
public class CmsProperty extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// alias
	/*
	 * public static final String TABLE_ALIAS = "CmsProperty"; public static
	 * final String ALIAS_ID = "siteId_code,保证一个站点下code唯一"; public static final
	 * String ALIAS_SITEID = "siteId"; public static final String ALIAS_NAME =
	 * "name"; public static final String ALIAS_CODE = "系统级别的编码,一个站点不可重复";
	 * public static final String ALIAS_INPUTTYPE =
	 * "text,date,datatime,int,float,select,file,img,imgs"; public static final
	 * String ALIAS_BUSINESSID = "业务Id"; public static final String
	 * ALIAS_MODELTYPE = "site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)";
	 * public static final String ALIAS_CREATEPERSON = "创建人"; public static
	 * final String ALIAS_CREATEDATE = "创建时间"; public static final String
	 * ALIAS_DEFAULTVALUE = "默认值"; public static final String ALIAS_STYLE =
	 * "样式"; public static final String ALIAS_SORT = "排序"; public static final
	 * String ALIAS_STATE = "0失效,1有效";
	 */
	// date formats
	// public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;

	// columns START
	/**
	 * siteId_code,保证一个站点下code唯一
	 */
	private java.lang.String id;
	/**
	 * siteId
	 */
	private java.lang.String siteId;
	/**
	 * name
	 */
	private java.lang.String name;
	/**
	 * 系统级别的编码,一个站点不可重复
	 */
	private java.lang.String code;
	/**
	 * text,date,datatime,int,float,select,file,img,imgs
	 */
	private java.lang.String inputType;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)
	 */
	private java.lang.String modelType;
	/**
	 * 创建人
	 */
	private java.lang.String createPerson;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 默认值
	 */
	private java.lang.String defaultValue;
	/**
	 * 样式
	 */
	private java.lang.String style;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 0失效,1有效
	 */
	private java.lang.Integer state;
	/** 是否允许被继承 */
	private String isextend;
	/** pvalue */
	private String pvalue;

	private String selectValues;

	private String bvalue;

	// columns END 数据库字段结束
	/**
	 * bvalue
	 * 在微信页面上的显示
	 */
	private String bvalueString;
	// concstructor
	@Transient
	public String getBvalueString() {
		if(StringUtils.isNotBlank(getBvalue())){
			bvalueString="";
			String[] ss=getBvalue().split("\r");
			for(String s:ss){
				bvalueString +=s+"<br>";
			}
		}
		return bvalueString;
	}

	public void setBvalueString(String bvalueString) {
		this.bvalueString = bvalueString;
	}

	public CmsProperty() {
	}

	public String getIsextend() {
		return isextend;
	}

	public void setIsextend(String isextend) {
		this.isextend = isextend;
	}

	public String getPvalue() {
		return pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public CmsProperty(java.lang.String id) {
		this.id = id;
	}

	// get and set
	public void setId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.id = value;
	}

	@Id
	@WhereSQL(sql = "id=:CmsProperty_id")
	public java.lang.String getId() {
		return this.id;
	}

	public void setSiteId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.siteId = value;
	}

	@WhereSQL(sql = "siteId=:CmsProperty_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}

	public void setName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:CmsProperty_name")
	public java.lang.String getName() {
		return this.name;
	}

	public void setCode(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.code = value;
	}

	@WhereSQL(sql = "code=:CmsProperty_code")
	public java.lang.String getCode() {
		return this.code;
	}

	public void setInputType(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.inputType = value;
	}

	@WhereSQL(sql = "inputType=:CmsProperty_inputType")
	public java.lang.String getInputType() {
		return this.inputType;
	}

	public void setBusinessId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.businessId = value;
	}

	@WhereSQL(sql = "businessId=:CmsProperty_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}

	public void setModelType(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.modelType = value;
	}

	@WhereSQL(sql = "modelType=:CmsProperty_modelType")
	public java.lang.String getModelType() {
		return this.modelType;
	}

	public void setCreatePerson(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.createPerson = value;
	}

	@WhereSQL(sql = "createPerson=:CmsProperty_createPerson")
	public java.lang.String getCreatePerson() {
		return this.createPerson;
	}

	/*
	 * public String getcreateDateString() { return
	 * DateUtils.convertDate2String(FORMAT_CREATEDATE, getcreateDate()); }
	 * public void setcreateDateString(String value) throws ParseException{
	 * setcreateDate(DateUtils.convertString2Date(FORMAT_CREATEDATE,value)); }
	 */

	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}

	@WhereSQL(sql = "createDate=:CmsProperty_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setDefaultValue(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.defaultValue = value;
	}

	@WhereSQL(sql = "defaultValue=:CmsProperty_defaultValue")
	public java.lang.String getDefaultValue() {
		return this.defaultValue;
	}

	public void setStyle(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.style = value;
	}

	@WhereSQL(sql = "style=:CmsProperty_style")
	public java.lang.String getStyle() {
		return this.style;
	}

	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}

	@WhereSQL(sql = "sort=:CmsProperty_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setState(java.lang.Integer value) {
		this.state = value;
	}

	@WhereSQL(sql = "state=:CmsProperty_state")
	public java.lang.Integer getState() {
		return this.state;
	}

	@WhereSQL(sql = "selectValues=:CmsProperty_selectValues")
	public String getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}

	public String toString() {
		return new StringBuffer().append("siteId_code,保证一个站点下code唯一[")
				.append(getId()).append("],").append("siteId[")
				.append(getSiteId()).append("],").append("name[")
				.append(getName()).append("],").append("系统级别的编码,一个站点不可重复[")
				.append(getCode()).append("],")
				.append("text,date,datatime,int,float,select,file,img,imgs[")
				.append(getInputType()).append("],").append("业务Id[")
				.append(getBusinessId()).append("],")
				.append("site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)[")
				.append(getModelType()).append("],").append("创建人[")
				.append(getCreatePerson()).append("],").append("创建时间[")
				.append(getCreateDate()).append("],").append("默认值[")
				.append(getDefaultValue()).append("],").append("样式[")
				.append(getStyle()).append("],").append("排序[")
				.append(getSort()).append("],").append("0失效,1有效[")
				.append(getState()).append("],").toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof CmsProperty == false)
			return false;
		if (this == obj)
			return true;
		CmsProperty other = (CmsProperty) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
	@Transient
	public String getBvalue() {
		return bvalue;
	}

	public void setBvalue(String bvalue) {
		this.bvalue = bvalue;
	}

	@Transient
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
