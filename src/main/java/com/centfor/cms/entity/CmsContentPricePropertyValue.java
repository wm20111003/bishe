package com.centfor.cms.entity;

import java.math.BigDecimal;
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
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2014-10-21 09:36:14
 * @see com.centfor.cms.entity.CmsContentPricePropertyValue
 */
@Table(name = "cms_content_price_property_value")
public class CmsContentPricePropertyValue extends BaseEntity {

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
	private java.lang.String name;
	/**
	 * 标题
	 */
	private java.lang.String code;
	/**
	 * 关键字
	 */
	private java.lang.Integer price;
	/**
	 * 批发价格（经销商）
	 */
	private Integer xsPrice;
	/**
	 * 描述
	 */
	private java.lang.Integer stock;
	private java.lang.String shopcode;
	private java.lang.Integer saleNum;
	private java.lang.Integer sort;
	private java.lang.Integer buyScore;
	private java.lang.Integer saleSore;

	private BigDecimal priceBd;
	private BigDecimal xsPriceBd;

	// concstructor

	public CmsContentPricePropertyValue() {
	}
	@Transient
	public BigDecimal getXsPriceBd() {
		if (xsPrice != null) {
			return new BigDecimal(xsPrice).divide(new BigDecimal(100));
		}
		return xsPriceBd;
	}

	public void setXsPriceBd(BigDecimal xsPriceBd) {
		this.xsPriceBd = xsPriceBd;
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

	public java.lang.Integer getSort() {
		return sort;
	}

	public java.lang.Integer getXsPrice() {
		return xsPrice;
	}

	public void setXsPrice(java.lang.Integer xsPrice) {
		this.xsPrice = xsPrice;
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

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.Integer getPrice() {
		return price;
	}

	public void setPrice(java.lang.Integer price) {
		this.price = price;
	}

	public java.lang.Integer getStock() {
		return stock;
	}

	public void setStock(java.lang.Integer stock) {
		this.stock = stock;
	}

	public java.lang.String getShopcode() {
		return shopcode;
	}

	public void setShopcode(java.lang.String shopcode) {
		this.shopcode = shopcode;
	}

	public java.lang.Integer getSaleNum() {
		if(saleNum==null){
			return 0;
		}
		return saleNum;
	}

	public void setSaleNum(java.lang.Integer saleNum) {
		this.saleNum = saleNum;
	}

	public java.lang.Integer getBuyScore() {
		return buyScore;
	}

	public void setBuyScore(java.lang.Integer buyScore) {
		this.buyScore = buyScore;
	}

	public java.lang.Integer getSaleSore() {
		return saleSore;
	}

	public void setSaleSore(java.lang.Integer saleSore) {
		this.saleSore = saleSore;
	}

	@Transient
	public BigDecimal getPriceBd() {
		if (price != null) {
			return new BigDecimal(price).divide(new BigDecimal(100));
		}
		return priceBd;
	}

	public void setPriceBd(BigDecimal priceBd) {
		this.priceBd = priceBd;
	}

}
