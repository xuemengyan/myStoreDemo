package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanxuemeng.domain.Product;
import com.yanxuemeng.domain.ResultInfo;
import com.yanxuemeng.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet",urlPatterns = "/product")
public class ProductServlet extends BaseServlet {
    private ProductService service = new ProductService();

    //查询最新和最热商品
    protected void findNewestAndHot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Map<String,List<Product>> map = service.findNewestAndHot();
      //将map转为json串返回给页面
        ResultInfo info = new ResultInfo(ResultInfo.SUCCESS,map,null);
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);
    }

    //查询指定商品的详细信息
    protected void findProductDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("zzzzz");
        ResultInfo info = null;
        //获取pid
        String pid = request.getParameter("pid");
        //调用service层查询数据库
        Product product = service.findProductDetail(pid);
        //将数据响应给浏览器
        if (product !=null){
            info = new ResultInfo(ResultInfo.SUCCESS,product,null);
        }else{
            info = new ResultInfo(ResultInfo.FAILED,null,"服务器正在维护");
        }
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);

    }



    }
