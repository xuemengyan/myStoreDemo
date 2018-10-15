package com.yanxuemeng.domain;

public class OrderItem {


    //订单项数量
    private Integer count;
    //订单项小计
    private Double subtotal;
    private String pid;
    private String oid;

    private Product product;


    public OrderItem() {
    }

    public OrderItem(Integer count, Double subtotal, String pid, String oid, Product product) {
        this.count = count;
        this.subtotal = subtotal;
        this.pid = pid;
        this.oid = oid;
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "count=" + count +
                ", subtotal=" + subtotal +
                ", pid='" + pid + '\'' +
                ", oid='" + oid + '\'' +
                ", product=" + product +
                '}';
    }
}
