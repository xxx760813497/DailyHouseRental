package com.xmj.demo.entity;

public class Order {
    Integer id;
    House house;
    User user;
    String boardDate;
    String boardUserName;
    String boardUserTel;
    Integer orderPrice;
    String orderProduceTime;
    String orderState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(String boardDate) {
        this.boardDate = boardDate;
    }

    public String getBoardUserName() {
        return boardUserName;
    }

    public void setBoardUserName(String boardUserName) {
        this.boardUserName = boardUserName;
    }

    public String getBoardUserTel() {
        return boardUserTel;
    }

    public void setBoardUserTel(String boardUserTel) {
        this.boardUserTel = boardUserTel;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderProduceTime() {
        return orderProduceTime;
    }

    public void setOrderProduceTime(String orderProduceTime) {
        this.orderProduceTime = orderProduceTime;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", house=" + house +
                ", user=" + user +
                ", boardDate='" + boardDate + '\'' +
                ", boardUserName='" + boardUserName + '\'' +
                ", boardUserTel='" + boardUserTel + '\'' +
                ", orderPrice=" + orderPrice +
                ", orderProduceTime='" + orderProduceTime + '\'' +
                ", orderState='" + orderState + '\'' +
                '}';
    }
}
