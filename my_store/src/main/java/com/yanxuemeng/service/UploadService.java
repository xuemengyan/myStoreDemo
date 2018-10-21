package com.yanxuemeng.service;

import com.yanxuemeng.dao.UploadDao;
import com.yanxuemeng.domain.Product;

public class UploadService {
    public boolean addProduct(Product product) {
        try {
            UploadDao dao = new UploadDao();
            int rows = dao.addProduct(product);
            return rows>0;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
