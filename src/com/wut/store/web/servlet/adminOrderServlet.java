package com.wut.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.wut.store.domain.Order;
import com.wut.store.domain.OrderItem;
import com.wut.store.service.OrderService;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;

/**
 * Servlet implementation class adminOrderServlet
 */
public class adminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String findAll(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接收状态
		String state = req.getParameter("state");
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		List<Order> list = null;
		if(state == null){
			//查询所有订单：
			list = orderService.findAll();
		}else{
			//按照状态查询订单
			int pstate = Integer.parseInt(state);
			list = orderService.findByState(pstate);
		}
		req.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "admin/order/list.jsp";
	}
	public String showDetail(HttpServletRequest req,HttpServletResponse resp){
		try {
			//接收参数
			String oid = req.getParameter("oid");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			List<OrderItem> list = orderService.showDetail(oid);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"order"});
			JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
			
			resp.getWriter().println(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String send(HttpServletRequest req,HttpServletResponse resp){
		try {
			//接收参数
			String oid = req.getParameter("oid");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setState(3);
			orderService.update(order);
			resp.sendRedirect(req.getContextPath()+"/adminOrderServlet?method=findAll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
