package com.centfor.cms.entity;

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
 * @version  2014-11-18 15:51:46
 * @see com.centfor.cms.entity.CmsTableindex
 */
@Table(name="cms_tableindex")
public class CmsTableindex  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * tablename
	 */
	private java.lang.String tablename;
	/**
	 * maxindex
	 */
	private java.lang.Integer maxindex;
	/**
	 * code
	 */
	private java.lang.String code;
	
	//columns END 数据库字段结束
	
	//concstructor

	public CmsTableindex(){ 
	}

	
	//get and set
	public void setTablename(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.tablename = value;
	}
	  @Id
     @WhereSQL(sql="tablename=:CmsTableindex_tablename")
	public java.lang.String getTablename() {
		return this.tablename;
	}
	public void setMaxindex(java.lang.Integer value) {
		this.maxindex = value;
	}
	
     @WhereSQL(sql="maxindex=:CmsTableindex_maxindex")
	public java.lang.Integer getMaxindex() {
		return this.maxindex;
	}
	public void setCode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.code = value;
	}
	
     @WhereSQL(sql="code=:CmsTableindex_code")
	public java.lang.String getCode() {
		return this.code;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("tablename[").append(getTablename()).append("],")
			.append("maxindex[").append(getMaxindex()).append("],")
			.append("code[").append(getCode()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsTableindex == false) return false;
		if(this == obj) return true;
		CmsTableindex other = (CmsTableindex)obj;
		return new EqualsBuilder()
			.isEquals();
	}
}

	
