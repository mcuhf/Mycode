package com.imooc.mall.filter;


import com.imooc.mall.common.Constant;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *是否为管理员过滤器
 */

public class AdminFilter implements Filter {
    @Autowired
    UserService userService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpSession session = request1.getSession();
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser==null){
            PrintWriter writer = new HttpServletResponseWrapper((HttpServletResponse) response).getWriter();
            writer.write("{\n" +
                     "    \"status\": 10007,\n" +
                     "    \"msg\": \"NEED_LOGIN\",\n" +
                     "    \"data\": null\n" +
                     "}");
             writer.flush();
             writer.close();
             return;
        }
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            chain.doFilter(request,response);
        }else{
            PrintWriter writer = new HttpServletResponseWrapper((HttpServletResponse) response).getWriter();
            writer.write("{\n" +
                    "    \"status\": 10009,\n" +
                    "    \"msg\": \"NEED_ADMIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
