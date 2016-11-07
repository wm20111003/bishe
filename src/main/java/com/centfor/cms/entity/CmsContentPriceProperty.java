package com.centfor.cms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.centfor.frame.annotation.WhereSQL;
import com.centfor.frame.entity.BaseEntity;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2014-10-21 09:36:14
 * @see com.centfor.cms.entity.CmsContentPriceProperty
 */
@Table(name = "cms_content_price_property")
public class CmsContentPriceProperty extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 上级Id
	 */
	private java.lang.String contentId;
	/**
	 * 名称
	 */
	private java.lang.String sid;
	/**
	 * 标题
	 */
	private java.lang.String sname;
	/**
	 * 关键字
	 */
	private java.lang.String value;
	/**
	 * 描述
	 */
	private java.lang.Integer sort;

	private List<String> valueList = new ArrayList<String>();

	// concstructor

	public CmsContentPriceProperty() {
	}

	// get and set
	public void setId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.id = value;
	}

	public java.lang.String getContentId() {
		return contentId;
	}

	public void setContentId(java.lang.String contentId) {
		this.contentId = contentId;
	}

	public java.lang.String getSid() {
		return sid;
	}

	public void setSid(java.lang.String sid) {
		this.sid = sid;
	}

	public java.lang.String getSname() {
		return sname;
	}

	public void setSname(java.lang.String sname) {
		this.sname = sname;
	}

	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

	public java.lang.Integer getSort() {
		return sort;
	}

	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	public java.lang.String getId() {
		return id;
	}

	public String toString() {
		return new StringBuffer().append("id[").append(getId()).append("],")
				.append("sId[").append(getSid()).append("],").append("sName[")
				.append(getSname()).append("],").append("value[")
				.append(getValue()).append("],").append("contentId[")
				.append(getContentId()).append("],").toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getValueList() {
		if (StringUtils.isNotBlank(value)) {
			String[] values = StringUtils.split(value, ",");
			return org.springframework.util.CollectionUtils.arrayToList(values);
		}
		return new ArrayList<String>();
	}

	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}

}
