package com.imooc.mall.filter;

import com.imooc.mall.common.Constant;
import com.imooc.mall.model.pojo.User;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserFilter implements Filter {
    public static User currentUser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpSession session = request1.getSession();
        currentUser  = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser==null) {
            PrintWriter writer = new HttpServletResponseWrapper((HttpServletResponse) response).getWriter();
            writer.write("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"NEED_LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            writer.flush();
            writer.close();
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
