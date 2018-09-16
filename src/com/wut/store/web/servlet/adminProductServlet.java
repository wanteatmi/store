package com.wut.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wut.store.domain.Category;
import com.wut.store.domain.PageBean;
import com.wut.store.domain.Product;
import com.wut.store.service.CategoryService;
import com.wut.store.service.ProductService;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;

/**
 * 后台商品管理的Servlet
 */
public class adminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * 商品分页查询的Servlet
	 */
	public String findByPage(HttpServletRequest req,HttpServletResponse resp){
		
		try {
			//接受参数
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			//调用业务层
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			PageBean<Product> pageBean = productService.findByPage(currPage);
			req.setAttribute("pageBean", pageBean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";
	}
	
	/*
	 * 跳转到添加页面的执行的方法：
	 */
	public String saveUI(HttpServletRequest req,HttpServletResponse resp){
		//查询所有分类
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		try {
			List<Category> list = categoryService.findAll();
			req.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}
	/*
	 * 下架商品
	 */
	public String pushDown(HttpServletRequest req,HttpServletResponse resp){
		
		try {
			//接收参数
			String pid = req.getParameter("pid");
			//调用业务层
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			product.setPflag(1);
			productService.update(product);
			resp.sendRedirect(req.getContextPath()+"/adminProductServlet?method=findByPage&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 查询已经下架的商品
	 */
	public String findByPushDown(HttpServletRequest req,HttpServletResponse resp){
		
		ProductService productService = (ProductService) BeanFactory.getBean("productService");
		try {
			List<Product> list = productService.findByPushDown();
			req.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/admin/product/pushDown_list.jsp";
	}
	/*
	 * 上架商品
	 */
	public String pushUp(HttpServletRequest req,HttpServletResponse resp){
	
		try {
			//接收参数
			String pid = req.getParameter("pid");
			//调用业务层
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			product.setPflag(0);
			productService.update(product);
			resp.sendRedirect(req.getContextPath()+"/adminProductServlet?method=findByPage&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

