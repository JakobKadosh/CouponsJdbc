package com.yakov.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yakov.coupons.beans.PostLoginUserData;
import com.yakov.coupons.beans.UserLoginData;
import com.yakov.coupons.logic.MyCachManager;

@Component
@WebFilter("/*")
public class LoginFilter implements Filter {

	@Autowired
	private MyCachManager cacheManager;

	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("options")) {
			chain.doFilter(request, response);
			return;
		}

		String path = ((HttpServletRequest) request).getRequestURI();
		if (path.startsWith("/users/login")) {

			chain.doFilter(request, response); 

		} 
		else if(path.startsWith("/customers") && ((HttpServletRequest) request).getMethod().equalsIgnoreCase("POST")){
			chain.doFilter(request, response); 
			
		}else{
			HttpServletRequest req = (HttpServletRequest) request;

			Integer token = Integer.parseInt(req.getParameter("token"));
			PostLoginUserData postlogin = (PostLoginUserData) cacheManager.get(token);
			if (postlogin != null) {

				request.setAttribute("userData", postlogin);
				chain.doFilter(request, response);
				return;

			}

			HttpServletResponse res = (HttpServletResponse) response;
			//	        401 = Unauthorized http error code
			res.setStatus(401);

			res.setHeader("ErrorCause", "Couldn't find a login session");
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
