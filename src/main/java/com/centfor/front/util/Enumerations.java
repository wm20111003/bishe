package com.centfor.front.util;

public class Enumerations {
	// 订单状态
	public enum OrderState {
		等待付款(0), 买家已付款(1), 货到付款(2), 卖家已发货(3), 订单已完成(4), 申请退款(5), 退款中(6), 订单退款完成(
				7), 订单完成评价(8), 订单已取消(9);
		int state;

		private OrderState(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}

		public static OrderState getOrderState(int state) {
			switch (state) {
			case 0:
				return 等待付款;
			case 1:
				return 买家已付款;
			case 2:
				return 货到付款;
			case 3:
				return 卖家已发货;
			case 4:
				return 订单已完成;
			case 5:
				return 申请退款;
			case 6:
				return 退款中;
			case 7:
				return 订单退款完成;
			case 8:
				return 订单完成评价;
			case 9:
				return 订单已取消;

			default:
				return 等待付款;
			}
		}

	}

	// 积分增减类型状态
	public enum JifenType {
		订单增加(0), 签到(1),分享好友(2);
		int state;

		private JifenType(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}

		public static JifenType getJifenType(int state) {
			switch (state) {
			case 0:
				return 订单增加;
			case 1:
				return 签到;
			default:
				return 订单增加;
			}
		}
	}


	// 账户金额增减类型状态
	public enum AddMoneyType {
		充值(0), 提现(1), 购物消费(2), 退款(3), 提成(4), 返利(5);
		int state;

		private AddMoneyType(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}

		public static AddMoneyType getAddMoneyType(int state) {
			switch (state) {
			case 0:
				return 充值;
			case 1:
				return 提现;
			case 2:
				return 购物消费;
			case 3:
				return 退款;
			case 4:
				return 提成;
			case 5:
				return 返利;
			default:
				return 充值;
			}
		}
	}

	// 金币增减类型增减状态
	public enum JinbiType {
		购物返还(0), 购物消费(1), 签到(2), 抽奖消耗(3), 抽奖获取(4), 购物退款(5);
		int state;

		private JinbiType(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}

		public static JinbiType getJinbiType(int state) {
			switch (state) {
			case 0:
				return 购物返还;
			case 1:
				return 购物消费;
			case 2:
				return 签到;
			case 3:
				return 抽奖消耗;
			case 4:
				return 抽奖获取;
			case 5:
				return 购物退款;
			default:
				return 购物返还;
			}
		}
	}

	// 支付类型
	public enum PaymentType {
		微信支付("1001"), 网银在线网页("1002"), 支付宝网页("1003"), 货到付款("1004"), 支付宝手机("1005"), 网银在线手机(
				"1006"), 账户余额("1007");
		String state;

		private PaymentType(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}

		public static PaymentType getPaymentType(String state) {
			if ("1001".equals(state)) {
				return 微信支付;
			} else if ("1002".equals(state)) {
				return 网银在线网页;
			} else if ("1003".equals(state)) {
				return 支付宝网页;
			} else if ("1004".equals(state)) {
				return 货到付款;
			} else if ("1005".equals(state)) {
				return 支付宝手机;
			} else if ("1006".equals(state)) {
				return 网银在线手机;
			} else if ("1007".equals(state)) {
				return 账户余额;
			}

			return null;
		}
	}

	// 提现类型
	public enum WithdrawCashType {
		等待审核("2001"), 审核未通过("2002"), 已撤销("2003"), 处理中("2004"), 成功("2005"), 提现失败(
				"2006");
		String state;

		private WithdrawCashType(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}

		public static WithdrawCashType getWithdrawCashType(String state) {
			if ("2001".equals(state)) {
				return 等待审核;
			} else if ("2002".equals(state)) {
				return 审核未通过;
			} else if ("2003".equals(state)) {
				return 已撤销;
			} else if ("2004".equals(state)) {
				return 处理中;
			} else if ("2005".equals(state)) {
				return 成功;
			} else if ("2006".equals(state)) {
				return 提现失败;
			}
			return 等待审核;
		}
	}

	// 优惠券是否使用状态
	public enum isUseCoupon {
		已使用, 未使用, 已过期;

	}

	// 消息类型
	public enum MessageType {
		系统消息(0), 优惠消息(1), 物流消息(2);
		int state;

		private MessageType(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}

		public static MessageType getMessageType(int state) {
			switch (state) {
			case 0:
				return 系统消息;
			case 1:
				return 优惠消息;
			case 2:
				return 物流消息;

			}
			return null;
		}
	}

	public enum PushType {
		个人(1), 部分(2), 全部(3);
		int state;

		private PushType(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}

		public static PushType getPushType(int state) {
			switch (state) {
			case 0:
				return 个人;
			case 1:
				return 部分;
			case 2:
				return 全部;

			}
			return null;
		}
	}

	// 订单类型 普通订单、秒杀订单、团购订单
	public enum OrderType {
		普通订单(1), 秒杀订单(2), 团购订单(3);
		int state;

		private OrderType(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}
		
		public static OrderType getOrderType(int state) {
			switch (state) {
			case 1:
				return 普通订单;
			case 2:
				return 秒杀订单;
			case 3:
				return 团购订单;

			}
			return 普通订单;
		}

	}

}
