package com.wut.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.wut.store.domain.User;
import com.wut.store.service.UserService;
import com.wut.store.service.impl.UserServiceImpl;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;
import com.wut.store.utils.MailUtils;
import com.wut.store.utils.MyDateConverter;

/**
 * �û�ģ��servlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ͨ��servletת��ע��ҳ�淽����registUI
	 */
	public String registUI(HttpServletRequest req,HttpServletResponse resp){
		return "/jsp/regist.jsp";
	}
	
	/**
	 * �첽У���û�����ִ�з�����checkUsername
	 */
	public String checkUsername(HttpServletRequest req,HttpServletResponse resp){
//		System.out.println("1111");
		try{
		//���ܲ���
		String username=req.getParameter("username");
		//����ҵ���
		
		//UserService userService=new UserServiceImpl();
		UserService userService=(UserService) BeanFactory.getBean("userService");
		User exituser=userService.findByUsername(username);
		if(exituser==null){
			//û�鵽���û�������ʹ��
			resp.getWriter().println("1");
		}else{
			//�鵽�ˣ��û�����ռ����
			resp.getWriter().println("2");
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
		
	}
	
	/**
	 * �û�ע�᷽����regist
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public String regist(HttpServletRequest req,HttpServletResponse resp) {
		try{
		//���ܲ���
		Map<String,String[]> map=req.getParameterMap();
		//��װ����
		User user=new User();
		ConvertUtils.register(new MyDateConverter(), Date.class);
		BeanUtils.populate(user, map);
		//����ҵ��㴦������
		UserService userService=(UserService) BeanFactory.getBean("userService");
		userService.save(user);
		//ҳ����ת
		req.setAttribute("msg", "ע��ɹ�����ȥ���伤�");
		return "/jsp/msg.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * ͨ��servlet�������active
	 */
	public String active(HttpServletRequest req,HttpServletResponse resp){
		try{
		//���ղ���
		String code=req.getParameter("code");
		//����ҵ��㴦������
		UserService userService=(UserService) BeanFactory.getBean("userService");
		User exitUser=userService.findByCode(code);
		if(exitUser==null){
			//������
			req.setAttribute("msg", "��֤��������������֤��");
			
		}else{
			//����
			
			exitUser.setState(2);
			exitUser.setCode(null);
			userService.update(exitUser);
			req.setAttribute("msg", "��֤�ɹ���");
		}
		return "/jsp/msg.jsp";	
		
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * ͨ��servletת����¼ҳ�淽����loginUI
	 */
	public String loginUI(HttpServletRequest req,HttpServletResponse resp){
		
		
		return "/jsp/login.jsp";
	}
	/**
	 * ͨ��servletת����¼������login
	 */
	public String login(HttpServletRequest req,HttpServletResponse resp){
		try{
		//һ������֤�����
			String code=req.getParameter("code");
			String code2=(String) req.getSession().getAttribute("code");
			req.getSession().removeAttribute("code");
			if(!code.equalsIgnoreCase(code2)){
				req.setAttribute("msg", "��֤���������");
				return "/jsp/login.jsp";
			}
		//��������
		Map<String,String[]>map=req.getParameterMap();
		//��װ����
		User user=new User();
		BeanUtils.populate(user, map);
		//����Service��������
		UserService userService=(UserService) BeanFactory.getBean("userService");
		User exitUser=userService.login(user);
		if(exitUser==null){
			req.setAttribute("msg", "�û�������������󣬿���û���");
			return "/jsp/login.jsp";
		}else{
			//��¼�ɹ����Զ���¼
			String autoLogin=req.getParameter("autoLogin");
			if("true".equals(autoLogin)){
				Cookie cookie=new Cookie("autoLogin", exitUser.getUsername()+"#"+exitUser.getPassword());
				cookie.setPath("/store_v2.0");
				cookie.setMaxAge(7*24*60*60);
				resp.addCookie(cookie);
			}
			//��ס�û���
			String username=req.getParameter("remebern");
			if("true".equals(username)){
				Cookie cookie=new Cookie("username", exitUser.getUsername());
				cookie.setPath("/store_v2.0");
				cookie.setMaxAge(24*60*60);
				resp.addCookie(cookie);
			}
			
			req.getSession().setAttribute("exitUser", exitUser);
			resp.sendRedirect(req.getContextPath()+"/index.jsp");
			return null;
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * ͨ��servlet�˳�ҳ�淽����logOut
	 */
	public String logOut(HttpServletRequest req,HttpServletResponse resp){
		//����session
		req.getSession().invalidate();
		//����Զ���¼��cookie
		Cookie cookie=new Cookie("autoLogin", "");
		cookie.setPath("/store_v2.0");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		try {
			resp.sendRedirect(req.getContextPath()+"/index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
