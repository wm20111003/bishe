package com.centfor.cms.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
 * @version 2014-10-21 09:36:15
 * @see com.centfor.cms.entity.CmsContent
 */
@Table(name = "cms_content")
public class CmsContent extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// alias
	/*
	 * public static final String TABLE_ALIAS = "CmsContent"; public static
	 * final String ALIAS_ID = "id"; public static final String ALIAS_TITLE =
	 * "title"; public static final String ALIAS_KEYWORDS = "关键字"; public static
	 * final String ALIAS_DESCRIPTION = "描述"; public static final String
	 * ALIAS_NAME = "名称"; public static final String ALIAS_MINTITLE = "小标题";
	 * public static final String ALIAS_LOOKCOUNT = "打开次数"; public static final
	 * String ALIAS_CREATEPERSON = "创建人"; public static final String
	 * ALIAS_CREATEDATE = "创建时间"; public static final String ALIAS_CONTENT =
	 * "内容"; public static final String ALIAS_SORT = "排序"; public static final
	 * String ALIAS_STATE = "0失效,1有效";
	 */
	// date formats
	// public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;

	// columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * title
	 */
	private java.lang.String title;
	/**
	 * 关键字
	 */
	private java.lang.String keywords;
	/**
	 * 描述
	 */
	private java.lang.String description;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 小标题
	 */
	private java.lang.String mintitle;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * 创建人
	 */
	private java.lang.String createPerson;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 内容
	 */
	private java.lang.String content;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 0失效,1有效
	 */
	private java.lang.Integer state;

	/**
	 * 内容类型 普通(default),焦点(focus),头条(bignews),滚动(marquee),推荐(recommend)
	 */
	private String contentType;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 要求登录
	 */
	private String loginReq;

	/**
	 * 价格
	 */
	private Integer price;
	/**
	 * 原价格
	 */
	private Integer oldPrice;
	/**
	 * 类型Id
	 */
	private String typeId;
	/**
	 * 品牌
	 */
	private String brandId;
	/**
	 * 站点ID
	 */
	private String siteId;
	/**
	 * 购买积分(送给用户)
	 */
	private Integer buyScore;
	/**
	 * 销售积分(用户用积分购买)
	 */
	private Integer saleSore;
	/**
	 * 库存
	 */
	private Integer stock;
	/**
	 * 物流模板
	 */
	private String expresstemplateId;
	/**
	 * 标示
	 * 
	 */
	private Integer ctype;
	/**
	 * 销量
	 */
	private Integer salesVolume;
	/**
	 * 评价人数
	 */
	private Integer evaluationNumer;
	/**
	 * 好评率
	 */
	private Integer probabilityGood;
	/**
	 * 是否是新品
	 */
	private java.lang.Integer newProduct;
	/**
	 * 是否推荐
	 */
	private java.lang.Integer recommend;
	// columns END 数据库字段结束
	private String channelId;
	private String ostype;
	private String picture;
	private String linkUrl;
	private String opentype;
	private String bak1;
	private String bak2;
	private String bak3;
	private String bak4;
	private String bak5;

	// 对应的栏目
	private List<CmsChannel> chanels = new ArrayList<CmsChannel>();
	// 价格属性
	private List<CmsContentPriceProperty> ccpps;
	// 决定价格参数
	private List<CmsContentPricePropertyValue> ccppvs;
	// 主图片
	private CmsPicture mainPricUrl;
	// 附图
	private List<CmsPicture> otherPricUrl;
	// 商品属性
	private List<CmsProperty> cps = new ArrayList<CmsProperty>();
	// 商品属性
	private List<CmsPropertyvalue> cpvs = new ArrayList<CmsPropertyvalue>();
	// 站点
	private CmsSite site;
	// 商品栏目中间表
	List<CmsChannelContent> listmid = new ArrayList<CmsChannelContent>();
	// 站点URL
	List<CmsLink> listlink = new ArrayList<CmsLink>();
	// 商品所有图片
	List<CmsPicture> ps = new ArrayList<CmsPicture>();
	// 店主
	private String userId;
	// 关联商品
	private List<CmsContent> otherContentList = new ArrayList<CmsContent>();

	private BigDecimal priceBd;
	/**
	 * 瞬时，为了在商品弹出层配置参数得到元
	 */
	private BigDecimal xsPriceBd;
	private BigDecimal oldPriceBd;

	private String channelName;

	private Integer num;
	/**
	 * 成本价格
	 */
	private Integer costPrice;
	/**
	 * 批发价格（经销商）
	 */
	private Integer xsPrice;
	/**
	 * 是否包邮
	 */
	private Integer isShipping;
	// 运费
	private Integer yunfei;
	// 月销量
	private Integer monthSalesVolume;
	// 如果存在价格属性取价格属性区间
	private BigDecimal startPrice;

	private BigDecimal endPrice;

	// concstructor

	public CmsContent() {
	}

	public CmsContent(java.lang.String id) {
		this.id = id;
	}

	// get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	@WhereSQL(sql="siteId=:CmsContent_siteId")
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Transient
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Transient
	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	@Transient
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Transient
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Transient
	public String getOpentype() {
		return opentype;
	}

	public void setOpentype(String opentype) {
		this.opentype = opentype;
	}

	@WhereSQL(sql = "loginReq=:CmsContent_loginReq")
	public String getLoginReq() {
		return loginReq;
	}

	public void setLoginReq(String loginReq) {
		this.loginReq = loginReq;
	}

	@Id
	@WhereSQL(sql = "id=:CmsContent_id")
	public java.lang.String getId() {
		return this.id;
	}

	public void setTitle(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.title = value;
	}

	@WhereSQL(sql = "title=:CmsContent_title")
	public java.lang.String getTitle() {
		return this.title;
	}

	public void setKeywords(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.keywords = value;
	}

	@WhereSQL(sql = "keywords=:CmsContent_keywords")
	public java.lang.String getKeywords() {
		return this.keywords;
	}

	public void setDescription(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.description = value;
	}

	@WhereSQL(sql = "description=:CmsContent_description")
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:CmsContent_name")
	public java.lang.String getName() {
		return this.name;
	}

	public void setMintitle(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.mintitle = value;
	}

	@WhereSQL(sql = "mintitle=:CmsContent_mintitle")
	public java.lang.String getMintitle() {
		return this.mintitle;
	}

	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}

	@WhereSQL(sql = "lookcount=:CmsContent_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}

	public void setCreatePerson(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.createPerson = value;
	}

	@WhereSQL(sql = "createPerson=:CmsContent_createPerson")
	public java.lang.String getCreatePerson() {
		return this.createPerson;
	}

	/*
	 * public String getcreateDateString() { return
	 * DateUtils.convertDate2String(FORMAT_CREATEDATE, getcreateDate()); }
	 * public void setcreateDateString(String value) throws ParseException{
	 * setcreateDate(DateUtils.convertString2Date(FORMAT_CREATEDATE,value)); }
	 */

	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}

	@WhereSQL(sql = "createDate=:CmsContent_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setContent(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.content = value;
	}

	@WhereSQL(sql = "content=:CmsContent_content")
	public java.lang.String getContent() {
		return this.content;
	}

	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}

	@WhereSQL(sql = "sort=:CmsContent_sort")
	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setState(java.lang.Integer value) {
		this.state = value;
	}

	@WhereSQL(sql = "state=:CmsContent_state")
	public java.lang.Integer getState() {
		return this.state;
	}

	@WhereSQL(sql = "contentType=:CmsContent_contentType")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@WhereSQL(sql = "source=:CmsContent_source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@WhereSQL(sql = "author=:CmsContent_author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String toString() {
		return new StringBuffer().append("id[").append(getId()).append("],")
				.append("title[").append(getTitle()).append("],")
				.append("关键字[").append(getKeywords()).append("],")
				.append("描述[").append(getDescription()).append("],")
				.append("名称[").append(getName()).append("],").append("小标题[")
				.append(getMintitle()).append("],").append("打开次数[")
				.append(getLookcount()).append("],").append("创建人[")
				.append(getCreatePerson()).append("],").append("创建时间[")
				.append(getCreateDate()).append("],").append("内容[")
				.append(getContent()).append("],").append("排序[")
				.append(getSort()).append("],").append("0失效,1有效[")
				.append(getState()).append("],").toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof CmsContent == false)
			return false;
		if (this == obj)
			return true;
		CmsContent other = (CmsContent) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@WhereSQL(sql="brandId=:CmsContent_brandId")
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public Integer getBuyScore() {
		return buyScore;
	}

	public void setBuyScore(Integer buyScore) {
		this.buyScore = buyScore;
	}

	public Integer getSaleSore() {
		return saleSore;
	}

	public void setSaleSore(Integer saleSore) {
		this.saleSore = saleSore;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getBak3() {
		return bak3;
	}

	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}

	public String getBak4() {
		return bak4;
	}

	public void setBak4(String bak4) {
		this.bak4 = bak4;
	}

	public String getBak5() {
		return bak5;
	}

	public void setBak5(String bak5) {
		this.bak5 = bak5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Integer oldPrice) {
		this.oldPrice = oldPrice;
	}

	@Transient
	public List<CmsChannel> getChanels() {
		return chanels;
	}

	public void setChanels(List<CmsChannel> chanels) {
		this.chanels = chanels;
	}

	@Transient
	public List<CmsContentPriceProperty> getCcpps() {
		return ccpps;
	}

	public void setCcpps(List<CmsContentPriceProperty> ccpps) {
		this.ccpps = ccpps;
	}

	@Transient
	public List<CmsContentPricePropertyValue> getCcppvs() {
		return ccppvs;
	}

	public void setCcppvs(List<CmsContentPricePropertyValue> ccppvs) {
		this.ccppvs = ccppvs;
	}

	@Transient
	public CmsPicture getMainPricUrl() {
		return mainPricUrl;
	}

	public void setMainPricUrl(CmsPicture mainPricUrl) {
		this.mainPricUrl = mainPricUrl;
	}

	@Transient
	public List<CmsPicture> getOtherPricUrl() {
		return otherPricUrl;
	}

	public void setOtherPricUrl(List<CmsPicture> otherPricUrl) {
		this.otherPricUrl = otherPricUrl;
	}

	@Transient
	public List<CmsProperty> getCps() {
		return cps;
	}

	public void setCps(List<CmsProperty> cps) {
		this.cps = cps;
	}

	@Transient
	public CmsSite getSite() {
		return site;
	}

	public void setSite(CmsSite site) {
		this.site = site;
	}

	@Transient
	public List<CmsChannelContent> getListmid() {
		return listmid;
	}

	public void setListmid(List<CmsChannelContent> listmid) {
		this.listmid = listmid;
	}

	@Transient
	public List<CmsLink> getListlink() {
		return listlink;
	}

	public void setListlink(List<CmsLink> listlink) {
		this.listlink = listlink;
	}

	@Transient
	public List<CmsPicture> getPs() {
		return ps;
	}

	public void setPs(List<CmsPicture> ps) {
		this.ps = ps;
	}

	@Transient
	public List<CmsPropertyvalue> getCpvs() {
		return cpvs;
	}

	public void setCpvs(List<CmsPropertyvalue> cpvs) {
		this.cpvs = cpvs;
	}

	public String getExpresstemplateId() {
		return expresstemplateId;
	}

	public void setExpresstemplateId(String expresstemplateId) {
		this.expresstemplateId = expresstemplateId;
	}

	@Transient
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getCtype() {
		return ctype;
	}

	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}

	@Transient
	public List<CmsContent> getOtherContentList() {
		return otherContentList;
	}

	public void setOtherContentList(List<CmsContent> otherContentList) {
		this.otherContentList = otherContentList;
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

	@Transient
	public BigDecimal getOldPriceBd() {
		if (oldPrice != null) {
			return new BigDecimal(oldPrice).divide(new BigDecimal(100));
		}

		return oldPriceBd;
	}

	public void setOldPriceBd(BigDecimal oldPriceBd) {

		this.oldPriceBd = oldPriceBd;
	}

	@Transient
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	/*
	 * public void setKeywords(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.keywords = value;
	}

	@WhereSQL(sql = "keywords=:CmsContent_keywords")
	public java.lang.String getKeywords() {
		return this.keywords;
	}
	 */
	@WhereSQL(sql = "costPrice=:CmsContent_costPrice")
	public Integer getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Integer costPrice) {
		this.costPrice = costPrice;
	}
	@WhereSQL(sql = "xsPrice=:CmsContent_xsPrice")
	public Integer getXsPrice() {
		return xsPrice;
	}

	public void setXsPrice(Integer xsPrice) {
		this.xsPrice = xsPrice;
	}
	
	@WhereSQL(sql = "isShipping=:CmsContent_isShipping")
	public Integer getIsShipping() {
		return isShipping;
	}

	public void setIsShipping(Integer isShipping) {
		this.isShipping = isShipping;
	}
	
	@WhereSQL(sql = "yunfei=:CmsContent_yunfei")
	public Integer getYunfei() {
		return yunfei;
	}

	public void setYunfei(Integer yunfei) {
		this.yunfei = yunfei;
	}

	@Transient
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getSalesVolume() {
		if(salesVolume==null){
			return 0;
		}
		return salesVolume;
	}
	

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	@Transient
	public Integer getMonthSalesVolume() {
		return monthSalesVolume;
	}

	public void setMonthSalesVolume(Integer monthSalesVolume) {
		this.monthSalesVolume = monthSalesVolume;
	}

	@Transient
	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	@Transient
	public BigDecimal getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}

	public Integer getEvaluationNumer() {
		return evaluationNumer;
	}

	public void setEvaluationNumer(Integer evaluationNumer) {
		this.evaluationNumer = evaluationNumer;
	}

	public Integer getProbabilityGood() {
		if(probabilityGood==null){
			return 0;
		}
		return probabilityGood;
	}

	public void setProbabilityGood(Integer probabilityGood) {
		this.probabilityGood = probabilityGood;
	}

	@WhereSQL(sql = "newProduct=:CmsContent_newProduct")
	public java.lang.Integer getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(java.lang.Integer newProduct) {
		this.newProduct = newProduct;
	}
	@WhereSQL(sql = "recommend=:CmsContent_recommend")
	public java.lang.Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(java.lang.Integer recommend) {
		this.recommend = recommend;
	}
	
}
