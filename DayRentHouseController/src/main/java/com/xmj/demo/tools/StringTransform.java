package com.xmj.demo.tools;

import java.util.ArrayList;

/**
 * 本类用于将前端发送的字符串数组转换为以“，”分割的字符串
 */
public class StringTransform {

    /**
     *将ArrayList转换为字符串
     * @param imgsList
     * @return String
     */
    public static String stringsToString(ArrayList imgsList){
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<imgsList.size();i++){
            sb.append(imgsList.get(i));
            if (i!=imgsList.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串转换为字符串数组
     * @param imgsList
     * @return String[]
     */
    public static ArrayList<String> stringToStrings(String imgsList){
        String[] Imgs=imgsList.split(",");
        ArrayList<String> result=new ArrayList<>();
        for (String s:Imgs){
            result.add(s);
        }
        return result;
    }

    /**
     * 将后端文件路径转换为前端可以显示图片的路径
     * @param filePath
     * @return
     */
    public static String filePathOfView (String filePath){
        String s=filePath.substring(filePath.indexOf("src"));
        String s2=s.substring(3);
        String s3="@"+s2;
        String s4=s3.replaceAll("\\\\","/");
        return s4;
    }
}
