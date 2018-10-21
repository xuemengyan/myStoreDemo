package com.yanxuemeng.dao;

import com.yanxuemeng.domain.Product;
import com.yanxuemeng.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDao {
    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    public int findAllProduct() throws SQLException {
        String sql = "select count(*) from product";
        long count = (long)qr.query(sql, new ScalarHandler());
        return (int)count;
    }

    public List<Product> findAllProductList(int curPage, int pageSize) throws SQLException {
        String sql = "select * from product limit ?,?";
        return qr.query(sql,new BeanListHandler<>(Product.class),(curPage-1)*pageSize,pageSize);
    }
}
