package com.centfor.front.entity;

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
 * @version  2015-01-28 17:22:09
 * @see com.centfor.front.entity.SpCart
 */
@Table(name="sp_cart")
public class SpCart  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "SpCart";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SITEID = "商铺Id";
	public static final String ALIAS_PRODUCTID = "产品Id";
	public static final String ALIAS_NUM = "数量";
	public static final String ALIAS_PROPERTIES = "属性";
	public static final String ALIAS_MEMBERID = "会员Id";
	public static final String ALIAS_CREATEDATE = "createDate";
	public static final String ALIAS_BAK = "bak";
	public static final String ALIAS_TYPE = "0是商品 1是套餐";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 商铺Id
	 */
	private java.lang.String siteId;
	/**
	 * 产品Id
	 */
	private java.lang.String productId;
	/**
	 * 数量
	 */
	private java.lang.Integer num;
	/**
	 * 属性
	 */
	private java.lang.String properties;
	/**
	 * 会员Id
	 */
	private java.lang.String memberId;
	/**
	 * createDate
	 */
	private java.util.Date createDate;
	/**
	 * bak
	 */
	private java.lang.String bak;
	/**
	 * 0是商品 1是套餐
	 */
	private java.lang.Integer type;
	
	private Integer price=0;
	
	private Integer oldPrice=0;
	
	private Integer jieshengPrice=0;
	/**
	 * 产品名字
	 */
	private String productName;
	
	//当是免邮的时候，运费为 null。
	private Integer yunfei;
	
	/**
	 * 图片url（一张图片）
	 */
	private String pictureUrl;
	
	/**
	 * 价格属性的展示
	 */
	private String info;
	//columns END 数据库字段结束
	
	//concstructor

	public SpCart(){
	}

	public SpCart(
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
     @WhereSQL(sql="id=:SpCart_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:SpCart_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setProductId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.productId = value;
	}
	
     @WhereSQL(sql="productId=:SpCart_productId")
	public java.lang.String getProductId() {
		return this.productId;
	}
	public void setNum(java.lang.Integer value) {
		this.num = value;
	}
	
     @WhereSQL(sql="num=:SpCart_num")
	public java.lang.Integer getNum() {
		return this.num;
	}
	public void setProperties(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.properties = value;
	}
	
     @WhereSQL(sql="properties=:SpCart_properties")
	public java.lang.String getProperties() {
		return this.properties;
	}
	public void setMemberId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.memberId = value;
	}
	
     @WhereSQL(sql="memberId=:SpCart_memberId")
	public java.lang.String getMemberId() {
		return this.memberId;
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
	
	
	
     public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@WhereSQL(sql="createDate=:SpCart_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setBak(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.bak = value;
	}
	
     @WhereSQL(sql="bak=:SpCart_bak")
	public java.lang.String getBak() {
		return this.bak;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
     @WhereSQL(sql="type=:SpCart_type")
	public java.lang.Integer getType() {
		return this.type;
	}
      
	@Transient
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	@Transient
	public Integer getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Integer oldPrice) {
		this.oldPrice = oldPrice;
	}
	
	@Transient
	public Integer getYunfei() {
		return yunfei;
	}

	public void setYunfei(Integer yunfei) {
		this.yunfei = yunfei;
	}
	

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	@Transient
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	

	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("商铺Id[").append(getSiteId()).append("],")
			.append("产品Id[").append(getProductId()).append("],")
			.append("数量[").append(getNum()).append("],")
			.append("属性[").append(getProperties()).append("],")
			.append("会员Id[").append(getMemberId()).append("],")
			.append("createDate[").append(getCreateDate()).append("],")
			.append("bak[").append(getBak()).append("],")
			.append("0是商品 1是套餐[").append(getType()).append("],")
			.toString();
	}
	@Transient
	public Integer getJieshengPrice() {
		return jieshengPrice;
	}

	public void setJieshengPrice(Integer jieshengPrice) {
		this.jieshengPrice = jieshengPrice;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SpCart == false) return false;
		if(this == obj) return true;
		SpCart other = (SpCart)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
