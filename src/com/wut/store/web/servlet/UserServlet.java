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
 * 用户模块servlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 通过servlet转跳注册页面方法：registUI
	 */
	public String registUI(HttpServletRequest req,HttpServletResponse resp){
		return "/jsp/regist.jsp";
	}
	
	/**
	 * 异步校验用户名的执行方法：checkUsername
	 */
	public String checkUsername(HttpServletRequest req,HttpServletResponse resp){
//		System.out.println("1111");
		try{
		//接受参数
		String username=req.getParameter("username");
		//调用业务层
		
		//UserService userService=new UserServiceImpl();
		UserService userService=(UserService) BeanFactory.getBean("userService");
		User exituser=userService.findByUsername(username);
		if(exituser==null){
			//没查到，用户名可以使用
			resp.getWriter().println("1");
		}else{
			//查到了，用户名被占用了
			resp.getWriter().println("2");
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
		
	}
	
	/**
	 * 用户注册方法：regist
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public String regist(HttpServletRequest req,HttpServletResponse resp) {
		try{
		//接受参数
		Map<String,String[]> map=req.getParameterMap();
		//封装数据
		User user=new User();
		ConvertUtils.register(new MyDateConverter(), Date.class);
		BeanUtils.populate(user, map);
		//调用业务层处理数据
		UserService userService=(UserService) BeanFactory.getBean("userService");
		userService.save(user);
		//页面跳转
		req.setAttribute("msg", "注册成功！请去邮箱激活！");
		return "/jsp/msg.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 通过servlet激活方法：active
	 */
	public String active(HttpServletRequest req,HttpServletResponse resp){
		try{
		//接收参数
		String code=req.getParameter("code");
		//调用业务层处理数据
		UserService userService=(UserService) BeanFactory.getBean("userService");
		User exitUser=userService.findByCode(code);
		if(exitUser==null){
			//不存在
			req.setAttribute("msg", "验证码有误！请重新验证！");
			
		}else{
			//存在
			
			exitUser.setState(2);
			exitUser.setCode(null);
			userService.update(exitUser);
			req.setAttribute("msg", "验证成功！");
		}
		return "/jsp/msg.jsp";	
		
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 通过servlet转跳登录页面方法：loginUI
	 */
	public String loginUI(HttpServletRequest req,HttpServletResponse resp){
		
		
		return "/jsp/login.jsp";
	}
	/**
	 * 通过servlet转跳登录方法：login
	 */
	public String login(HttpServletRequest req,HttpServletResponse resp){
		try{
		//一次性验证码程序：
			String code=req.getParameter("code");
			String code2=(String) req.getSession().getAttribute("code");
			req.getSession().removeAttribute("code");
			if(!code.equalsIgnoreCase(code2)){
				req.setAttribute("msg", "验证码输入错误！");
				return "/jsp/login.jsp";
			}
		//接收数据
		Map<String,String[]>map=req.getParameterMap();
		//封装数据
		User user=new User();
		BeanUtils.populate(user, map);
		//调用Service处理数据
		UserService userService=(UserService) BeanFactory.getBean("userService");
		User exitUser=userService.login(user);
		if(exitUser==null){
			req.setAttribute("msg", "用户名或者密码错误，可能没激活！");
			return "/jsp/login.jsp";
		}else{
			//登录成功：自动登录
			String autoLogin=req.getParameter("autoLogin");
			if("true".equals(autoLogin)){
				Cookie cookie=new Cookie("autoLogin", exitUser.getUsername()+"#"+exitUser.getPassword());
				cookie.setPath("/store_v2.0");
				cookie.setMaxAge(7*24*60*60);
				resp.addCookie(cookie);
			}
			//记住用户名
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
	 * 通过servlet退出页面方法：logOut
	 */
	public String logOut(HttpServletRequest req,HttpServletResponse resp){
		//销毁session
		req.getSession().invalidate();
		//清空自动登录的cookie
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
