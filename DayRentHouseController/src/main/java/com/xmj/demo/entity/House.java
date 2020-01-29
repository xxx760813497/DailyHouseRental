package com.xmj.demo.entity;

public class House {

    private Integer id;
    private Integer userId;
    private String name;
    private String location;
    private String address;
    private String houseTitleImg;
    private String describe;
    private String houseImgs;
    private Integer price;
    private String grade;
    private Integer orderQuantity;
    private String  equipments;
    private String  houseState;
    private String addDate;
    private String date;
    private double distance;
    private String userPhone;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHouseState() {
        return houseState;
    }

    public void setHouseState(String houseState) {
        this.houseState = houseState;
    }

    public House() {
    }

    public House(Integer userId, String name, String location,String address, String houseTitleImg, String describe, String houseImgs, Integer price, String equipments) {
        this.userId = userId;
        this.name = name;
        this.location = location;
        this.address = address;
        this.houseTitleImg = houseTitleImg;
        this.describe = describe;
        this.houseImgs = houseImgs;
        this.price = price;
        this.equipments = equipments;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", userId=" + userId +'\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", houseTitleImg='" + houseTitleImg + '\'' +
                ", describe='" + describe + '\'' +
                ", houseImgs='" + houseImgs + '\'' +
                ", price=" + price +
                ", grade='" + grade + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", equipments='" + equipments + '\'' +
                ", houseState='" + houseState + '\'' +
                ", addDate='" + addDate + '\'' +
                ", date='" + date + '\'' +
                ", distance=" + distance +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
