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
 * @version  2015-04-20 21:49:19
 * @see com.centfor.school.entity.Record
 */
@Table(name="record")
public class Record  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Record";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "关联用户表的Id";
	public static final String ALIAS_STARTDATE = "入学时间";
	public static final String ALIAS_ENDDATE = "毕业时间";
	public static final String ALIAS_YJENDDATE = "预计毕业时间";
	public static final String ALIAS_DEGREE = "学位";
	public static final String ALIAS_SCHOOLNAME = "学校名";
	public static final String ALIAS_ADDRESS = "住址";
	public static final String ALIAS_BIRTHDAY = "出生年月日";
	public static final String ALIAS_POLITICALSTATUS = "政治面貌";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_BAK = "bak";
    */
	//date formats
	//public static final String FORMAT_STARTDATE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_ENDDATE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_YJENDDATE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_BIRTHDAY = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 关联用户表的Id
	 */
	private java.lang.String userId;
	/**
	 * 入学时间
	 */
	private java.util.Date startDate;
	/**
	 * 毕业时间
	 */
	private java.util.Date endDate;
	/**
	 * 预计毕业时间
	 */
	private java.util.Date yjEndDate;
	/**
	 * 学位
	 */
	private java.lang.String degree;
	/**
	 * 学校名
	 */
	private java.lang.String schoolName;
	/**
	 * 住址
	 */
	private java.lang.String address;
	/**
	 * 出生年月日
	 */
	private java.util.Date birthday;
	/**
	 * 政治面貌
	 */
	private java.lang.String politicalStatus;
	/**
	 * 性别
	 */
	private java.lang.String sex;
	/**
	 * bak
	 */
	private java.lang.String bak;
	//columns END 数据库字段结束
	
	//concstructor

	public Record(){
	}

	public Record(
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
     @WhereSQL(sql="id=:Record_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:Record_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
		/*
	public String getstartDateString() {
		return DateUtils.convertDate2String(FORMAT_STARTDATE, getstartDate());
	}
	public void setstartDateString(String value) throws ParseException{
		setstartDate(DateUtils.convertString2Date(FORMAT_STARTDATE,value));
	}*/
	
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}
	
     @WhereSQL(sql="startDate=:Record_startDate")
	public java.util.Date getStartDate() {
		return this.startDate;
	}
		/*
	public String getendDateString() {
		return DateUtils.convertDate2String(FORMAT_ENDDATE, getendDate());
	}
	public void setendDateString(String value) throws ParseException{
		setendDate(DateUtils.convertString2Date(FORMAT_ENDDATE,value));
	}*/
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
     @WhereSQL(sql="endDate=:Record_endDate")
	public java.util.Date getEndDate() {
		return this.endDate;
	}
		/*
	public String getyjEndDateString() {
		return DateUtils.convertDate2String(FORMAT_YJENDDATE, getyjEndDate());
	}
	public void setyjEndDateString(String value) throws ParseException{
		setyjEndDate(DateUtils.convertString2Date(FORMAT_YJENDDATE,value));
	}*/
	
	public void setYjEndDate(java.util.Date value) {
		this.yjEndDate = value;
	}
	
     @WhereSQL(sql="yjEndDate=:Record_yjEndDate")
	public java.util.Date getYjEndDate() {
		return this.yjEndDate;
	}
	public void setDegree(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.degree = value;
	}
	
     @WhereSQL(sql="degree=:Record_degree")
	public java.lang.String getDegree() {
		return this.degree;
	}
	public void setSchoolName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.schoolName = value;
	}
	
     @WhereSQL(sql="schoolName=:Record_schoolName")
	public java.lang.String getSchoolName() {
		return this.schoolName;
	}
	public void setAddress(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.address = value;
	}
	
     @WhereSQL(sql="address=:Record_address")
	public java.lang.String getAddress() {
		return this.address;
	}
		/*
	public String getbirthdayString() {
		return DateUtils.convertDate2String(FORMAT_BIRTHDAY, getbirthday());
	}
	public void setbirthdayString(String value) throws ParseException{
		setbirthday(DateUtils.convertString2Date(FORMAT_BIRTHDAY,value));
	}*/
	
	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}
	
     @WhereSQL(sql="birthday=:Record_birthday")
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	public void setPoliticalStatus(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.politicalStatus = value;
	}
	
     @WhereSQL(sql="politicalStatus=:Record_politicalStatus")
	public java.lang.String getPoliticalStatus() {
		return this.politicalStatus;
	}
	public void setSex(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.sex = value;
	}
	
     @WhereSQL(sql="sex=:Record_sex")
	public java.lang.String getSex() {
		return this.sex;
	}
	public void setBak(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.bak = value;
	}
	
     @WhereSQL(sql="bak=:Record_bak")
	public java.lang.String getBak() {
		return this.bak;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("关联用户表的Id[").append(getUserId()).append("],")
			.append("入学时间[").append(getStartDate()).append("],")
			.append("毕业时间[").append(getEndDate()).append("],")
			.append("预计毕业时间[").append(getYjEndDate()).append("],")
			.append("学位[").append(getDegree()).append("],")
			.append("学校名[").append(getSchoolName()).append("],")
			.append("住址[").append(getAddress()).append("],")
			.append("出生年月日[").append(getBirthday()).append("],")
			.append("政治面貌[").append(getPoliticalStatus()).append("],")
			.append("性别[").append(getSex()).append("],")
			.append("bak[").append(getBak()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Record == false) return false;
		if(this == obj) return true;
		Record other = (Record)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
