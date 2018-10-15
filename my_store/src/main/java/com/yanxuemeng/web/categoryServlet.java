package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanxuemeng.domain.Category;
import com.yanxuemeng.service.CategoryService;
import com.yanxuemeng.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "categoryServlet",urlPatterns = "/category")
public class categoryServlet extends BaseServlet {


    protected void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先从redis获取,如果没有就从数据库获取并存到redis中
        Jedis jedis = JedisUtils.getJedis();
        String categoryList = jedis.get("categoryList");
        if (categoryList==null){
            //调用servers层方法从数据库获取数据
            CategoryService service = new CategoryService();
            List<Category> list = service.findAllCategory();
            //将list集合转为json串并存到redis中
            categoryList = new ObjectMapper().writeValueAsString(list);
            jedis.set("categoryList",categoryList);
            //关闭jedis
            JedisUtils.close(jedis);
        }
        //将json串响应给页面

        response.getWriter().write(categoryList);
    }
}
