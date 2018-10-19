package com.yanxuemeng.dao;

import com.yanxuemeng.domain.Category;
import com.yanxuemeng.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    public List<Category> findAllCategory() throws SQLException {

        String sql = "select * from category order by cid";
       return qr.query(sql,new BeanListHandler<>(Category.class));


    }

    public int isProduct(String cid) throws SQLException {
        String sql = "select count(*) from product where cid = ?";
        long count = (long)qr.query(sql, new ScalarHandler(), cid);
        return (int)count;
    }

    public int delCategory(String cid) throws SQLException {
        String sql = "delete from category where cid = ?";
        int rows = qr.update(sql, cid);
        return rows;
    }

    public int addCategory(String cid, String cname) throws SQLException {
        String sql = "insert into category values(?,?)";
        int rows = qr.update(sql, cid, cname);
        return rows;
    }

    public Category findCategoryByCid(String cid) throws SQLException {
        String sql = "select * from category where cid = ?";
        Category category = qr.query(sql, new BeanHandler<>(Category.class), cid);
        return  category;
    }

    public int updateCategory(Category category) throws SQLException {
        String sql = "update category set cname = ? where cid = ?";
        return  qr.update(sql, category.getCname(),category.getCid());
    }
}
