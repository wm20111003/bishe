package com.centfor.system.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @copyright {@link 9iu.org}
 * @author bbz<Auto generate>
 * @version  2013-07-06 16:03:00
 * @see com.centfor.system.entity.User
 */
@Table(name="t_user")
public class User  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "用户";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_NAME = "姓名";
	public static final String ALIAS_WORKNO = "工号";
	public static final String ALIAS_ACCOUNT = "账号";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_CARDNO = "身份证";
	public static final String ALIAS_AGE = "年龄";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_PHONE = "电话号码";
	public static final String ALIAS_MOBILE = "手机号码";
	public static final String ALIAS_EAMIL = "邮箱";
	public static final String ALIAS_ADDRESS = "地址";
	public static final String ALIAS_GRADEID = "级别";
	public static final String ALIAS_EDUNAME = "学历";
	public static final String ALIAS_ENTRYDATE = "入职日期";
	public static final String ALIAS_STARTDATE = "转正日期";
	public static final String ALIAS_ENDDATE = "离职日期";
	public static final String ALIAS_FIRENAME = "紧急联系人";
	public static final String ALIAS_FIREPHONE = "紧急联系电话";
	public static final String ALIAS_DESCRIPTION = "备注";
	public static final String ALIAS_STATE = "是否有效,是/否/离职";
    */
	//date formats
	//public static final String FORMAT_ENTRYDATE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_STARTDATE = DateUtils.DATETIME_FORMAT;
	//public static final String FORMAT_ENDDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 姓名
	 */
	private java.lang.String name;
	/**
	 * 工号
	 */
	private java.lang.String workno;
	/**
	 * 账号
	 */
	private java.lang.String account;
	/**
	 * 密码
	 */
	private java.lang.String password;
	/**
	 * 身份证
	 */
	private java.lang.String cardno;
	/**
	 * 年龄
	 */
	private java.lang.Integer age;
	/**
	 * 性别
	 */
	private java.lang.String sex;
	/**
	 * 电话号码
	 */
	private java.lang.String phone;
	/**
	 * 手机号码
	 */
	private java.lang.String mobile;
	/**
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 地址
	 */
	private java.lang.String address;
	/**
	 * 级别
	 */
	private java.lang.String gradeId;
	/**
	 * 学历
	 */
	private java.lang.String eduName;

	/**
	 * 紧急联系人
	 */
	private java.lang.String fireName;
	/**
	 * 紧急联系电话
	 */
	private java.lang.String firePhone;
	/**
	 * 备注
	 */
	private java.lang.String description;
	/**
	 * 是否有效,是/否/离职
	 */
	private java.lang.String state;
	/**
	 * 头像图片路径
	 */
	private java.lang.String headimgsrc;
	
	/**
	 * 创建此用户店铺的ID
	 */
	private java.lang.String siteId;
	
	//columns END 数据库字段结束

	//用户所有的部门
	private List<Org> userOrgs;
	//用户的所有角色
	private List<Role> userRoles;
	
	
	

	

	//concstructor
	public User(){
	}

	public User(
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
	     @WhereSQL(sql="id=:User_id")
		public java.lang.String getId() {
			return this.id;
		}
		public void setName(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.name = value;
		}
		
	     @WhereSQL(sql="name=:User_name")
		public java.lang.String getName() {
			return this.name;
		}
		public void setWorkno(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.workno = value;
		}
		
		@Transient
	     @WhereSQL(sql="workno=:User_workno")
		public java.lang.String getWorkno() {
			return this.workno;
		}
		public void setAccount(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.account = value;
		}
		
	     @WhereSQL(sql="account=:User_account")
		public java.lang.String getAccount() {
			return this.account;
		}
		public void setPassword(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.password = value;
		}
		
	     @WhereSQL(sql="password=:User_password")
		public java.lang.String getPassword() {
			return this.password;
		}
		public void setCardno(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.cardno = value;
		}
		
	     @WhereSQL(sql="cardno=:User_cardno")
		public java.lang.String getCardno() {
			return this.cardno;
		}
		public void setAge(java.lang.Integer value) {
			this.age = value;
		}
		
	     @WhereSQL(sql="age=:User_age")
		public java.lang.Integer getAge() {
			return this.age;
		}
		public void setSex(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.sex = value;
		}
		
	     @WhereSQL(sql="sex=:User_sex")
		public java.lang.String getSex() {
			return this.sex;
		}
		public void setPhone(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.phone = value;
		}
		
	     @WhereSQL(sql="phone=:User_phone")
		public java.lang.String getPhone() {
			return this.phone;
		}
		public void setMobile(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.mobile = value;
		}
		
	     @WhereSQL(sql="mobile=:User_mobile")
		public java.lang.String getMobile() {
			return this.mobile;
		}
		public void setEmail(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.email = value;
		}
		
	     @WhereSQL(sql="eamil=:User_email")
		public java.lang.String getEmail() {
			return this.email;
		}
		public void setAddress(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.address = value;
		}
		
	     @WhereSQL(sql="address=:User_address")
		public java.lang.String getAddress() {
			return this.address;
		}
		public void setGradeId(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.gradeId = value;
		}
		
	     @WhereSQL(sql="gradeId=:User_gradeId")
		public java.lang.String getGradeId() {
			return this.gradeId;
		}
		public java.lang.String getEduName() {
			return eduName;
		}

		public void setEduName(java.lang.String eduName) {
			this.eduName = eduName;
		}

		

		public void setFireName(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.fireName = value;
		}
		
	     @WhereSQL(sql="fireName=:User_fireName")
		public java.lang.String getFireName() {
			return this.fireName;
		}
		public void setFirePhone(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.firePhone = value;
		}
		
	     @WhereSQL(sql="firePhone=:User_firePhone")
		public java.lang.String getFirePhone() {
			return this.firePhone;
		}
		public void setDescription(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.description = value;
		}
		
	     @WhereSQL(sql="description=:User_description")
		public java.lang.String getDescription() {
			return this.description;
		}
		public void setState(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.state = value;
		}
		
	     @WhereSQL(sql="state=:User_state")
		public java.lang.String getState() {
			return this.state;
		}
	     
		public void setSiteId(java.lang.String value) {
			if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.siteId = value;
		}
		
		 @WhereSQL(sql="siteId=:User_siteId")
		public java.lang.String getSiteId() {
			return siteId;
		}
			
	     @Transient
	 	public java.lang.String getHeadimgsrc() {
			return headimgsrc;
		}

		public void setHeadimgsrc(java.lang.String headimgsrc) {
			this.headimgsrc = headimgsrc;
		}
		
		public String toString() {
			return new StringBuffer()
			.append("编号[").append(getId()).append("],")
			.append("姓名[").append(getName()).append("],")
			.append("工号[").append(getWorkno()).append("],")
			.append("账号[").append(getAccount()).append("],")
			.append("密码[").append(getPassword()).append("],")
			.append("身份证[").append(getCardno()).append("],")
			.append("年龄[").append(getAge()).append("],")
			.append("性别[").append(getSex()).append("],")
			.append("电话号码[").append(getPhone()).append("],")
			.append("手机号码[").append(getMobile()).append("],")
			.append("邮箱[").append(getEmail()).append("],")
			.append("地址[").append(getAddress()).append("],")
			.append("级别[").append(getGradeId()).append("],")
			.append("学历[").append(getEduName()).append("],")
			.append("紧急联系人[").append(getFireName()).append("],")
			.append("紧急联系电话[").append(getFirePhone()).append("],")
			.append("备注[").append(getDescription()).append("],")
			.append("是否有效,是/否/离职[").append(getState()).append("],")
			.append("创建用户店铺的id[").append(getSiteId()).append("],")
			.toString();
		}
		
		public int hashCode() {
			return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
		}
		
		public boolean equals(Object obj) {
			if(obj instanceof User == false) return false;
			if(this == obj) return true;
			User other = (User)obj;
			return new EqualsBuilder()
				.append(getId(),other.getId())
				.isEquals();
		}

		@Transient
		public List<Org> getUserOrgs() {
			return userOrgs;
		}

		public void setUserOrgs(List<Org> userOrgs) {
			this.userOrgs = userOrgs;
		}

		@Transient
		public List<Role> getUserRoles() {
			return userRoles;
		}

		public void setUserRoles(List<Role> userRoles) {
			this.userRoles = userRoles;
		}
}

	
