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
	 * ���ݷ���ID��ѯ�����µ���Ʒ����ҳ��ʾ����findByCid
	 */
	public String findByCid(HttpServletRequest req,HttpServletResponse resp){
		try{
		//���ղ���
		String cid=req.getParameter("cid");
		Integer currPage=Integer.parseInt(req.getParameter("currPage"));
		//����ҵ���
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
	 * ��Ʒ���鷽����findByPid
	 * 
	 */
	public String findByPid(HttpServletRequest req,HttpServletResponse resp){
		try{
		//���ܲ���
		String pid=req.getParameter("pid");
		//����ҵ���
		ProductService productService=(ProductService) BeanFactory.getBean("productService");
		Product product=productService.findByPid(pid);
		//��¼�û�����Ʒ�����¼:history
		//��Cookie�л��ָ�����Ƶ�Cookie
		Cookie[] cookies=req.getCookies();
		Cookie cookie=CookieUtils.findCookie(cookies, "history");
		if(cookie==null){
			//û�������¼
			Cookie c=new Cookie("history", pid);
			c.setPath("/store_v2.0");
			c.setMaxAge(7*24*60*60);
			resp.addCookie(c);
		}else{
			//�������¼��1-2-3 
			//�ж��Ƿ��Ѿ����������Ʒ 1-2-3 ��� 2-1-3 ������4  4-2-1-3
			String value=cookie.getValue();
			String[] ids=value.split("-");
			//������ת��linkedList����
			LinkedList<String> list=new LinkedList<String>(Arrays.asList(ids));
			//�ж��Ƿ��Ѿ��������¼�У�
			if(list.contains(pid)){
				//�Ѿ����������Ʒ
				list.remove(pid);
				list.addFirst(pid);
			}else{
				//û�������
				if(list.size()>=6){
					//����6��
					list.removeLast();
					list.addFirst(pid);
				}else{
					//û�г���6��
					list.addFirst(pid);
				}
			}
			StringBuffer sb=new StringBuffer();
			//����List���� ƴ�ӳ�һ���ַ���
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
