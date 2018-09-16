package com.wut.store.web.servlet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wut.store.domain.PageBean;
import com.wut.store.domain.Product;
import com.wut.store.service.ProductService;
import com.wut.store.service.impl.ProductServiceImpl;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;
import com.wut.store.utils.CookieUtils;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * 根据分类ID查询分类下的商品（分页显示）：findByCid
	 */
	public String findByCid(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接收参数
		String cid=req.getParameter("cid");
		Integer currPage=Integer.parseInt(req.getParameter("currPage"));
		//调用业务层
	//	ProductService productService=new ProductServiceImpl();
		ProductService productService=(ProductService) BeanFactory.getBean("productService");
		PageBean<Product> pageBean=productService.findByPageCid(cid,currPage);
		
		req.setAttribute("pageBean", pageBean);
		return "/jsp/product_list.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	//	return null;
	}
	/*
	 * 商品详情方法：findByPid
	 * 
	 */
	public String findByPid(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接受参数
		String pid=req.getParameter("pid");
		//调用业务层
		ProductService productService=(ProductService) BeanFactory.getBean("productService");
		Product product=productService.findByPid(pid);
		//记录用户的商品浏览记录:history
		//从Cookie中获得指定名称的Cookie
		Cookie[] cookies=req.getCookies();
		Cookie cookie=CookieUtils.findCookie(cookies, "history");
		if(cookie==null){
			//没有浏览记录
			Cookie c=new Cookie("history", pid);
			c.setPath("/store_v2.0");
			c.setMaxAge(7*24*60*60);
			resp.addCookie(c);
		}else{
			//有浏览记录：1-2-3 
			//判断是否已经浏览过该商品 1-2-3 点击 2-1-3 如果点击4  4-2-1-3
			String value=cookie.getValue();
			String[] ids=value.split("-");
			//将数组转成linkedList集合
			LinkedList<String> list=new LinkedList<String>(Arrays.asList(ids));
			//判断是否已经在浏览记录中：
			if(list.contains(pid)){
				//已经浏览过该商品
				list.remove(pid);
				list.addFirst(pid);
			}else{
				//没有浏览过
				if(list.size()>=6){
					//超过6个
					list.removeLast();
					list.addFirst(pid);
				}else{
					//没有超过6个
					list.addFirst(pid);
				}
			}
			StringBuffer sb=new StringBuffer();
			//遍历List集合 拼接成一个字符串
			for(String id:list){
				sb.append(id).append("-");
			}
			String idStr=sb.toString().substring(0,sb.length()-1);
			Cookie c=new Cookie("history", idStr);
			c.setPath("/store_v2.0");
			c.setMaxAge(7*24*60*60);
			resp.addCookie(c);
		}
		
		req.setAttribute("p", product);
		return "/jsp/product_info.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	
	}
}
