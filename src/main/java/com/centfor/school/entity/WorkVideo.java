package com.centfor.school.entity;

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
 * @author centfor<Auto generate>
 * @version  2015-05-05 21:53:10
 * @see com.centfor.school.entity.WorkVideo
 */
@Table(name="work_video")
public class WorkVideo  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "WorkVideo";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_FTLNAME = "ftlName";
	public static final String ALIAS_FTLPATH = "ftlPath";
	public static final String ALIAS_AUTHOR = "author";
	public static final String ALIAS_CREATEDATE = "createDate";
	public static final String ALIAS_SITEID = "siteId";
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
	 * ftlName
	 */
	private java.lang.String ftlName;
	/**
	 * ftlPath
	 */
	private java.lang.String ftlPath;
	/**
	 * author
	 */
	private java.lang.String author;
	/**
	 * createDate
	 */
	private java.util.Date createDate;
	/**
	 * siteId
	 */
	private java.lang.String siteId;
	/**
	 * bak
	 */
	private java.lang.String bak;
	//columns END 数据库字段结束
	
	//concstructor

	public WorkVideo(){
	}

	public WorkVideo(
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
     @WhereSQL(sql="id=:WorkVideo_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setFtlName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ftlName = value;
	}
	
     @WhereSQL(sql="ftlName=:WorkVideo_ftlName")
	public java.lang.String getFtlName() {
		return this.ftlName;
	}
	public void setFtlPath(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ftlPath = value;
	}
	
     @WhereSQL(sql="ftlPath=:WorkVideo_ftlPath")
	public java.lang.String getFtlPath() {
		return this.ftlPath;
	}
	public void setAuthor(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.author = value;
	}
	
     @WhereSQL(sql="author=:WorkVideo_author")
	public java.lang.String getAuthor() {
		return this.author;
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
	
     @WhereSQL(sql="createDate=:WorkVideo_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:WorkVideo_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setBak(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.bak = value;
	}
	
     @WhereSQL(sql="bak=:WorkVideo_bak")
	public java.lang.String getBak() {
		return this.bak;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("ftlName[").append(getFtlName()).append("],")
			.append("ftlPath[").append(getFtlPath()).append("],")
			.append("author[").append(getAuthor()).append("],")
			.append("createDate[").append(getCreateDate()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("bak[").append(getBak()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof WorkVideo == false) return false;
		if(this == obj) return true;
		WorkVideo other = (WorkVideo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
