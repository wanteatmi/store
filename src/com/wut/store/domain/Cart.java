package com.wut.store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Double total=0d;
	//Map集合用于存放购物项的集合列表，由于要进行删除购物项，不用list，商品的id可作为map的key
	private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>();
	public Double getTotal() {
		return total;
	}
	public Map<String, CartItem> getMap() {
		return map;
	}
	/*
	 * 购物项的方法
	 */
	//添加购物项到购物车
	public void addCart(CartItem cartItem){
		//判断商品是否已经存在
		String pid=cartItem.getProduct().getPid();
		//判断pid是否在map 的key中
		if(map.containsKey(pid)){
			//已经存在
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
		}else{
			//购物车没有该商品
			map.put(pid, cartItem);
		}
		total+=cartItem.getSubtotal();
	}
	//移除购物项
	public void removeCart(String pid){
		//从map中移除某个元素
		CartItem cartItem=map.remove(pid);
		//总金额减去移除购物项的小计
		total-=cartItem.getSubtotal();
	}
	//清除所有
	public void clearCart(){
		//清空map
		map.clear();
		//设置总金额为0
		total=0d;
	}
}
