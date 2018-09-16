package com.wut.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.wut.store.domain.User;

public class PrivilegeFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		User exitUser = (User) req.getSession().getAttribute("exitUser");
		if(exitUser==null){
			req.setAttribute("msg", "您还没有登录！！没有权限访问~");
			req.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
		chain.doFilter(req,response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
