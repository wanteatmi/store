package com.wut.store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Double total=0d;
	//Map�������ڴ�Ź�����ļ����б�����Ҫ����ɾ�����������list����Ʒ��id����Ϊmap��key
	private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>();
	public Double getTotal() {
		return total;
	}
	public Map<String, CartItem> getMap() {
		return map;
	}
	/*
	 * ������ķ���
	 */
	//��ӹ�������ﳵ
	public void addCart(CartItem cartItem){
		//�ж���Ʒ�Ƿ��Ѿ�����
		String pid=cartItem.getProduct().getPid();
		//�ж�pid�Ƿ���map ��key��
		if(map.containsKey(pid)){
			//�Ѿ�����
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
		}else{
			//���ﳵû�и���Ʒ
			map.put(pid, cartItem);
		}
		total+=cartItem.getSubtotal();
	}
	//�Ƴ�������
	public void removeCart(String pid){
		//��map���Ƴ�ĳ��Ԫ��
		CartItem cartItem=map.remove(pid);
		//�ܽ���ȥ�Ƴ��������С��
		total-=cartItem.getSubtotal();
	}
	//�������
	public void clearCart(){
		//���map
		map.clear();
		//�����ܽ��Ϊ0
		total=0d;
	}
}
