package com.xmj.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;


@RestController
public class HouseService {




    @PostMapping("/houseTitleImg")
    public String titleImgUpload (@RequestParam("file") MultipartFile file, HttpSession session){

        if (file.isEmpty()){
            return "error";
        }

        Integer userId=20;
        String filePath=fileUpload(file,userId,"HouseTileImg");
        return  filePath;
    }

    @PostMapping("/deleteHouseImg")
    public String deleteHouseTitleImg(@RequestParam("filePath") String filePath){

        System.out.println(filePath);
        if (deleteFile(filePath)){
            return "success";
        }else {
            return "error";
        }


    }

    @PostMapping("/houseImgs")
    public String imgsUpload(@RequestParam("file") MultipartFile file){
        if (file==null){
            return "error";
        }
        Integer userId=20;
        long startTime = System.currentTimeMillis();
        String filePath=fileUpload(file,userId,"HouseImgs");
        long endTime = System.currentTimeMillis();
        System.out.println("程序执行时间："+(endTime-startTime)+"ms");

        return  filePath;
    }





    public String fileUpload(MultipartFile file,Integer userId,String fileName){
        if (file.isEmpty()){
            return "error";
        }
        StringBuilder filePath=new StringBuilder("C:\\Users\\hasee\\Desktop\\userimgs");
        filePath.append("\\"+userId+"\\");
        File houseFiles=new File(filePath.toString());
        if (!houseFiles.exists()){
            houseFiles.mkdirs();
        }
        String fileType=file.getContentType().substring(file.getContentType().indexOf("/")+1);
        String filename=userId+"_"+UUID.randomUUID()+"_"+fileName+"."+fileType;
        filePath.append(filename);
        System.out.println(filePath.toString());
        try {
            file.transferTo(new File(filePath.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return filePath.toString();
    }


    public boolean deleteFile(String filePath){
        File file=new File(filePath);
        if (file.exists()){
            return file.delete();
        }else {
            return false;
        }

    }

}
