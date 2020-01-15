package com.xmj.demo.tools;

import com.xmj.demo.unitilEmitity.Point;

public class LatLonUtil {

    private static final double PI = 3.14159265;
    private static final double EARTH_RADIUS = 6378137;
    private static final double RAD = Math.PI / 180.0;


    /**
     * 根据一个点与距离，得到一个园形范围
     */
    public static double[] getAround(Point p,int raidus){

        Double latitude = p.getLat();
        Double longitude = p.getLng();

        Double degree = (24901*1609)/360.0;
        double raidusMile = raidus;

        Double dpmLat = 1/degree;
        Double radiusLat = dpmLat*raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree*Math.cos(latitude * (PI/180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng*raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new double[]{minLat,minLng,maxLat,maxLng};
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     */
    public static double getDistance(Point p1,Point p2)
    {
        double lng1=p1.getLng();
        double lng2=p2.getLng();
        double lat1=p1.getLat();
        double lat2=p2.getLat();
        double radLat1 = lat1*RAD;
        double radLat2 = lat2*RAD;
        double a = radLat1 - radLat2;
        double b = (lng1 - lng2)*RAD;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}