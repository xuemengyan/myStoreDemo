package com.yanxuemeng.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class CharSetFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1:解决请求参数乱码
        HttpServletRequest request = (HttpServletRequest)req;
        String method = request.getMethod();

        if(method.equals("POST")) {
            req.setCharacterEncoding("UTF-8");
        }
        resp.setContentType("text/html;charset=UTF-8");
        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
