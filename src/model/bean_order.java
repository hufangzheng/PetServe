package model;

import java.sql.*;

public class bean_order {
    private int order_id;
    private Integer product_id;
    private java.sql.Date order_begin_time;
    private java.sql.Date order_end_time;
    private int quantity;
    private double order_price;
    private String transfer_state;
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public bean_order(){};

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getTransfer_state() {
        return transfer_state;
    }

    public void setTransfer_state(String transfer_state) {
        this.transfer_state = transfer_state;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getOrder_begin_time() {
        return order_begin_time;
    }

    public void setOrder_begin_time(Date order_begin_time) {
        this.order_begin_time = order_begin_time;
    }

    public Date getOrder_end_time() {
        return order_end_time;
    }

    public void setOrder_end_time(Date order_end_time) {
        this.order_end_time = order_end_time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }



}
