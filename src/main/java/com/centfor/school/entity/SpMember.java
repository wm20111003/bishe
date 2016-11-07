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
 * @version  2016-03-22 10:26:46
 * @see com.centfor.school.entity.SpMember
 */
@Table(name="sp_member")
public class SpMember  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_NAME = "姓名";
	public static final String ALIAS_ACCOUNT = "账号";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_AGE = "年龄";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_STUDENTID = "学号";
	public static final String ALIAS_MOBILE = "手机号码";
	public static final String ALIAS_EMAIL = "邮箱";
	public static final String ALIAS_GRADEID = "年级";
	public static final String ALIAS_CARDID = "身份证号";
	public static final String ALIAS_STATE = "是否有效";
	public static final String ALIAS_HEADIMGSRC = "头像";
	public static final String ALIAS_SCHOOLNAME = "院校名称";
	public static final String ALIAS_LENGTH = "学制";
	public static final String ALIAS_SITEID = "站点id";
	public static final String ALIAS_TESTID = "考生号";
	public static final String ALIAS_CREATEDATE = "注册时间";
	public static final String ALIAS_NATIONAL = "民族";
	public static final String ALIAS_LEVEL = "系";
	public static final String ALIAS_MAJORNAME = "专业名称";
	public static final String ALIAS_EDUCATION = "学历";
	public static final String ALIAS_BIRTHDAY = "出生日期";
	public static final String ALIAS_STARTDATE = "入学日期";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_BIRTHDAY = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_STARTDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 账号
	 */
	private java.lang.String account;
	/**
	 * 密码
	 */
	private java.lang.String password;
	/**
	 * 年龄
	 */
	private java.lang.Integer age;
	/**
	 * 性别
	 */
	private java.lang.String sex;
	/**
	 * 学号
	 */
	private java.lang.String studentId;
	/**
	 * 手机号码
	 */
	private java.lang.String mobile;
	/**
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 年级
	 */
	private java.lang.String gradeId;
	/**
	 * 身份证号
	 */
	private java.lang.String cardId;
	/**
	 * 是否有效
	 */
	private java.lang.String state;
	/**
	 * 头像
	 */
	private java.lang.String headimgsrc;
	/**
	 * 院校名称
	 */
	private java.lang.String schoolName;
	/**
	 * 学制
	 */
	private java.lang.Integer length;
	/**
	 * siteId
	 */
	private java.lang.String siteId;
	/**
	 * 考生号
	 */
	private java.lang.String testId;
	/**
	 * 注册时间
	 */
	private java.util.Date createDate;
	/**
	 * 民族
	 */
	private java.lang.String national;
	/**
	 * 系
	 */
	private java.lang.String level;
	/**
	 * 专业名称
	 */
	private java.lang.String majorName;
	/**
	 * 学历
	 */
	private java.lang.String education;
	/**
	 * 出生日期
	 */
	private java.util.Date birthday;
	/**
	 * 入学日期
	 */
	private java.util.Date startDate;
	//columns END 数据库字段结束
	
	//concstructor

	public SpMember(){
	}

	public SpMember(
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
     @WhereSQL(sql="id=:SpMember_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:SpMember_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setAccount(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.account = value;
	}
	
     @WhereSQL(sql="account=:SpMember_account")
	public java.lang.String getAccount() {
		return this.account;
	}
	public void setPassword(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.password = value;
	}
	
     @WhereSQL(sql="password=:SpMember_password")
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setAge(java.lang.Integer value) {
		this.age = value;
	}
	
     @WhereSQL(sql="age=:SpMember_age")
	public java.lang.Integer getAge() {
		return this.age;
	}
	public void setSex(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.sex = value;
	}
	
     @WhereSQL(sql="sex=:SpMember_sex")
	public java.lang.String getSex() {
		return this.sex;
	}
	public void setStudentId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.studentId = value;
	}
	
     @WhereSQL(sql="studentId=:SpMember_studentId")
	public java.lang.String getStudentId() {
		return this.studentId;
	}
	public void setMobile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.mobile = value;
	}
	
     @WhereSQL(sql="mobile=:SpMember_mobile")
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setEmail(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.email = value;
	}
	
     @WhereSQL(sql="email=:SpMember_email")
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setGradeId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.gradeId = value;
	}
	
     @WhereSQL(sql="gradeId=:SpMember_gradeId")
	public java.lang.String getGradeId() {
		return this.gradeId;
	}
	public void setCardId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.cardId = value;
	}
	
     @WhereSQL(sql="cardId=:SpMember_cardId")
	public java.lang.String getCardId() {
		return this.cardId;
	}
	public void setState(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.state = value;
	}
	
     @WhereSQL(sql="state=:SpMember_state")
	public java.lang.String getState() {
		return this.state;
	}
	public void setHeadimgsrc(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.headimgsrc = value;
	}
	
     @WhereSQL(sql="headimgsrc=:SpMember_headimgsrc")
	public java.lang.String getHeadimgsrc() {
		return this.headimgsrc;
	}
	public void setSchoolName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.schoolName = value;
	}
	
     @WhereSQL(sql="schoolName=:SpMember_schoolName")
	public java.lang.String getSchoolName() {
		return this.schoolName;
	}
	public void setLength(java.lang.Integer value) {
		this.length = value;
	}
	
     @WhereSQL(sql="length=:SpMember_length")
	public java.lang.Integer getLength() {
		return this.length;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:SpMember_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setTestId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.testId = value;
	}
	
     @WhereSQL(sql="testId=:SpMember_testId")
	public java.lang.String getTestId() {
		return this.testId;
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
	
     @WhereSQL(sql="createDate=:SpMember_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setNational(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.national = value;
	}
	
     @WhereSQL(sql="national=:SpMember_national")
	public java.lang.String getNational() {
		return this.national;
	}
	public void setLevel(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.level = value;
	}
	
     @WhereSQL(sql="level=:SpMember_level")
	public java.lang.String getLevel() {
		return this.level;
	}
	public void setMajorName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.majorName = value;
	}
	
     @WhereSQL(sql="majorName=:SpMember_majorName")
	public java.lang.String getMajorName() {
		return this.majorName;
	}
	public void setEducation(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.education = value;
	}
	
     @WhereSQL(sql="education=:SpMember_education")
	public java.lang.String getEducation() {
		return this.education;
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
	
     @WhereSQL(sql="birthday=:SpMember_birthday")
	public java.util.Date getBirthday() {
		return this.birthday;
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
	
     @WhereSQL(sql="startDate=:SpMember_startDate")
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("编号[").append(getId()).append("],")
			.append("姓名[").append(getName()).append("],")
			.append("账号[").append(getAccount()).append("],")
			.append("密码[").append(getPassword()).append("],")
			.append("年龄[").append(getAge()).append("],")
			.append("性别[").append(getSex()).append("],")
			.append("学号[").append(getStudentId()).append("],")
			.append("手机号码[").append(getMobile()).append("],")
			.append("邮箱[").append(getEmail()).append("],")
			.append("年级[").append(getGradeId()).append("],")
			.append("身份证号[").append(getCardId()).append("],")
			.append("是否有效[").append(getState()).append("],")
			.append("头像[").append(getHeadimgsrc()).append("],")
			.append("院校名称[").append(getSchoolName()).append("],")
			.append("学制[").append(getLength()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("考生号[").append(getTestId()).append("],")
			.append("注册时间[").append(getCreateDate()).append("],")
			.append("民族[").append(getNational()).append("],")
			.append("系[").append(getLevel()).append("],")
			.append("专业名称[").append(getMajorName()).append("],")
			.append("学历[").append(getEducation()).append("],")
			.append("出生日期[").append(getBirthday()).append("],")
			.append("入学日期[").append(getStartDate()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SpMember == false) return false;
		if(this == obj) return true;
		SpMember other = (SpMember)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
