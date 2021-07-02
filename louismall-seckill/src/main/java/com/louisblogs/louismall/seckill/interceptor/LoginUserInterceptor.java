package com.louisblogs.louismall.seckill.interceptor;

import com.louisblogs.common.constant.AuthServerConstant;
import com.louisblogs.common.vo.MemberRespVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ：luqi
 * @description：TODO
 * @date ：2021/6/26 17:02
 */

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

	public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		AntPathMatcher antPathMatcher = new AntPathMatcher();
		boolean match = antPathMatcher.match("/kill", request.getRequestURI());
		if (match) {
			HttpSession session = request.getSession();
			MemberRespVo attribute = (MemberRespVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
			if (attribute != null) {
				//说明登录了 放行
				loginUser.set(attribute);
				return true;
			} else {
				//没登录 去登录页 来拦截
				session.setAttribute("msg", "请先登录～～");
				response.sendRedirect("http://auth.louismall.com/login.html");
				return false;
			}
		}
		return true;
	}
}
