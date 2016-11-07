package com.centfor.front.entity;


import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.centfor.frame.annotation.WhereSQL;
import com.centfor.frame.entity.BaseEntity;
import com.centfor.frame.util.DateUtils;
/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2015-01-25 15:14:57
 * @see com.centfor.shop.entity.SpComment
 */
@Table(name="sp_comment")
public class SpComment  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "SpComment";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_BUSINESSID = "关联的Id（商品Id或者是分店Id或者是总店Id）";
	public static final String ALIAS_GRADE = "分数（1 差评 3中评、5好评）";
	public static final String ALIAS_REVIEWCONTENT = "评价内容";
	public static final String ALIAS_PICTUREURL = "逗号隔开";
	public static final String ALIAS_COMMENTMEMBER = "评论会员账号";
	public static final String ALIAS_COMMNENTMEMBERID = "评论会员的Id";
	public static final String ALIAS_CREATEDATE = "创建时间";
	public static final String ALIAS_DOUSER = "回复人的账号";
	public static final String ALIAS_PARENTID = "关联的回复Id（整个会话只存一个Id，只在回复的时候用）";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 关联的Id（商品Id或者是分店Id或者是总店Id）
	 */
	private java.lang.String businessId;
	/**
	 * 分数（1 差评 3中评、5好评）
	 */
	private java.lang.Integer grade;
	/**
	 * 评价内容
	 */
	private java.lang.String reviewContent;
	/**
	 * 逗号隔开
	 */
	private java.lang.String pictureUrl;

	/**
	 * 评论会员账号
	 */
	private java.lang.String commentMember;
	/**
	 * 评论会员的Id
	 */
	private java.lang.String commnentMemberId;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 开始查询时间
	 */
	private java.util.Date startDate;
	/**
	 * 结束查询时间
	 */
	private java.util.Date endDate;
	/**
	 * 回复人的账号
	 */
	private java.lang.String doUser;
	/**
	 * 关联的回复Id（整个会话只存一个Id，只在回复的时候用）
	 */
	private java.lang.String parentId;
	/**
	 * 站点id
	 */
	private java.lang.String siteId;
	//columns END 数据库字段结束
	/**
	 * 时间字符串
	 */
	private java.lang.String dateString;
	/**
	 * 显示时调用的图片URL
	 */
	private java.lang.String[] allPicture;
	/**
	 * 评论数
	 */
	private Integer num=0;
	/**
	 * 页面显示图片
	 */
	private java.lang.String[] pictureUrls;
	//concstructor

	public SpComment(){
	}

	public SpComment(
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
     @WhereSQL(sql="id=:SpComment_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:SpComment_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setGrade(java.lang.Integer value) {
		this.grade = value;
	}
	
     @WhereSQL(sql="grade=:SpComment_grade")
	public java.lang.Integer getGrade() {
		return this.grade;
	}
	public void setReviewContent(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.reviewContent = value;
	}
	
     @WhereSQL(sql="reviewContent=:SpComment_reviewContent")
	public java.lang.String getReviewContent() {
		return this.reviewContent;
	}
	public void setPictureUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.pictureUrl = value;
	}
	
     @WhereSQL(sql="pictureUrl=:SpComment_pictureUrl")
	public java.lang.String getPictureUrl() {
		return this.pictureUrl;
	}
	public void setCommentMember(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.commentMember = value;
	}
	
     @WhereSQL(sql="commentMember=:SpComment_commentMember")
	public java.lang.String getCommentMember() {
		return this.commentMember;
	}
	public void setCommnentMemberId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.commnentMemberId = value;
	}
	
     @WhereSQL(sql="commnentMemberId=:SpComment_commnentMemberId")
	public java.lang.String getCommnentMemberId() {
		return this.commnentMemberId;
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
	
     @WhereSQL(sql="createDate=:SpComment_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setDoUser(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.doUser = value;
	}
	
     @WhereSQL(sql="doUser=:SpComment_doUser")
	public java.lang.String getDoUser() {
		return this.doUser;
	}
	public void setParentId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.parentId = value;
	}
	
     @WhereSQL(sql="parentId=:SpComment_parentId")
	public java.lang.String getParentId() {
		return this.parentId;
	}
	
    @Transient
    @WhereSQL(sql="createDate >=:SpComment_startDate")
	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	
	public java.lang.String getSiteId() {
		return siteId;
	}

	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}

	@Transient
    @WhereSQL(sql="createDate <=:SpComment_endDate")
	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	
	@Transient
	public java.lang.String[] getAllPicture() {
		if(StringUtils.isNotBlank(getPictureUrl())){
			
			allPicture=getPictureUrl().split(",");
		}
		return allPicture;
	}

	public void setAllPicture(java.lang.String[] allPicture) {
		this.allPicture = allPicture;
	}
	@Transient
	public java.lang.String getDateString() {
		dateString=DateUtils.convertDate2String("yyyy-MM-dd  HH:mm:ss", getCreateDate());
		return dateString;
	}

	public void setDateString(java.lang.String dateString) {
		this.dateString = dateString;
	}

	public String toString() {
		return new StringBuffer()
			.append("id[").append(getId()).append("],")
			.append("关联的Id（商品Id或者是分店Id或者是总店Id）[").append(getBusinessId()).append("],")
			.append("分数（1 差评 3中评、5好评）[").append(getGrade()).append("],")
			.append("评价内容[").append(getReviewContent()).append("],")
			.append("逗号隔开[").append(getPictureUrl()).append("],")
			.append("评论会员账号[").append(getCommentMember()).append("],")
			.append("评论会员的Id[").append(getCommnentMemberId()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
			.append("回复人的账号[").append(getDoUser()).append("],")
			.append("关联的回复Id（整个会话只存一个Id，只在回复的时候用）[").append(getParentId()).append("],")
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SpComment == false) return false;
		if(this == obj) return true;
		SpComment other = (SpComment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
@Transient
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Transient
	public java.lang.String[] getPictureUrls() {
		if(StringUtils.isNotBlank(getPictureUrl())){
			pictureUrls=getPictureUrl().split(",");
		}
		return pictureUrls;
	}

	public void setPictureUrls(java.lang.String[] pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
	
	
}

	
