package com.wut.store.domain;

public class CartItem {
	private Product product;
	private Integer count;
	private Double subtotal;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return count*product.getShop_price();
	}
	
	
}
