package com.centfor.school.entity;

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
 * @author centfor<Auto generate>
 * @version  2016-03-29 11:40:01
 * @see com.centfor.school.entity.WorkMark
 */
@Table(name="work_mark")
public class WorkMark  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "WorkMark";
	public static final String ALIAS_ID = "用户id";
	public static final String ALIAS_USERID = "用户Id";
	public static final String ALIAS_COURSENAMEID = "课程id";
	public static final String ALIAS_MARK = "成绩";
	public static final String ALIAS_GPA = "绩点";
    */
	//date formats
	
	//columns START
	/**
	 * 用户id
	 */
	private java.lang.String id;
	/**
	 * 用户Id
	 */
	private java.lang.String userId;
	/**
	 * 课程id
	 */
	private java.lang.String courseNameId;
	/**
	 * 成绩
	 */
	private java.lang.Integer mark;
	/**
	 * 绩点
	 */
	private java.lang.Integer gpa;
	
	
	/**
	 * 课程名
	 */
	private java.lang.String courseName;
	
	
	/**
	 * 学员姓名
	 */
	private java.lang.String memberName;
	/**
	 * 课程类型0为选修，1为必修
	 * @return
	 */
	private java.lang.String courseType;
	
	@Transient
	public java.lang.String getCourseType() {
		return courseType;
	}

	public void setCourseType(java.lang.String courseType) {
		this.courseType = courseType;
	}

	@Transient
	public java.lang.String getCourseName() {
		return courseName;
	}

	public void setCourseName(java.lang.String courseName) {
		this.courseName = courseName;
	}
	
	@Transient
	public java.lang.String getMemberName() {
		return memberName;
	}

	public void setMemberName(java.lang.String memberName) {
		this.memberName = memberName;
	}

	//columns END 数据库字段结束
	
	//concstructor

	public WorkMark(){
	}

	public WorkMark(
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
     @WhereSQL(sql="id=:WorkMark_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:WorkMark_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setCourseNameId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.courseNameId = value;
	}
	
     @WhereSQL(sql="courseNameId=:WorkMark_courseNameId")
	public java.lang.String getCourseNameId() {
		return this.courseNameId;
	}
	public void setMark(java.lang.Integer value) {
		this.mark = value;
	}
	
     @WhereSQL(sql="mark=:WorkMark_mark")
	public java.lang.Integer getMark() {
		return this.mark;
	}
	public void setGpa(java.lang.Integer value) {
		this.gpa = value;
	}
	
     @WhereSQL(sql="gpa=:WorkMark_gpa")
	public java.lang.Integer getGpa() {
		return this.gpa;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("用户id[").append(getId()).append("],")
			.append("用户Id[").append(getUserId()).append("],")
			.append("课程id[").append(getCourseNameId()).append("],")
			.append("成绩[").append(getMark()).append("],")
			.append("绩点[").append(getGpa()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof WorkMark == false) return false;
		if(this == obj) return true;
		WorkMark other = (WorkMark)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
