package com.centfor.system.entity;

import java.util.List;

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
 * @copyright {@link bbz}
 * @author 9iu.org<Auto generate>
 * @version  2013-08-02 16:32:29
 * @see com.centfor.system.entity.DicData
 */
@Table(name="t_dic_data")
public class DicData  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "公共字典";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_CODE = "编码";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_SORT = "sort";
	public static final String ALIAS_STATE = "是否有效";
	public static final String ALIAS_TYPEKEY = "类型";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 编码
	 */
	private java.lang.String code;
	
	/**
	 * 父ID
	 */
	private String pid;
	
	
	/**
	 * 描述
	 */
	private java.lang.String remark;
	
	/**
	 * 是否有效
	 */
	private java.lang.String state;
	/**
	 * 类型
	 */
	private java.lang.String typekey;
	
	private Integer sort;
	//columns END 数据库字段结束
	
	
	private List<DicData> leaf;
	
	//concstructor

	public DicData(){
	}

	public DicData(
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
     @WhereSQL(sql="id=:DicData_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:DicData_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setCode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.code = value;
	}
	   @WhereSQL(sql="pid=:DicData_pid")
     public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@WhereSQL(sql="code=:DicData_code")
	public java.lang.String getCode() {
		return this.code;
	}
	public void setRemark(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.remark = value;
	}
	
     @WhereSQL(sql="remark=:DicData_remark")
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setState(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.state = value;
	}
	
     @WhereSQL(sql="state=:DicData_state")
	public java.lang.String getState() {
		return this.state;
	}
	public void setTypekey(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.typekey = value;
	}
	
     @WhereSQL(sql="typekey=:DicData_typekey")
	public java.lang.String getTypekey() {
		return this.typekey;
	}
     @WhereSQL(sql="sort=:DicData_sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("编码[").append(getCode()).append("],")
			.append("描述[").append(getRemark()).append("],")
			.append("是否有效[").append(getState()).append("],")
			.append("类型[").append(getTypekey()).append("],")
			.append("排序[").append(getSort()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DicData == false) return false;
		if(this == obj) return true;
		DicData other = (DicData)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Transient
	public List<DicData> getLeaf() {
		return leaf;
	}

	public void setLeaf(List<DicData> leaf) {
		this.leaf = leaf;
	}
}

	
