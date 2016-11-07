package com.centfor.frame.shiro;

import org.apache.shiro.util.ThreadContext;

import com.centfor.school.entity.SpMember;




public class MemberUser {
	
	public static SpMember getMember(){
		SpMember member=(SpMember) ThreadContext.getSubject().getSession().getAttribute("spMember");
		return member;
	}
	
	public static String getUserId(){
		String userId=(String) ThreadContext.getSubject().getSession().getAttribute("userId");
		return userId;
	}
	
	public static String getAccount(){
		String account=(String) ThreadContext.getSubject().getSession().getAttribute("account");
		return account;
	}
	public static String getGradeId(){
		String gradeId=(String) ThreadContext.getSubject().getSession().getAttribute("gradeId");
		return gradeId;
	}
	
	

}
