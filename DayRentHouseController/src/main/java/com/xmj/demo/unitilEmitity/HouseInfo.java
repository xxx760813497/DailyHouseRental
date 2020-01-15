package com.xmj.demo.unitilEmitity;

public class HouseInfo {
    private Integer id;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "HouseInfo{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}
