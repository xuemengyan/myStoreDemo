package com.yanxuemeng.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanxuemeng.domain.Cart;
import com.yanxuemeng.domain.CartItem;
import com.yanxuemeng.domain.Product;
import com.yanxuemeng.domain.ResultInfo;
import com.yanxuemeng.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet",urlPatterns = "/cart")
public class CartServlet extends BaseServlet {

//添加购物车
    protected void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求数据
        String pid = request.getParameter("pid");
        String count1 = request.getParameter("count");
        int count = Integer.parseInt(count1);
        //将商品信息封装为购物项(CartItem)
        CartItem cartItem = new CartItem();
        //封装商品
        Product product = new ProductService().findProductDetail(pid);
        cartItem.setProduct(product);
        //封装数量
        cartItem.setCount(count);
        //从session中获取购物车
        Cart cart = GetCart(request);
        cart.addCartItem(cartItem);
        //给页面做出相应
        ResultInfo info = new ResultInfo(ResultInfo.SUCCESS,null,null);
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);

    }

    private Cart GetCart(HttpServletRequest request){
        //1：先从session获取，如果没有，则创建Cart对象啊，再添加到Session中
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        if(cart == null){
            //如果是第一次获取
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        return  cart;
    }

    //获取购物车数据
    private void getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取购物车对象
        Cart cart = GetCart(request);
        //将购物车对象转为json,响应给页面
        ResultInfo info = new ResultInfo(ResultInfo.SUCCESS,cart,null);
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);
    }
    //删除购物项
    private void delCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String pid = request.getParameter("pid");
        //获取购物车对象
        Cart cart = GetCart(request);
        //删除数据
        cart.removeCartItem(pid);
        //将购物车对象响应回页面
        ResultInfo info = new ResultInfo(ResultInfo.SUCCESS,cart,null);
        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);

    }
    //清空购物车
    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        Cart cart = null;
        try {
            //获取购物车对象
            cart = GetCart(request);
            cart.clear();
            info = new ResultInfo(ResultInfo.SUCCESS,null,null);


        }catch (Exception e){
            e.printStackTrace();
            info = new ResultInfo(ResultInfo.FAILED,null,"服务器正在维护");
        }


        String jsonStr = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(jsonStr);

    }





}
