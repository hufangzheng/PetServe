package model;

import java.sql.*;

public class bean_appointment {
    private int appointment_id;
    private String user_id;
    private Integer product_id;
    private java.sql.Date appointment_begin_time;
    private java.sql.Date appointment_end_time;
    private String circumstance;
    private Integer pet_id;

    public Integer getPet_id() {
        return pet_id;
    }

    public void setPet_id(Integer pet_id) {
        this.pet_id = pet_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public bean_appointment(){}

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public Date getAppointment_begin_time() {
        return appointment_begin_time;
    }

    public void setAppointment_begin_time(Date appointment_begin_time) {
        this.appointment_begin_time = appointment_begin_time;
    }

    public Date getAppointment_end_time() {
        return appointment_end_time;
    }

    public void setAppointment_end_time(Date appointment_end_time) {
        this.appointment_end_time = appointment_end_time;
    }

    public String getCircumstance() {
        return circumstance;
    }

    public void setCircumstance(String circumstance) {
        this.circumstance = circumstance;
    }

}
