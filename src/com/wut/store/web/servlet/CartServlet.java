package com.wut.store.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wut.store.domain.Cart;
import com.wut.store.domain.CartItem;
import com.wut.store.domain.Product;
import com.wut.store.service.ProductService;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * ��ӵ����ﳵ�ķ�����addCart
	 */
	public String addCart(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// ���ղ���
			String pid = req.getParameter("pid");
			Integer count = Integer.parseInt(req.getParameter("count"));
		//	System.out.println("pid="+pid+"  count="+count);
			// ��װCartItem��
			CartItem cartItem = new CartItem();
			cartItem.setCount(count);
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
		//	System.out.println(product.getPname());
			cartItem.setProduct(product);
			// ����Cart�еķ�������
			Cart cart = getCart(req);
			cart.addCart(cartItem);
			// ҳ����ת
			resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	private Cart getCart(HttpServletRequest req) {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	/*
	 * ��չ��ﳵ
	 */
	public String clearCart(HttpServletRequest req, HttpServletResponse resp){
		
		
		try {
			//����Cart��clearCart����
			Cart cart = getCart(req);
			cart.clearCart();
			resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	/*
	 * ɾ����Ʒ
	 */
	public String removeCart(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			String pid = req.getParameter("pid");
			Cart  cart = getCart(req);
			cart.removeCart(pid);
			resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
