package com.centfor.system.entity;

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
 * @version  2014-08-17 12:13:39
 * @see com.bibizao.bms.goods.entity.DicBusiness
 */
@Table(name="bbz_dic_business")
public class DicBusiness  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "字典业务数据表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_DICID = "字典Id";
	public static final String ALIAS_BUSINESSID = "业务数据Id";
	public static final String ALIAS_DICVALUE = "字典值  字典值";
	public static final String ALIAS_TYPEKEY = "字典类型";
	public static final String ALIAS_SORT = "排序";
    */
	//date formats
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 字典Id
	 */
	private java.lang.String dicId;
	
	/**
	 * 业务数据Id
	 */
	private java.lang.String businessId;
	/**
	 * 字典值  字典值
	 */
	private java.lang.String dicValue;
	/**
	 * 字典类型
	 */
	private java.lang.String typeKey;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	//columns END 数据库字段结束
	
	private String pid;
	/**
	 * 属性的名称
	 */
	private String name;
	/**
	 * 属性编码
	 */
	private String code;
	
	//concstructor

	public DicBusiness(){
	}

	public DicBusiness(
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
     @WhereSQL(sql="id=:DicBusiness_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setDicId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.dicId = value;
	}
	
     @WhereSQL(sql="dicId=:DicBusiness_dicId")
	public java.lang.String getDicId() {
		return this.dicId;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:DicBusiness_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setDicValue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.dicValue = value;
	}
	
     @WhereSQL(sql="dicValue=:DicBusiness_dicValue")
	public java.lang.String getDicValue() {
		return this.dicValue;
	}
	public void setTypeKey(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.typeKey = value;
	}
	
     @WhereSQL(sql="typeKey=:DicBusiness_typeKey")
	public java.lang.String getTypeKey() {
		return this.typeKey;
	}
	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
     @WhereSQL(sql="sort=:DicBusiness_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("ID[").append(getId()).append("],")
			.append("字典Id[").append(getDicId()).append("],")
			.append("业务数据Id[").append(getBusinessId()).append("],")
			.append("字典值  字典值[").append(getDicValue()).append("],")
			.append("字典类型[").append(getTypeKey()).append("],")
			.append("排序[").append(getSort()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DicBusiness == false) return false;
		if(this == obj) return true;
		DicBusiness other = (DicBusiness)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Transient
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

	
