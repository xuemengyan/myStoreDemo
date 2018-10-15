package com.yanxuemeng.service;

import com.yanxuemeng.dao.Userdao;
import com.yanxuemeng.domain.User;
import com.yanxuemeng.exception.PasswordErrorException;
import com.yanxuemeng.exception.UsernameExistsException;
import com.yanxuemeng.exception.UsernameNotExistsException;
import com.yanxuemeng.utils.Md5Util;
import com.yanxuemeng.utils.UUIDUtil;

import java.sql.SQLException;

public class userService {
    private Userdao dao = new Userdao();
    public boolean register(User user) throws Exception {
        //判断用户名是否存在

        User user_db =  dao.findUserByUsername(user.getUsername());
        if (user_db != null){
                //如果用户名存在,手动抛出异常,通知web层
            throw new UsernameExistsException("用户名已经存在");
            }

            //用户名不存在,程序继续执行,可以注册
        //对密码进行加密并把加密后的密码保存到user对象中
        String md5Password = Md5Util.encodeByMd5(user.getPassword());
        user.setPassword(md5Password);
        //生成一个32位永不重复的字符串作为主键,保存到user对象的uid;
        String uid = UUIDUtil.getId();
        user.setUid(uid);
        //注册,将表单数据存到数据库
        int rows = dao.register(user);
        return rows>0;
    }

    public User login(String username, String password) throws Exception {
        //判断登陆的用户名是否存在
        User user = dao.findUserByUsername(username);
        if (user ==null){   //用户不存在抛出自定义异常
            throw new UsernameNotExistsException("用户名不存在");
        }
        //用户存在,判断密码是否正确
        String md5Password = Md5Util.encodeByMd5(password);
        if (!md5Password.equals(user.getPassword())){
            throw new PasswordErrorException("密码错误");
        }
        return user;
    }
}
