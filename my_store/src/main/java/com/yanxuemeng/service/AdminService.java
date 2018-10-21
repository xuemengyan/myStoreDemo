package com.yanxuemeng.service;

import com.yanxuemeng.dao.AdminDao;
import com.yanxuemeng.domain.PageBean;
import com.yanxuemeng.domain.Product;

import java.util.List;

public class AdminService {


    public PageBean<Product> getPageBean(String curPage) {

        try {
            //封装pageBean
            PageBean<Product> pageBean = new PageBean<>();
            //封装当前页
            pageBean.setCurPage(Integer.parseInt(curPage));
            //封装每页显示数量
            int pageSize = 10;
            pageBean.setPageSize(pageSize);
            //封装总商品数
            AdminDao dao = new AdminDao();
            int count = dao.findAllProduct();
            pageBean.setCount(count);
            //封装总页数
            int totalPage = (int)Math.ceil(count*1.0/pageSize);
            pageBean.setTotalPage(totalPage);
            //封装每一页的商品集合
            List<Product> list = dao.findAllProductList(Integer.parseInt(curPage),pageSize);
            pageBean.setPageList(list);
            return pageBean;


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
