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
 * @copyright {@wm20111003@163.com}
 * @author centfor<Auto generate>
 * @version  2016-03-31 19:12:32
 * @see com.centfor.school.entity.WorkCoursename
 */
@Table(name="work_coursename")
public class WorkCoursename  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "WorkCoursename";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_COURSENAME = "课程";
	public static final String ALIAS_COURSETYPE = "修习类别";
	public static final String ALIAS_SCHOOLITEM = "学期";
	public static final String ALIAS_CREDIT = "学分";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 课程
	 */
	private java.lang.String courseName;
	/**
	 * 修习类别
	 */
	private java.lang.String courseType;
	/**
	 * 学期
	 */
	private java.lang.String schoolItem;
	/**
	 * 学分
	 */
	private java.lang.Integer credit;
	//columns END 数据库字段结束
	/**
	 * 成绩
	 */
	private java.lang.Integer mark;
	/**
	 * 专业名称
	 * @return
	 */
	private java.lang.String majorName;
	
	 @WhereSQL(sql="majorName like %:WorkCoursename_majorName%")
	public java.lang.String getMajorName() {
		return majorName;
	}

	public void setMajorName(java.lang.String majorName) {
		this.majorName = majorName;
	}

	@Transient
	public java.lang.Integer getMark() {
		return mark;
	}

	public void setMark(java.lang.Integer mark) {
		this.mark = mark;
	}

	@Transient
	public java.lang.Integer getGpa() {
		return gpa;
	}

	public void setGpa(java.lang.Integer gpa) {
		this.gpa = gpa;
	}

	/**
	 * 绩点
	 */
	private java.lang.Integer gpa;
	//concstructor

	public WorkCoursename(){
	}

	public WorkCoursename(
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
     @WhereSQL(sql="id=:WorkCoursename_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setCourseName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.courseName = value;
	}
	
     @WhereSQL(sql="courseName=:WorkCoursename_courseName")
	public java.lang.String getCourseName() {
		return this.courseName;
	}
	public void setCourseType(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.courseType = value;
	}
	
     @WhereSQL(sql="courseType=:WorkCoursename_courseType")
	public java.lang.String getCourseType() {
		return this.courseType;
	}
	public void setSchoolItem(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.schoolItem = value;
	}
	
     @WhereSQL(sql="schoolItem=:WorkCoursename_schoolItem")
	public java.lang.String getSchoolItem() {
		return this.schoolItem;
	}
	public void setCredit(java.lang.Integer value) {
		this.credit = value;
	}
	
     @WhereSQL(sql="credit=:WorkCoursename_credit")
	public java.lang.Integer getCredit() {
		return this.credit;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("课程[").append(getCourseName()).append("],")
			.append("修习类别[").append(getCourseType()).append("],")
			.append("学期[").append(getSchoolItem()).append("],")
			.append("学分[").append(getCredit()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof WorkCoursename == false) return false;
		if(this == obj) return true;
		WorkCoursename other = (WorkCoursename)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
