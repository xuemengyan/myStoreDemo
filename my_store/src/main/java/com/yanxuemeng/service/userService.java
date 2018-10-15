package com.yanxuemeng.service;

import com.yanxuemeng.dao.Userdao;
import com.yanxuemeng.domain.User;
import com.yanxuemeng.exception.UsernameExistsException;
import com.yanxuemeng.utils.Md5Util;
import com.yanxuemeng.utils.UUIDUtil;

import java.sql.SQLException;

public class userService {
    public boolean register(User user) throws Exception {
        //判断用户名是否存在
        Userdao dao = new Userdao();
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
}
