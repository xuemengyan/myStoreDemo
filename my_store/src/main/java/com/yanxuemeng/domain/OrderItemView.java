package com.yanxuemeng.domain;

public class OrderItemView {
    //商品名  商品图片, 数量, 小计
    private String pname;
    private String pimage;
    private double shop_price;
    private int count;
    private double subTotal;

    public OrderItemView() {
    }

    public OrderItemView(String pname, String pimage, int count, double subTotal) {
        this.pname = pname;
        this.pimage = pimage;
        this.count = count;
        this.subTotal = subTotal;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "OrderItemView{" +
                "pname='" + pname + '\'' +
                ", pimage='" + pimage + '\'' +
                ", count=" + count +
                ", subTotal=" + subTotal +
                '}';
    }
}
