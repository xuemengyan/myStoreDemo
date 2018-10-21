package com.yanxuemeng.dao;

import com.yanxuemeng.domain.Product;
import com.yanxuemeng.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class UploadDao {
    public int addProduct(Product product) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        int rows = qr.update(sql, product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(),
                product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(),
                product.getCid());
        return  rows;
    }
}
