package com.it.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AjaxFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain chain) throws IOException, ServletException {
        //实现Ajax跨域访问,设置响应头
        //强制转换
        HttpServletRequest request= (HttpServletRequest)servletrequest;
        HttpServletResponse response = (HttpServletResponse)servletresponse;
        response.setHeader("Access-Control-Allow-Origin","http://www.ecps.com:8020");
        response.setHeader("Access-Control-Allow-Credentials","true");
        chain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
