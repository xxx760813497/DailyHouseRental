package com.xmj.demo.controller.master;

import com.xmj.demo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
public class HouseImgUtil {







    public static String fileUpload(MultipartFile file,Integer userId,String fileName){
        if (file.isEmpty()){
            return "error";
        }
        StringBuilder filePath=new StringBuilder("C:\\Users\\hasee\\Desktop\\DayRentHouse\\DayRentHouseView\\dayrenthouseview\\src\\assets\\userImgs");
        filePath.append("\\"+userId+"\\");
        File houseFiles=new File(filePath.toString());
        if (!houseFiles.exists()){
            houseFiles.mkdirs();
        }
        String fileType=file.getContentType().substring(file.getContentType().indexOf("/")+1);
        String filename=userId+"_"+UUID.randomUUID()+"_"+fileName+"."+fileType;
        filePath.append(filename);

        try {
            file.transferTo(new File(filePath.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return filePath.toString();
    }




    public static boolean deleteFile(String filePath){
        File file=new File(filePath);
        if (file.exists()){
            return file.delete();
        }else {
            return false;
        }

    }

}
