package com.wut.store.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wut.store.domain.User;
import com.wut.store.service.UserService;
import com.wut.store.service.impl.UserServiceImpl;
import com.wut.store.utils.CookieUtils;



/**
 * 鑷姩鐧诲綍鐨勮繃婊ゅ櫒鐨勫疄鐜�
 * @author admin
 *
 */
public class AutoLoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/**
		 * 鍒ゆ柇session涓槸鍚︽湁鐢ㄦ埛鐨勪俊鎭�:
		 * * session涓鏋滄湁:鏀捐.
		 * * session涓病鏈�:
		 *    * 浠嶤ookie涓幏鍙�:
		 *        * Cookie涓病鏈�:鏀捐.
		 *        * Cookie涓湁:
		 *            * 鑾峰彇Cookie涓瓨鐨勭敤鎴峰悕鍜屽瘑鐮佸埌鏁版嵁搴撴煡璇�.
		 *                * 娌℃湁鏌ヨ鍒�:鏀捐.
		 *                * 鏌ヨ鍒�:灏嗙敤鎴蜂俊鎭瓨鍏ュ埌session . 鏀捐.
		 */
		// 鍒ゆ柇session涓槸鍚︽湁鐢ㄦ埛鐨勪俊鎭�:
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		User exitUser = (User) session.getAttribute("exitUser");
		if(exitUser != null){
			// session涓湁鐢ㄦ埛淇℃伅.
			chain.doFilter(req, response);
		}else{
			// session涓病鏈夌敤鎴蜂俊鎭�.
			// 鑾峰緱Cookie鐨勬暟鎹�:
			Cookie[] cookies = req.getCookies();
			Cookie cookie = CookieUtils.findCookie(cookies, "autoLogin");
			// 鍒ゆ柇Cookie涓湁娌℃湁淇℃伅:
			if(cookie == null){
				// 娌℃湁鎼哄甫Cookie鐨勪俊鎭繃鏉�:
				chain.doFilter(req, response);
			}else{
				// 甯︾潃Cookie淇℃伅杩囨潵.
				String value = cookie.getValue();// aaa#111
				// 鑾峰緱鐢ㄦ埛鍚嶅拰瀵嗙爜:
				String username = value.split("#")[0];
				String password = value.split("#")[1];
				// 鍘绘暟鎹簱杩涜鏌ヨ:
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				UserService userService = new UserServiceImpl();
				try {
					exitUser = userService.login(user);
					if(exitUser == null){
						// 鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒:Cookie琚鏀圭殑.
						chain.doFilter(req, response);
					}else{
						// 灏嗙敤鎴峰瓨鍒皊ession涓�,鏀捐
						session.setAttribute("exitUser", exitUser);
						chain.doFilter(req, response);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void destroy() {
		
	}

}
