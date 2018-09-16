package com.wut.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wut.store.domain.Product;
import com.wut.store.service.ProductService;
import com.wut.store.service.impl.ProductServiceImpl;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String index(HttpServletRequest req, HttpServletResponse resp){
		try{
		//ProductService productService=new ProductServiceImpl();
			ProductService productService=(ProductService) BeanFactory.getBean("productService");
		//��ѯ������Ʒ
			List<Product> newList=productService.findByNew();
		//��ѯ������Ʒ
			List<Product> hotList=productService.findByHot();
			req.setAttribute("newList", newList);
			req.setAttribute("hotList", hotList);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/jsp/index.jsp";
	}
}
