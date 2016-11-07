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
 * @author springrain<Auto generate>
 * @version  2014-10-21 09:36:15
 * @see com.centfor.cms.entity.CmsComment
 */
@Table(name="cms_comment")
public class CmsComment  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "CMS评论表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "评论用户ID";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_PCOMMENTID = "父级评论Id";
	public static final String ALIAS_SITEID = "站点ID";
	public static final String ALIAS_CREATEDATE = "评论时间";
	public static final String ALIAS_IP = "IP地址";
	public static final String ALIAS_CONTENT = "评论内容";
	public static final String ALIAS_UPS = "支持数";
	public static final String ALIAS_DOWNS = "反对数";
	public static final String ALIAS_CHECKED = "是否审核";
	public static final String ALIAS_SCORE = "评分";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 评论用户ID
	 */
	private java.lang.String userId;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 父级评论Id
	 */
	private java.lang.String pcommentId;
	/**
	 * 站点ID
	 */
	private java.lang.Integer siteId;
	/**
	 * 评论时间
	 */
	private java.util.Date createDate;
	/**
	 * IP地址
	 */
	private java.lang.String ip;
	/**
	 * 评论内容
	 */
	private java.lang.String content;
	/**
	 * 支持数
	 */
	private Integer ups;
	/**
	 * 反对数
	 */
	private Integer downs;
	/**
	 * 是否审核
	 */
	private java.lang.Boolean checked;
	/**
	 * 评分
	 */
	private java.lang.Integer score;
	//columns END 数据库字段结束
	
	//concstructor

	public CmsComment(){
	}

	public CmsComment(
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
     @WhereSQL(sql="id=:CmsComment_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:CmsComment_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsComment_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setPcommentId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.pcommentId = value;
	}
	
     @WhereSQL(sql="pcommentId=:CmsComment_pcommentId")
	public java.lang.String getPcommentId() {
		return this.pcommentId;
	}
	public void setSiteId(java.lang.Integer value) {
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsComment_siteId")
	public java.lang.Integer getSiteId() {
		return this.siteId;
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
	
     @WhereSQL(sql="createDate=:CmsComment_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setIp(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ip = value;
	}
	
     @WhereSQL(sql="ip=:CmsComment_ip")
	public java.lang.String getIp() {
		return this.ip;
	}
	public void setContent(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.content = value;
	}
	
     @WhereSQL(sql="content=:CmsComment_content")
	public java.lang.String getContent() {
		return this.content;
	}
	public void setUps(Integer value) {
		this.ups = value;
	}
	
     @WhereSQL(sql="ups=:CmsComment_ups")
	public Integer getUps() {
		return this.ups;
	}
	public void setDowns(Integer value) {
		this.downs = value;
	}
	
     @WhereSQL(sql="downs=:CmsComment_downs")
	public Integer getDowns() {
		return this.downs;
	}
	public void setChecked(java.lang.Boolean value) {
		this.checked = value;
	}
	
     @WhereSQL(sql="checked=:CmsComment_checked")
	public java.lang.Boolean getChecked() {
		return this.checked;
	}
	public void setScore(java.lang.Integer value) {
		this.score = value;
	}
	
     @WhereSQL(sql="score=:CmsComment_score")
	public java.lang.Integer getScore() {
		return this.score;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("评论用户ID[").append(getUserId()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("父级评论Id[").append(getPcommentId()).append("],")
			.append("站点ID[").append(getSiteId()).append("],")
			.append("评论时间[").append(getCreateDate()).append("],")
			.append("IP地址[").append(getIp()).append("],")
			.append("评论内容[").append(getContent()).append("],")
			.append("支持数[").append(getUps()).append("],")
			.append("反对数[").append(getDowns()).append("],")
			.append("是否审核[").append(getChecked()).append("],")
			.append("评分[").append(getScore()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CmsComment == false) return false;
		if(this == obj) return true;
		CmsComment other = (CmsComment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
