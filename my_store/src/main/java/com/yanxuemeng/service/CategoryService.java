package com.yanxuemeng.service;

import com.yanxuemeng.dao.CategoryDao;
import com.yanxuemeng.domain.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
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
}
