package com.centfor.frame.util;


/**
 * Web层信息管理类
 *
 * @copyright {@link 9iu.org}
 * @author centfor<Auto generate>
 * @version  2013-03-19 11:08:15
 * @see com.centfor.frame.util.MessageUtils
 */
public class MessageUtils {
	public static final String REFUND_NOTIFY_URL=ResourceUtils.getString("refund_notify_url");//退款回调路径
	public static final String YI_LIAO_MALL_ID=ResourceUtils.getString("yi_liao_mall_id");//医疗商城用户id,共分利使用
	
	public static final String ADD_SUCCESS=ResourceUtils.getString("add_success");//数据添加成功
	public static final String ADD_FAIL=ResourceUtils.getString("add_fail");//数据添加失败
	
	public static final String UPDATE_SUCCESS=ResourceUtils.getString("update_success");//数据修改成功
	public static final String UPDATE_ERROR=ResourceUtils.getString("update_error");//数据修改异常

	
	public static final String SELECT_SUCCESS=ResourceUtils.getString("select_success");//数据查询成功
	public static final String SELECT_WARING=ResourceUtils.getString("select_warning");//数据查询异常
	
	
	public static final String SIGN_SUCCESS=ResourceUtils.getString("sign_success");//签到成功
	public static final String SIGN_WARING=ResourceUtils.getString("sign_warning");//今天已签到
	
	public static final String SEND_SUCCESS=ResourceUtils.getString("send_success");//发货成功
	public static final String SEND_WARING=ResourceUtils.getString("send_warning");//发货
	
	public static final String SIGN_INFO=ResourceUtils.getString("sign_info");//签到详情 已签到
	
	public static final String NO_ACCESS=ResourceUtils.getString("no_access");//没有此权限
	
	public static final String CONFIRM_GOODS=ResourceUtils.getString("confirm_goods");//确认发货模板信息
	
	

	public static final String DELETE_SUCCESS=ResourceUtils.getString("delete_success");//数据删除成功
	public static final String DELETE_WARNING=ResourceUtils.getString("delete_warning");//数据删除警告
	public static final String DELETE_ALL_WARNING=ResourceUtils.getString("delete_all_waring");//批量数据删除警告
	public static final String DELETE_ALL_FAIL=ResourceUtils.getString("delete_all_fail");//批量数据删除失败
	public static final String DELETE_NULL_FAIL=ResourceUtils.getString("delete_null_fail");//批量数据删除失败
	public static final String DELETE_ALL_SUCCESS=ResourceUtils.getString("delete_all_success");//批量数据删除成功
	
	public static final String RESET_PASSWORD_SUCCESS=ResourceUtils.getString("reset_password_success");//密码重置成功	

	public static final String ROLE_ASSIGN_SUCCESS=ResourceUtils.getString("role_assign_success");//角色分配成功

	public static final String RESOURCE_ASSIGN_SUCCESS=ResourceUtils.getString("resource_assign_success");//资源分配成功
	
	public static final String PASSWORD_ERROR=ResourceUtils.getString("password_error");//旧密码输入错误

	public static final String PASSWORD_EDIT_SUCCESS=ResourceUtils.getString("password_edit_success");//密码修改成功

	public static final String NOTICE_RELEASE_SUCCESS=ResourceUtils.getString("notice_release_success");//通知公告发布成功
	
	public static final String NOTICE_RELEASE_ERROR=ResourceUtils.getString("notice_release_error");//通知公告发布失败
	
	public static final String ADD_MEMBER_COUPON_SUCCESS=ResourceUtils.getString("add_member_coupon_success");//领取优惠券成功
	public static final String ADD_MEMBER_COUPON_FAIL=ResourceUtils.getString("add_member_coupon_fail");//领取优惠券失败
	public static final String COUPON_INVALID=ResourceUtils.getString("coupon_invalid");//优惠券已失效
	public static final String COUPON_RECEIVE_OVER=ResourceUtils.getString("coupon_receive_over");//优惠券已被领取完
	public static final String COUPON_LIMIT=ResourceUtils.getString("coupon_limit");//领取的优惠券已达上限
	
	public static final String COLLECT_CONTENT_REPEAT=ResourceUtils.getString("collect_content_repeat");//商品已收藏
	public static final String COLLECT_CONTENT_FAIL=ResourceUtils.getString("collect_content_fail");//商品收藏失败
	public static final String COLLECT_CONTENT_SUCCESS=ResourceUtils.getString("collect_content_success");//商品收藏成功
	
	public static final String COMMENT_ORDER_SUCCESS=ResourceUtils.getString("comment_order_success");//评价订单成功
	public static final String COMMENT_ORDER_FAIL=ResourceUtils.getString("comment_order_fail");//评价订单失败
	public static final String COMMENT_ORDER_OVER=ResourceUtils.getString("comment_order_over");//订单已评价
	
	public static final String NOTICE_ADD_SUCCESS=ResourceUtils.getString("notice_add_success");//消息发送成功
	public static final String NOTICE_ADD_FAIL=ResourceUtils.getString("notice_add_fail");//消息发送失败
	
	public static final String FEED_BACK_SUCCESS=ResourceUtils.getString("feed_back_success");//反馈成功
	public static final String FEED_BACK_FAIL=ResourceUtils.getString("feed_back_fail");//反馈失败
	
	public static final String PLEASE_LOGIN=ResourceUtils.getString("please_login");//请先登录
	public static final String LOGINED=ResourceUtils.getString("logined");//已登录
}