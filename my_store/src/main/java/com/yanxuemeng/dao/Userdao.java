package com.yanxuemeng.dao;

import com.mchange.v2.c3p0.impl.C3P0ImplUtils;
import com.yanxuemeng.domain.User;
import com.yanxuemeng.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class Userdao {
    QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    public User findUserByUsername(String username) throws SQLException {
        //查询用户名是否存在
        String sql = "select * from user where username = ?";
        User user = qr.query(sql, new BeanHandler<>(User.class), username);
        return user;
    }

    public int register(User user) throws SQLException {
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        return qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
                user.getName(),user.getEmail(),user.getBirthday(),user.getGender(),
                0,"111",null);

    }
}
