package com.xmj.demo.entity;

public class House {

    //ak MwYHY5IRaImP4rvu6U19MNrmk77rKT9O

    private Integer id;
    private Integer userId;
    private String name;
    private String address;
    private String houseTitleImg;
    private String describe;
    private String houseImgs;
    private Integer price;
    private String grade;
    private Integer orderQuantity;
    private String  equipments;

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", houseTitleImg='" + houseTitleImg + '\'' +
                ", describe='" + describe + '\'' +
                ", houseImgs='" + houseImgs + '\'' +
                ", price=" + price +
                ", grade='" + grade + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", equipments='" + equipments + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseTitleImg() {
        return houseTitleImg;
    }

    public void setHouseTitleImg(String houseTitleImg) {
        this.houseTitleImg = houseTitleImg;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getHouseImgs() {
        return houseImgs;
    }

    public void setHouseImgs(String houseImgs) {
        this.houseImgs = houseImgs;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
