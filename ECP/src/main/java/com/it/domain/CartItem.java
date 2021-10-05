package com.it.domain;

/**
 *  购物项
 *  包含: 商品对象,数量,小计
 */
public class CartItem {
    private Product product;   //商品对象
    private int count;         //数量
    private double subTotal;   //小计

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

    /**
     * 购物项中的小计价格
     * 小计=商品单价*购买数量
     */
    public double getSubTotal() {
        return subTotal = product.getShop_price()*count;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
