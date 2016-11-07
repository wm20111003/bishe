package com.centfor.frame.shiro;


import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.centfor.system.entity.User;

public class ShiroUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 0.女1.男
	 */
	private java.lang.String sex;
	
	private User user;

	public ShiroUser() {

	}

	public ShiroUser(User user) {
		this.id = user.getId();
		this.account = user.getAccount();
		this.name = user.getName();
		this.email = user.getEmail();
		this.workno = user.getWorkno();
		this.sex=user.getSex();
		this.user=user;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.account;
	}

	/**
	 * 重载equals,只计算account;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getAccount())
			.toHashCode();
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	
	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getWorkno() {
		return workno;
	}

	public void setWorkno(java.lang.String workno) {
		this.workno = workno;
	}

	public java.lang.String getAccount() {
		return account;
	}

	public void setAccount(java.lang.String account) {
		this.account = account;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}