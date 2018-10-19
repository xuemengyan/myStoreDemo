package com.yanxuemeng.service;

import com.yanxuemeng.dao.CategoryDao;
import com.yanxuemeng.domain.Category;
import com.yanxuemeng.exception.CategoryDeleteErrorException;
import com.yanxuemeng.utils.UUIDUtil;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    CategoryDao dao = new CategoryDao();
    public List<Category> findAllCategory() {
        CategoryDao dao = new CategoryDao();
        List<Category> list = null;
        try {
            list = dao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public boolean delCategory(String cid) throws SQLException, CategoryDeleteErrorException {
        //cid是否有依赖的从表.没有从表数据才能删除
        int count = dao.isProduct(cid);
        if (count>0){
            throw new CategoryDeleteErrorException("该分类有商品数据");
        }else {
            int rows = dao.delCategory(cid);
            return  rows > 0;
        }
    }

    public boolean addCategory(String cname) {
        try{
            int rows = dao.addCategory(UUIDUtil.getId(),cname);
            return  rows > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    public Category findCategoryByCid(String cid) {
        try{
            return  dao.findCategoryByCid(cid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public boolean updateCategory(Category category) {
        try{
            int rows = dao.updateCategory(category);
            return  rows > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }
}
