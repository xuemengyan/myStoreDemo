package com.yanxuemeng.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yanxuemeng.dao.ProductDao;
import com.yanxuemeng.domain.Category;
import com.yanxuemeng.domain.Product;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private ProductDao dao = new ProductDao();
    public Map<String, List<Product>> findNewestAndHot() {
        //调用dao层查询最新商品
        try {
            List<Product> newestList = dao.findNewest();
            List<Product> hotList = dao.findHot();
            //将数据封装到map集合中去
            HashMap<String, List<Product>> map = new HashMap<>();
            map.put("newestList",newestList);
            map.put("hotList",hotList);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询最热商品

        return null;
    }

    public Product findProductDetail(String pid)  {

        try {
            //调用dao层
            Map<String, Object> map = dao.findProductDetail(pid);
            //将map中的数据封装到product和category对象中去
            Product product = new Product();
            Category category = new Category();
            BeanUtils.populate(product,map);
            BeanUtils.populate(category,map);
            product.setCategory(category);
            return product;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
