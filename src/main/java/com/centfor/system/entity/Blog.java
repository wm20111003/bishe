package com.centfor.system.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import javax.persistence.Table;

import com.centfor.frame.annotation.WhereSQL;
import com.centfor.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-09-07 09:37:01
 * @see com.centfor.system.entity.Blog
 */
@Table(name="blog")
public class Blog  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "博客";
	public static final String ALIAS_ID = "序号";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_CONTENT = "内容";
    */
	//date formats
	
	//columns START
	/**
	 * 序号
	 */
	private java.lang.Integer id;
	/**
	 * 标题
	 */
	private java.lang.String title;
	/**
	 * 内容
	 */
	private java.lang.String content;
	//columns END 数据库字段结束
	
	//concstructor

	public Blog(){
	}

	public Blog(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Blog_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setTitle(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.title = value;
	}
	
     @WhereSQL(sql="title=:Blog_title")
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setContent(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.content = value;
	}
	
     @WhereSQL(sql="content=:Blog_content")
	public java.lang.String getContent() {
		return this.content;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("序号[").append(getId()).append("],")
			.append("标题[").append(getTitle()).append("],")
			.append("内容[").append(getContent()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Blog == false) return false;
		if(this == obj) return true;
		Blog other = (Blog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
