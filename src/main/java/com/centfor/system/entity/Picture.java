package com.centfor.system.entity;

import java.text.ParseException;
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
 * @version  2014-08-20 22:48:34
 * @see com.centfor.system.entity.Picture
 */
@Table(name="bbz_picture")
public class Picture  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "图片表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_URLPATH = "网络路径";
	public static final String ALIAS_FILEPATH = "文件物理路径";
	public static final String ALIAS_IMGTYPE = "图片类型";
	public static final String ALIAS_SMALLPICTUREURL = "缩略图";
	public static final String ALIAS_CREATEDATE = "创建时间";
	public static final String ALIAS_CREATEPERSON = "创建人";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_REMARKER = "备注";
	public static final String ALIAS_SORT = "排序";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	
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
	 * 网络路径
	 */
	private java.lang.String urlpath;
	/**
	 * 文件物理路径
	 */
	private java.lang.String filepath;
	/**
	 * 图片类型
	 */
	private java.lang.String imgtype;
	/**
	 * 缩略图
	 */
	private java.lang.String smallPictureUrl;
	
	private String middlePictureUrl;
	
	public String getMiddlePictureUrl() {
		return middlePictureUrl;
	}

	public void setMiddlePictureUrl(String middlePictureUrl) {
		this.middlePictureUrl = middlePictureUrl;
	}

	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 创建人
	 */
	private java.lang.String createPerson;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 备注
	 */
	private java.lang.String remarker;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	//columns END 数据库字段结束
	
	//concstructor

	public Picture(){
	}

	public Picture(
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
     @WhereSQL(sql="id=:Picture_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:Picture_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setUrlpath(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.urlpath = value;
	}
	
     @WhereSQL(sql="urlpath=:Picture_urlpath")
	public java.lang.String getUrlpath() {
		return this.urlpath;
	}
	public void setFilepath(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.filepath = value;
	}
	
     @WhereSQL(sql="filepath=:Picture_filepath")
	public java.lang.String getFilepath() {
		return this.filepath;
	}
	public void setImgtype(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.imgtype = value;
	}
	
     @WhereSQL(sql="imgtype=:Picture_imgtype")
	public java.lang.String getImgtype() {
		return this.imgtype;
	}
	public void setSmallPictureUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.smallPictureUrl = value;
	}
	
     @WhereSQL(sql="smallPictureUrl=:Picture_smallPictureUrl")
	public java.lang.String getSmallPictureUrl() {
		return this.smallPictureUrl;
	}
		/*
	public String getcreateDateString() {
		return DateUtils.convertDate2String(FORMAT_CREATEDATE, getcreateDate());
	}
	public void setcreateDateString(String value) throws ParseException{
		setcreateDate(DateUtils.convertString2Date(FORMAT_CREATEDATE,value));
	}*/
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
     @WhereSQL(sql="createDate=:Picture_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setCreatePerson(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.createPerson = value;
	}
	
     @WhereSQL(sql="createPerson=:Picture_createPerson")
	public java.lang.String getCreatePerson() {
		return this.createPerson;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:Picture_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setRemarker(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.remarker = value;
	}
	
     @WhereSQL(sql="remarker=:Picture_remarker")
	public java.lang.String getRemarker() {
		return this.remarker;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
     @WhereSQL(sql="sort=:Picture_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("网络路径[").append(getUrlpath()).append("],")
			.append("文件物理路径[").append(getFilepath()).append("],")
			.append("图片类型[").append(getImgtype()).append("],")
			.append("缩略图[").append(getSmallPictureUrl()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
			.append("创建人[").append(getCreatePerson()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("备注[").append(getRemarker()).append("],")
			.append("排序[").append(getSort()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Picture == false) return false;
		if(this == obj) return true;
		Picture other = (Picture)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
