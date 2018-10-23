package model;

public class bean_products_types {
    private int type_id;
    private String type_name;
    private String type_describe;

    public bean_products_types(){};

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_describe() {
        return type_describe;
    }

    public void setType_describe(String type_describe) {
        this.type_describe = type_describe;
    }

}
