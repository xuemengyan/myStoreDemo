package com.yanxuemeng.dao;

import com.yanxuemeng.domain.Category;
import com.yanxuemeng.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select * from category order by cid";
       return qr.query(sql,new BeanListHandler<>(Category.class));


    }
}
