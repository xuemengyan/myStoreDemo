package com.yanxuemeng.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.yanxuemeng.dao.OrderDao;
import com.yanxuemeng.domain.*;
import com.yanxuemeng.utils.ConnectionManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class OrderService {
    private OrderDao dao = new OrderDao();
    public boolean submitOrder(Cart cart, User user, String oid) {
        try {
            //数据封装
            Orders orders = new Orders();
            //订单id
            orders.setOid(oid);
            //订单时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date());
            orders.setOrdertime(time);
            //订单总金额
            orders.setTotal(cart.getTotal());
            //订单状态
            orders.setState(0);
            //用户
            orders.setUid(user.getUid());
            //将购物项转为订单项
            Collection<CartItem> cartItemList = cart.getItemsMap();
            ArrayList<OrderItem> orderItmeList = new ArrayList<>();
            for (CartItem cartItem : cartItemList) {
                OrderItem orderItem = new OrderItem();
                //购买数量
                orderItem.setCount(cartItem.getCount());
                //小计
                orderItem.setSubTotal(cartItem.getSubTotal());
                //pid
                orderItem.setOid(oid);
                orderItem.setPid(cartItem.getProduct().getPid());
                orderItmeList.add(orderItem);
            }
            //开启事务
            ConnectionManager.begin();
            boolean flag = true;
            //将封装的order对象添加到数据库
            int rows = dao.saveOrders(orders);
            if (rows<1){
                flag = false;
            }
            //将订单项orderItmeList存到数据库
            for (OrderItem orderItem : orderItmeList) {
                int rows2 = dao.saveOrderItem(orderItem);
                if (rows2<1){
                    flag = false;
                    break;
                }
            }
            //提交事务
            if (flag){
                ConnectionManager.commit();
                return true;
            }else {
                ConnectionManager.rollback();
            }



        }catch (Exception e){
            e.printStackTrace();
            //回滚事务
            try {
                ConnectionManager.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }finally {
            try {
                ConnectionManager.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public PageBean<Orders> getOrderPage(int curPage, String uid) {
        try {
            PageBean<Orders> pageBean = new PageBean<>();
            //设置当前页
            pageBean.setCurPage(curPage);
            //设置每一页显示的订单数
            int pageSize = 2;
            pageBean.setPageSize(pageSize);
            //设置订单总数
            int count = dao.findOrderCountByUid(uid);
            pageBean.setCount(count);
            //设置总页数
            int totalPage = (int)Math.ceil(count * 1.0 / pageSize);
            pageBean.setTotalPage(totalPage);
            //设置订单列表
            List<Orders> list = dao.findOrdersList(uid,curPage,pageSize);
            //遍历集合
            for (Orders orders : list) {
                String oid = orders.getOid();
                //通过oid去查询订单详细信息
                List<OrderItemView>list2 = dao.findOrderInfo(oid);
                orders.setList(list2);
            }
            pageBean.setPageList(list);
            return pageBean;
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;

    }

    public Orders getOrderDetail(String oid) {
        try {
            Orders orders = dao.getOrderByOid(oid);
            List<OrderItemView> list = dao.findOrderInfo(oid);
            orders.setList(list);
            return orders;


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateInfo(String address, String name, String telephone, String oid) {
        try{
            int rows =   dao.updateInfo(address,name,telephone,oid);
            return  rows > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    public boolean updateState(String r6_order) {
        try{
            int rows =   dao.updateState(r6_order);
            return  rows > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;

    }
}
