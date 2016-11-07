package com.centfor.cms.entity;

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
 * @version 2014-10-21 09:36:17
 * @see com.centfor.cms.entity.CmsSite
 */
@Table(name = "cms_site")
public class CmsSite extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// alias
	/*
	 * `id` varchar(50) NOT NULL, `userId` varchar(50) NOT NULL COMMENT '用户Id',
	 * `name` varchar(255) DEFAULT NULL COMMENT '名称', `title` varchar(255)
	 * DEFAULT NULL, `logo` varchar(2000) NOT NULL COMMENT '网站logo', `footer`
	 * varchar(2000) NOT NULL COMMENT '页脚', `qq` varchar(20) NOT NULL COMMENT
	 * 'QQ', `phone` varchar(20) NOT NULL COMMENT '电话', `contacts` varchar(20)
	 * NOT NULL COMMENT '联系人', `keywords` varchar(1000) DEFAULT NULL,
	 * `description` varchar(1000) DEFAULT NULL, `themeGroupId` varchar(50)
	 * DEFAULT NULL COMMENT '主题组Id', `lookcount` int(11) DEFAULT NULL COMMENT
	 * '打开次数', `siteType` varchar(50) DEFAULT '网站' COMMENT '网站类型(网站,商城,论坛)',
	 * `state` int(11) DEFAULT NULL COMMENT '状态 0关闭,1开启',
	 */
	// date formats

	// columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 用户Id
	 */
	private java.lang.String userId;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * title
	 */
	private java.lang.String title;
	/**
	 * 网站logo
	 */
	private java.lang.String logo;
	/**
	 * 页脚
	 */
	private java.lang.String footer;
	/**
	 * QQ
	 */
	private java.lang.String qq;
	/**
	 * 电话
	 */
	private java.lang.String phone;
	/**
	 * 联系人
	 */
	private java.lang.String contacts;
	/**
	 * keywords
	 */
	private java.lang.String keywords;
	/**
	 * description
	 */
	private java.lang.String description;
	/**
	 * 主题组Id
	 */
	private java.lang.String themeGroupId;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * 网站类型(网站,商城,论坛)
	 */
	private java.lang.String siteType;
	/**
	 * 状态 0关闭,1开启
	 */
	private java.lang.Integer state;
	/**
	 * 金币抵现百分比
	 */
	private java.lang.Integer jinbiOffsetPercent;
	
	// columns END 数据库字段结束

	// 站点的所有栏目
	private List<CmsChannel> siteChannels;
	
	//用户名
	private String userName;

	
	//主页主题
	private List<String> siteThemeIds;
	//栏目主题
	private List<String> channelThemeIds;
	/**
	 * 货号前缀
	 */
	private String goodsPrefix;
	/**
	 * 是否允许评论
	 */
	private int isCanComment;
	/**
	 * 是否审核评论
	 */
	private int isCheckComment;
	/**
	 * 格式化金额
	 */
	private String moneyFormat="￥";
	/**
	 *商品名称显示长度(首页)
	 */
	private Integer goodsNameLength;
	/**
	 * 是否验证库存
	 */
	private Integer isValidateStock;
	
	/**
	 * X坐标
	 */
	private String xCoordinate;
	/**
	 * Y坐标
	 */
	private String yCoordinate;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private  String city;
	/**
	 * 区
	 */
	private String area;
	
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 确认订单时间
	 */
	private Integer overOrder=6;
	
	/**
	 * 关闭订单时间
	 */
	private Integer closeOrder=2;
	
	/**
	 * 分享好友每日新增最大积分
	 */
	private Integer maxAddScore;
	
	/**
	 * 好友每次点击分享链接，经销商获得的积分
	 */
	private Integer clickOnceScore;
	
	@WhereSQL(sql = "maxAddScore=:CmsSite_maxAddScore")
	public Integer getMaxAddScore() {
		return maxAddScore;
	}

	public void setMaxAddScore(Integer maxAddScore) {
		this.maxAddScore = maxAddScore;
	}

	/**
	 * 商家编码
	 */
	private String sitecode;
	
	/**
	 * 物流模板id
	 */
	private String expresstemplateId;
	/**
	 * 省(拼音)
	 */
	private String provincePinyin;
	// 附图
		private List<CmsPicture> pics;
	// concstructor
	private Integer exchangeRate;

	public CmsSite() {
	}

	public CmsSite(java.lang.String id) {
		this.id = id;
	}
	

	@Transient
	public List<String> getSiteThemeIds() {
		return siteThemeIds;
	}

	public void setSiteThemeIds(List<String> siteThemeIds) {
		this.siteThemeIds = siteThemeIds;
	}
    @Transient
	public List<String> getChannelThemeIds() {
		return channelThemeIds;
	}

	public void setChannelThemeIds(List<String> channelThemeIds) {
		this.channelThemeIds = channelThemeIds;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// get and set
	public void setId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.id = value;
	}

	@Id
	@WhereSQL(sql = "id=:CmsSite_id")
	public java.lang.String getId() {
		return this.id;
	}

	public void setUserId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.userId = value;
	}

	@WhereSQL(sql = "userId=:CmsSite_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}

	public void setName(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.name = value;
	}

	@WhereSQL(sql = "name=:CmsSite_name")
	public java.lang.String getName() {
		return this.name;
	}

	public void setTitle(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.title = value;
	}

	@WhereSQL(sql = "title=:CmsSite_title")
	public java.lang.String getTitle() {
		return this.title;
	}

	public void setLogo(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.logo = value;
	}

	@WhereSQL(sql = "logo=:CmsSite_logo")
	public java.lang.String getLogo() {
		return this.logo;
	}

	public void setFooter(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.footer = value;
	}

	@WhereSQL(sql = "footer=:CmsSite_footer")
	public java.lang.String getFooter() {
		return this.footer;
	}

	public void setQq(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.qq = value;
	}

	@WhereSQL(sql = "qq=:CmsSite_qq")
	public java.lang.String getQq() {
		return this.qq;
	}

	public void setPhone(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.phone = value;
	}

	@WhereSQL(sql = "phone=:CmsSite_phone")
	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setContacts(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.contacts = value;
	}

	@WhereSQL(sql = "contacts=:CmsSite_contacts")
	public java.lang.String getContacts() {
		return this.contacts;
	}

	public void setKeywords(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.keywords = value;
	}

	@WhereSQL(sql = "keywords=:CmsSite_keywords")
	public java.lang.String getKeywords() {
		return this.keywords;
	}

	public void setDescription(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.description = value;
	}

	@WhereSQL(sql = "description=:CmsSite_description")
	public java.lang.String getDescription() {
		return this.description;
	}

	public void setThemeGroupId(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.themeGroupId = value;
	}

	@WhereSQL(sql = "themeGroupId=:CmsSite_themeGroupId")
	public java.lang.String getThemeGroupId() {
		return this.themeGroupId;
	}

	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}

	@WhereSQL(sql = "lookcount=:CmsSite_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}

	public void setSiteType(java.lang.String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.trim();
		}
		this.siteType = value;
	}

	@WhereSQL(sql = "siteType=:CmsSite_siteType")
	public java.lang.String getSiteType() {
		return this.siteType;
	}

	public void setState(java.lang.Integer value) {
		this.state = value;
	}

	@WhereSQL(sql = "state=:CmsSite_state")
	public java.lang.Integer getState() {
		return this.state;
	}
	

	public Integer getExchangeRate() {
		return exchangeRate;
	}
	@WhereSQL(sql = "jinbiOffsetPercent=:CmsSite_jinbiOffsetPercent")
	public java.lang.Integer getJinbiOffsetPercent() {
		return jinbiOffsetPercent;
	}

	public void setJinbiOffsetPercent(java.lang.Integer jinbiOffsetPercent) {
		this.jinbiOffsetPercent = jinbiOffsetPercent;
	}

	public void setExchangeRate(Integer exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String toString() {
		return new StringBuffer().append("id[").append(getId()).append("],")
				.append("用户Id[").append(getUserId()).append("],").append("名称[")
				.append(getName()).append("],").append("title[")
				.append(getTitle()).append("],").append("网站logo[")
				.append(getLogo()).append("],").append("页脚[")
				.append(getFooter()).append("],").append("QQ[").append(getQq())
				.append("],").append("电话[").append(getPhone()).append("],")
				.append("联系人[").append(getContacts()).append("],")
				.append("keywords[").append(getKeywords()).append("],")
				.append("description[").append(getDescription()).append("],")
				.append("主题组Id[").append(getThemeGroupId()).append("],")
				.append("打开次数[").append(getLookcount()).append("],")
				.append("网站类型(网站,商城,论坛)[").append(getSiteType()).append("],")
				.append("状态 0关闭,1开启[").append(getState()).append("],")
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof CmsSite == false)
			return false;
		if (this == obj)
			return true;
		CmsSite other = (CmsSite) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
	@Transient
	public List<CmsChannel> getSiteChannels() {
		return siteChannels;
	}

	public void setSiteChannels(List<CmsChannel> siteChannels) {
		this.siteChannels = siteChannels;
	}

	public String getGoodsPrefix() {
		return goodsPrefix;
	}

	public void setGoodsPrefix(String goodsPrefix) {
		this.goodsPrefix = goodsPrefix;
	}

	public int getIsCanComment() {
		return isCanComment;
	}

	public void setIsCanComment(int isCanComment) {
		this.isCanComment = isCanComment;
	}

	public int getIsCheckComment() {
		return isCheckComment;
	}

	public void setIsCheckComment(int isCheckComment) {
		this.isCheckComment = isCheckComment;
	}

	public String getMoneyFormat() {
		return moneyFormat;
	}

	public void setMoneyFormat(String moneyFormat) {
		this.moneyFormat = moneyFormat;
	}

	public Integer getGoodsNameLength() {
		return goodsNameLength;
	}

	public void setGoodsNameLength(Integer goodsNameLength) {
		this.goodsNameLength = goodsNameLength;
	}

	public Integer getIsValidateStock() {
		return isValidateStock;
	}

	public void setIsValidateStock(Integer isValidateStock) {
		this.isValidateStock = isValidateStock;
	}

	public String getXCoordinate() {
		return xCoordinate;
	}

	public void setXCoordinate(String xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public String getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(String yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	@WhereSQL(sql = "province=:CmsSite_province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getOverOrder() {
		return overOrder;
	}

	public void setOverOrder(Integer overOrder) {
		this.overOrder = overOrder;
	}

	public Integer getCloseOrder() {
		return closeOrder;
	}

	public void setCloseOrder(Integer closeOrder) {
		this.closeOrder = closeOrder;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public String getExpresstemplateId() {
		return expresstemplateId;
	}

	public void setExpresstemplateId(String expresstemplateId) {
		this.expresstemplateId = expresstemplateId;
	}

	public String getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(String xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public String getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(String yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	@Transient
	public List<CmsPicture> getPics() {
		return pics;
	}

	public void setPics(List<CmsPicture> pics) {
		this.pics = pics;
	}
	@WhereSQL(sql = "provincePinyin=:CmsSite_provincePinyin")
	public String getProvincePinyin() {
		return provincePinyin;
	}

	public void setProvincePinyin(String provincePinyin) {
		this.provincePinyin = provincePinyin;
	}
	
	@WhereSQL(sql = "clickOnceScore=:CmsSite_clickOnceScore")
	public Integer getClickOnceScore() {
		return clickOnceScore;
	}

	public void setClickOnceScore(Integer clickOnceScore) {
		this.clickOnceScore = clickOnceScore;
	}
	
	
	
	
}
