package com.yanxuemeng.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private User user;
    private Double total=0.0;
    /**
     * 以pid为key 购物项为值
     */
    private Map<String,CartItem> items=new HashMap<>();


    //添加购物项
    public void addCartItem(CartItem item){
        //将购物项添加到购物项集合中
        //判断该购物项所关联的商品是否已经在购物车中了?
        String pid = item.getProduct().getPid();

        if (items.containsKey(pid)){
            //有了
            //将数量+=新增的数量
            //获取原本就有的
            CartItem ori = items.get(pid);
            ori.setCount(ori.getCount()+item.getCount());

        }else {

            //没有
            //直接添加
            items.put(pid,item);
        }

        //将总金额 +=新增购物项的小计

        total+=item.getSubTotal();

    }
    //删除购物项
    public void removeCartItem(String pid){
        //将购物项从集合中移除
        CartItem remove = items.remove(pid);

        //总金额-=移除的购物项的小计
        total-=remove.getSubTotal();


    }
    //清空购物车
    public void clear(){
        //集合清空
        items.clear();
        //总金额=0;
        total=0.0;


    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    @Override
    public String toString() {
        return "Cart2{" +
                "user=" + user +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
}
