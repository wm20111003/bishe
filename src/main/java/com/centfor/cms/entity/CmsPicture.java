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
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2014-10-21 09:36:16
 * @see com.centfor.cms.entity.CmsPicture
 */
@Table(name="cms_picture")
public class CmsPicture  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CmsPicture";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SITEID = "站点Id";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_PROPERTYCODE = "扩展属性code";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_FILEPATH = "文件物理路径";
	public static final String ALIAS_IMGTYPE = "图片类型";
	public static final String ALIAS_PICTUREURL = "缩略图";
	public static final String ALIAS_MIDDLEPICTUREURL = "中图";
	public static final String ALIAS_SMALLPICTUREURL = "小图";
	public static final String ALIAS_CREATEDATE = "创建时间";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_MODELTYPE = "site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_LOOKCOUNT = "打开次数";
	public static final String ALIAS_STATE = "state";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 站点Id
	 */
	private java.lang.String siteId;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 扩展属性code
	 */
	private java.lang.String propertyCode;
	/**
	 * 名称
	 */
	private java.lang.String name;
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
	private java.lang.String pictureUrl;
	/**
	 * 中图
	 */
	private java.lang.String middlePictureUrl;
	/**
	 * 小图
	 */
	private java.lang.String smallPictureUrl;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)
	 */
	private java.lang.String modelType;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * state
	 */
	private java.lang.Integer state;
	
	/**
	 * 是否是主图(0否,1是)
	 */
	private java.lang.Integer ismain=0;
	
	//columns END 数据库字段结束
	
	// 关联该图片的links
	List<CmsLink> pictureLinks;
	
	//concstructor

	public CmsPicture(){
	}

	public CmsPicture(
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
     @WhereSQL(sql="id=:CmsPicture_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsPicture_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsPicture_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setPropertyCode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.propertyCode = value;
	}
	
     @WhereSQL(sql="propertyCode=:CmsPicture_propertyCode")
	public java.lang.String getPropertyCode() {
		return this.propertyCode;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:CmsPicture_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setFilepath(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.filepath = value;
	}
	
     @WhereSQL(sql="filepath=:CmsPicture_filepath")
	public java.lang.String getFilepath() {
		return this.filepath;
	}
	public void setImgtype(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.imgtype = value;
	}
	
     @WhereSQL(sql="imgtype=:CmsPicture_imgtype")
	public java.lang.String getImgtype() {
		return this.imgtype;
	}
	public void setPictureUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.pictureUrl = value;
	}
	
     @WhereSQL(sql="pictureUrl=:CmsPicture_pictureUrl")
	public java.lang.String getPictureUrl() {
		return this.pictureUrl;
	}
	public void setMiddlePictureUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.middlePictureUrl = value;
	}
	
     @WhereSQL(sql="middlePictureUrl=:CmsPicture_middlePictureUrl")
	public java.lang.String getMiddlePictureUrl() {
		return this.middlePictureUrl;
	}
	public void setSmallPictureUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.smallPictureUrl = value;
	}
	
     @WhereSQL(sql="smallPictureUrl=:CmsPicture_smallPictureUrl")
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
	
     @WhereSQL(sql="createDate=:CmsPicture_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setRemark(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.remark = value;
	}
	
     @WhereSQL(sql="remark=:CmsPicture_remark")
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setModelType(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.modelType = value;
	}
	
     @WhereSQL(sql="modelType=:CmsPicture_modelType")
	public java.lang.String getModelType() {
		return this.modelType;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
     @WhereSQL(sql="sort=:CmsPicture_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}
	
     @WhereSQL(sql="lookcount=:CmsPicture_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:CmsPicture_state")
	public java.lang.Integer getState() {
		return this.state;
	}
     public java.lang.Integer getIsmain() {
		return ismain;
	}

	public void setIsmain(java.lang.Integer ismain) {
		this.ismain = ismain;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
     public List<CmsLink> getPictureLinks() {
 		return pictureLinks;
 	}

 	public void setPictureLinks(List<CmsLink> pictureLinks) {
 		this.pictureLinks = pictureLinks;
 	}
 	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("站点Id[").append(getSiteId()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("扩展属性code[").append(getPropertyCode()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("文件物理路径[").append(getFilepath()).append("],")
			.append("图片类型[").append(getImgtype()).append("],")
			.append("缩略图[").append(getPictureUrl()).append("],")
			.append("中图[").append(getMiddlePictureUrl()).append("],")
			.append("小图[").append(getSmallPictureUrl()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
			.append("备注[").append(getRemark()).append("],")
			.append("site,channel,content(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)[").append(getModelType()).append("],")
			.append("排序[").append(getSort()).append("],")
			.append("打开次数[").append(getLookcount()).append("],")
			.append("state[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsPicture == false) return false;
		if(this == obj) return true;
		CmsPicture other = (CmsPicture)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
