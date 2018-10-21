package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanxuemeng.domain.MyFlag;
import com.yanxuemeng.domain.PageBean;
import com.yanxuemeng.domain.Product;
import com.yanxuemeng.domain.ResultInfo;
import com.yanxuemeng.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet(name = "adminServlet",urlPatterns = "/admin")
public class adminServlet extends BaseServlet {

    protected void getProductPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try{
                //获取当前页
                String curPage = request.getParameter("curPage");
                //去service层获取pageBean对象
                AdminService service = new AdminService();
                PageBean<Product> pageBean = service.getPageBean(curPage);
                info = new ResultInfo(MyFlag.SUCCESS,pageBean,null);

        }catch (Exception e){
            e.printStackTrace();
            info = new ResultInfo(MyFlag.FAILED,null,"服务器正在维护");
        }
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);


    }


}
