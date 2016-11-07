package com.centfor.school.entity;

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
 * @author centfor<Auto generate>
 * @version  2015-04-29 16:06:13
 * @see com.centfor.school.entity.WorkDocument
 */
@Table(name="work_document")
public class WorkDocument  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "WorkDocument";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "用户id";
	public static final String ALIAS_FTLNAME = "文件名";
	public static final String ALIAS_FTLPATH = "文件路径";
	public static final String ALIAS_CREATEDATE = "创建时间";
	public static final String ALIAS_AUTHOR = "上传人";
	public static final String ALIAS_BAK = "bak";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 用户id
	 */
	private java.lang.String userId;
	/**
	 * 文件名
	 */
	private java.lang.String ftlName;
	/**
	 * 文件路径
	 */
	private java.lang.String ftlPath;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 上传人
	 */
	private java.lang.String author;
	/**
	 * bak
	 */
	private java.lang.String bak;
	//columns END 数据库字段结束
	
	//concstructor

	public WorkDocument(){
	}

	public WorkDocument(
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
     @WhereSQL(sql="id=:WorkDocument_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:WorkDocument_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setFtlName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ftlName = value;
	}
	
     @WhereSQL(sql="ftlName=:WorkDocument_ftlName")
	public java.lang.String getFtlName() {
		return this.ftlName;
	}
	public void setFtlPath(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ftlPath = value;
	}
	
     @WhereSQL(sql="ftlPath=:WorkDocument_ftlPath")
	public java.lang.String getFtlPath() {
		return this.ftlPath;
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
	
     @WhereSQL(sql="createDate=:WorkDocument_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setAuthor(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.author = value;
	}
	
     @WhereSQL(sql="author=:WorkDocument_author")
	public java.lang.String getAuthor() {
		return this.author;
	}
	public void setBak(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.bak = value;
	}
	
     @WhereSQL(sql="bak=:WorkDocument_bak")
	public java.lang.String getBak() {
		return this.bak;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("用户id[").append(getUserId()).append("],")
			.append("文件名[").append(getFtlName()).append("],")
			.append("文件路径[").append(getFtlPath()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
			.append("上传人[").append(getAuthor()).append("],")
			.append("bak[").append(getBak()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof WorkDocument == false) return false;
		if(this == obj) return true;
		WorkDocument other = (WorkDocument)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
