package com.centfor.system.entity.autoid;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.centfor.frame.annotation.WhereSQL;
import com.centfor.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2014-10-05 21:44:11
 * @see com.centfor.system.entity.DicdataAutoid
 */
@Table(name="bbz_dicdata_autoid")
public class DicdataAutoid  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "DicdataAutoid";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_STATE = "state";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * state
	 */
	private java.lang.Integer state;
	//columns END 数据库字段结束
	
	//concstructor

	public DicdataAutoid(){
	}

	public DicdataAutoid(
		java.lang.Integer id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:DicdataAutoid_id")
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
     @WhereSQL(sql="state=:DicdataAutoid_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("state[").append(getState()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DicdataAutoid == false) return false;
		if(this == obj) return true;
		DicdataAutoid other = (DicdataAutoid)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
