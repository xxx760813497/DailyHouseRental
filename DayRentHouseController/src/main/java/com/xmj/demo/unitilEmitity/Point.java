package com.xmj.demo.unitilEmitity;

public class Point {

    private double lng;
    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Point(String address) {
        String[] addressStr=address.split(",");
        this.lng= Double.parseDouble(addressStr[0]);
        this.lat= Double.parseDouble(addressStr[1]);
    }


}
