package com.deathyyoung.struts2.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 该类是定义了一个过滤器，当既用到struts2有用到servlet时， 必须用该过滤器对所有的servlet做一次过滤，否则servlet将无法正常运行。
 * 同时在web.xml中，在定义struts的核心控制器前面添加该过滤器，添加代码如下： <filter>
 * <filter-name>redisp</filter-name>
 * <filter-class>filters.ReDispatcherFilter（该过滤器的类名）</filter-class> <init-param>
 * <param-name>includeServlets</param-name>
 * <param-value>所有配置的servlet名字</param-value> </init-param> </filter>
 * <filter-mapping> <filter-name>redisp</filter-name>
 * <url-pattern>/*</url-pattern> </filter-mapping>
 * 
 * @author xsl
 *
 */
public class ReDispatcherFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		String target = request.getRequestURI();
		if (target.contains("WEB-INF") || target.contains("web-inf")) {
			chain.doFilter(req, resp);
		}
		target = target.lastIndexOf("?") > 0 ? target.substring(
				target.lastIndexOf("/") + 1,
				target.lastIndexOf("?") - target.lastIndexOf("/")) : target
				.substring(target.lastIndexOf("/") + 1);

		if (this.includes.contains(target)) {
			request.getRequestDispatcher(target).forward(req, resp);
		} else
			chain.doFilter(req, resp);
	}

	private ArrayList<String> includes = new ArrayList<String>();

	public void init(FilterConfig config) throws ServletException {

		this.includes.addAll(Arrays.asList(config.getInitParameter(
				"includeServlets").split(",")));

	}

}