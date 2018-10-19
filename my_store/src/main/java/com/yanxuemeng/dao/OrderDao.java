package com.yanxuemeng.dao;

import com.yanxuemeng.domain.OrderItem;
import com.yanxuemeng.domain.OrderItemView;
import com.yanxuemeng.domain.Orders;
import com.yanxuemeng.utils.C3P0Utils;
import com.yanxuemeng.utils.ConnectionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDao {
     private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    public int saveOrders(Orders orders) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into orders values(?,?,?,?,null,null,null,?)";
        int rows = qr.update(ConnectionManager.getConnection(),sql, orders.getOid(), orders.getOrdertime(), orders.getTotal(), orders.getState(), orders.getUid());
        return rows;
    }

    public int saveOrderItem(OrderItem orderItem) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into orderitem values(?,?,?,?)";
        int rows = qr.update(ConnectionManager.getConnection(),sql, orderItem.getCount(), orderItem.getSubTotal(), orderItem.getPid(), orderItem.getOid());
        return rows;
    }

    public int findOrderCountByUid(String uid) throws SQLException {
        String sql ="select count(*) from orders where uid = ?";
        long query = (long)qr.query(sql, new ScalarHandler(), uid);
        return (int)query;
    }

    public List<Orders> findOrdersList(String uid, int curPage, int pageSize) throws SQLException {
        String sql = "select * from orders where uid = ? limit ?,?";
        List<Orders> list = qr.query(sql, new BeanListHandler<>(Orders.class), uid, (curPage - 1) * pageSize, pageSize);

        return list;
    }

    public List<OrderItemView> findOrderInfo(String oid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select p.pname,p.pimage,p.shop_price,oi.count,oi.subtotal from product p, orderitem oi where p.pid = oi.pid and  oi.oid = ?";
        List<OrderItemView> list = qr.query(sql, new BeanListHandler<>(OrderItemView.class), oid);
        return  list;
    }

    public Orders getOrderByOid(String oid) throws SQLException {
        String sql = "select * from orders where oid = ?";
        Orders orders = qr.query(sql, new BeanHandler<>(Orders.class), oid);
        return orders;
    }

    public int updateInfo(String address, String name, String telephone, String oid) throws SQLException {
        String sql = "update orders set address = ?,name=?,telephone=? where oid = ?";
        int rows = qr.update(sql, address, name, telephone, oid);
        return  rows;

    }

    public int updateState(String r6_order) throws SQLException {
        String sql = "update orders set state = 1 where oid = ?";
        int rows = qr.update(sql, r6_order);
        return  rows;
    }
}
