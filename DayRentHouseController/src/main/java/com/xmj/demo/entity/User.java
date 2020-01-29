package com.xmj.demo.entity;


public class User {
    private Integer id;
    private String phonenum;
    private String password;
    private String status;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phonenum='" + phonenum + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
