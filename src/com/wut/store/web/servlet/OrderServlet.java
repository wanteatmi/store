package com.wut.store.web.servlet;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wut.store.domain.Cart;
import com.wut.store.domain.CartItem;
import com.wut.store.domain.Order;
import com.wut.store.domain.OrderItem;
import com.wut.store.domain.PageBean;
import com.wut.store.domain.User;
import com.wut.store.service.OrderService;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;
import com.wut.store.utils.PaymentUtil;
import com.wut.store.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * ���ɶ�����ִ�з�����saveOrder
	 */
	public String saveOrder(HttpServletRequest req,HttpServletResponse resp){
		//��װorder����
		Order order =new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setState(1);//1.δ����
		//�����ܽ��,�ӹ��ﳵ�л���ܽ��
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		if(cart==null){
			req.setAttribute("msg", "���ﳵ�ǿյ���~");
			return "/jsp/msg.jsp";
		}
		order.setTotal(cart.getTotal());
		//���������û�����¼�û���Ϣ
		User exitUser = (User) req.getSession().getAttribute("exitUser");
		if(exitUser==null){
			req.setAttribute("msg", "����û�е�¼��~");
			return "/jsp/login.jsp";
		}
		//order.setName(exitUser.getName());
		order.setUser(exitUser);
		//���ö�����
		for(CartItem cartItem:cart.getMap().values()){
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		//����ҵ�����ɱ��棺
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService"); 
		orderService.save(order);
		
		//��չ��ﳵ
		cart.clearCart();
		
		//ҳ����ת
		req.setAttribute("order", order);
		return "/jsp/order_info.jsp";
		
	}
	/*
	 * �����û�ID��ѯ������findByUid
	 */
	public String findByUid(HttpServletRequest req,HttpServletResponse resp){
		try{
		//���ղ���
		Integer currPage = Integer.parseInt(req.getParameter("currPage"));
		//����û�����Ϣ
		User user = (User) req.getSession().getAttribute("exitUser");
		//����ҵ��
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		PageBean<Order> pageBean = orderService.findByUid(user.getUid(),currPage);
		
		req.setAttribute("pageBean", pageBean);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		//ҳ����ת
		return "/jsp/order_list.jsp";
	}
	/*
	 * ���ݶ���ID��ѯ�����ķ�����findByOid
	 */
	public String findByOid(HttpServletRequest req,HttpServletResponse resp) {
		try{
		//���ղ���
		String oid=req.getParameter("oid");
		//����ҵ���
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		Order order = orderService.findByOid(oid);
		req.setAttribute("order", order);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";
	}
	/*
	 * Ϊ�����������
	 */
	public String payOrder(HttpServletRequest req,HttpServletResponse resp){
		try{
			// ���ղ���
			String oid = req.getParameter("oid");
			String name = req.getParameter("name");
			String address = req.getParameter("address");
			String telephone = req.getParameter("telephone");
			String pd_FrpId = req.getParameter("pd_FrpId");

			// �޸����ݿ⣬��ַ���绰
			OrderService orderService = (OrderService) BeanFactory
					.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			orderService.update(order);
			// �����ת�������Ľ���
			//׼��������
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:8080/store_v2.0/OrderServlet?method=callBack";
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			resp.sendRedirect(sb.toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	/*
	 * ����ɹ���callBack
	 */
	public String callBack(HttpServletRequest req,HttpServletResponse resp){
		
		
		try {
			//���ܲ���
			String oid = req.getParameter("r6_Order");
			String money = req.getParameter("r3_Amt");
			
			//�޸Ķ���״̬
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setState(2);
			orderService.update(order);
			req.setAttribute("msg", "���Ķ�����"+oid+"����ɹ������Ϊ��"+money);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/jsp/msg.jsp";
	}
	public String finish(HttpServletRequest req,HttpServletResponse resp){
		
		String oid = req.getParameter("oid");
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		Order order = new Order();
		order.setState(4);
		try {
			orderService.update(order);
			resp.sendRedirect(req.getContextPath()+"/OrderServlet?method=findByUid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
}
