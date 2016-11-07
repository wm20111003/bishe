package com.centfor.front.entity;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;



public class PriceProperty {
	/**
	 * 购物车的商品
	 */
	private List<SpCart> spCarts;
	
	/**
	 * 会员的优惠券
	 */
	private String couponId;
	
	/**
	 * 使用优惠券减的金额
	 */
	private Integer couponMoney=0;
	/**
	 * 使用的金币
	 */
	private Integer jinbi=0;
	
	/**
	 * 邮寄的省份
	 */
	private String province;
	
	/**
	 * 邮费
	 */
	private int youfei;
	
	/**
	 * 是否免邮
	 * 
	 */
	private boolean mianyou=false;
	/**
	 * 站点
	 */
	private String siteId;
	
	
	private Map map=new HashedMap();
	
	public PriceProperty(){
		
	}
	
	public PriceProperty(List<SpCart> spCarts,String couponId,Integer jinbi,String siteId){
		this.spCarts=spCarts;
		this.couponId=couponId;
		this.jinbi=jinbi;
		this.siteId=siteId;
	}

	public List<SpCart> getSpCarts() {
		return spCarts;
	}

	public void setSpCarts(List<SpCart> spCarts) {
		this.spCarts = spCarts;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Integer getJinbi() {
		return jinbi;
	}

	public void setJinbi(Integer jinbi) {
		this.jinbi = jinbi;
	}


	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	

	public int getYoufei() {
		return youfei;
	}

	public void setYoufei(int youfei) {
		this.youfei = youfei;
	}

	public boolean getMianyou() {
		return mianyou;
	}

	public void setMianyou(boolean mianyou) {
		this.mianyou = mianyou;
	}


	public Integer getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(Integer couponMoney) {
		this.couponMoney = couponMoney;
	}
	
	
	
	

}
