package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanxuemeng.domain.Category;
import com.yanxuemeng.domain.MyFlag;
import com.yanxuemeng.domain.ResultInfo;
import com.yanxuemeng.exception.CategoryDeleteErrorException;
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

    private CategoryService service = new CategoryService();
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


    protected void delCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1:获取请求数据
            String cid = request.getParameter("cid");
            //2:调用Service层,操作数据库
            boolean bl = service.delCategory(cid);
            //删除Redis
            delRedis();
            info = new ResultInfo(MyFlag.SUCCESS,null,null);
        }catch (CategoryDeleteErrorException e){
            e.printStackTrace();
            info = new ResultInfo(MyFlag.FAILED,null,e.getMessage());
        } catch (Exception e){
            info = new ResultInfo(MyFlag.FAILED,null,"服务器正在维护");
            e.printStackTrace();
        }
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        //将json响应给页面
        response.getWriter().write(jsonStr);
    }

    protected void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1:获取请求数据

            String cname = request.getParameter("cname");
            //2:调用Service层
            boolean bl = service.addCategory(cname);

            info = new ResultInfo(MyFlag.SUCCESS,null,null);
            //删除Reids中分类列表数据
            delRedis();
        } catch (Exception e) {
            info = new ResultInfo(MyFlag.FAILED,null,"服务器正在维护");
            e.printStackTrace();
        }

        String jsonStr = new ObjectMapper().writeValueAsString(info);
        //将json响应给页面
        response.getWriter().write(jsonStr);

    }




    public void delRedis(){
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("categoryList");
        jedis.close();
    }




}
