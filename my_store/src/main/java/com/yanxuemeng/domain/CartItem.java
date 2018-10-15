package com.yanxuemeng.domain;

public class CartItem {
    private Product product;
    private int count;
    private double subTotal;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubTotal() {
        return product.getShop_price()*count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", count=" + count +
                ", subTotal=" + subTotal +
                '}';
    }
}
