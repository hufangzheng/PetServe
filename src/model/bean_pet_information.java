package model;

import java.sql.Blob;

public class bean_pet_information {
    private int pet_id;
    private String nick_name;
    private String pet_name;
    private String pet_owner;
    //    private bean_user_information owner = new bean_user_information();
    private Blob picture_path;
    private Integer pet_age;
    private String healthy;
    private Integer product_id;

    public Integer getPet_age() {
        return pet_age;
    }

    public void setPet_age(Integer pet_age) {
        this.pet_age = pet_age;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_owner() {
        return pet_owner;
    }

    public void setPet_owner(String pet_owner) {
        this.pet_owner = pet_owner;
    }

    public Blob getPicture_path() {
        return picture_path;
    }
    //    }

    public void setPicture_path(Blob picture_path) {
        this.picture_path = picture_path;
    }
    //        this.user_id = user_id;

    public String getHealthy() {
        return healthy;
    }

    public void setHealthy(String healthy) {
        this.healthy = healthy;
    }

    public bean_pet_information(){};

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

}
