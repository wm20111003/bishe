package com.centfor.school.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.centfor.cms.directive.util.DirectiveUtils;
import com.centfor.frame.shiro.MemberUser;
import com.centfor.school.entity.SpMember;
import com.centfor.school.service.ISpMemberService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component("memberDirective")
public class MemberDirective implements TemplateDirectiveModel{
	@Resource
	ISpMemberService memberService;
	/**
	 * ouyang
	 * 模板名称(个人信息)
	 */
	public static final String TPL_NAME = "sp_member_bean";
	@SuppressWarnings({ "rawtypes","unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		SpMember member=null;
		if(MemberUser.getMember()!=null){
			//登陆用户所属店铺
			String siteId=MemberUser.getMember().getSiteId();
			//登陆用户账号
			String account=MemberUser.getAccount();
			//会员可用状态
			String state=DirectiveUtils.getString("state", params);
			try {
				member=memberService.finderMemberByAccountAndState(siteId, account, state);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Map<String,TemplateModel> paramWrap =new HashMap<String ,TemplateModel>(params);
		paramWrap.put(DirectiveUtils.OUT_BEAN, DEFAULT_WRAPPER.wrap(member));

		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		
		body.render(env.getOut());

		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);

	}

}
