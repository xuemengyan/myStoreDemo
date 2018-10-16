package com.yanxuemeng.dao;

import com.yanxuemeng.domain.Product;
import com.yanxuemeng.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductDao {
    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    public List<Product> findNewest() throws SQLException {
        String sql = "select * from product order by pdate desc limit 0,9";
        return  qr.query(sql,new BeanListHandler<>(Product.class));
    }

    public List<Product> findHot() throws SQLException {
        String sql = "select * from product where is_hot=1 limit 0,9";
        return  qr.query(sql,new BeanListHandler<>(Product.class));
    }

    public Map<String, Object> findProductDetail(String pid) throws SQLException {
        String sql = "SELECT * FROM product p,category c WHERE p.cid = c.cid AND pid = ?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), pid);
        return map;
    }

    public int findAllProductCount(String cid) throws SQLException {
        String sql = "select count(*) from product where cid = ?";
        long count = (long)qr.query(sql, new ScalarHandler(),cid);

        return  (int)count;

    }

    public List<Product> findAllProductList(int curPage, int pageSize, String cid) throws SQLException {
        String sql = "select * from product where cid = ? limit ?,?";
        return  qr.query(sql, new BeanListHandler<>(Product.class),cid,(curPage -1)*pageSize,pageSize);


    }
}
