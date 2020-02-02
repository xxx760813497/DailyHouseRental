package com.xmj.demo.controller.consumer;

import com.xmj.demo.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class CommentaryImgController {

    @PostMapping("/commentaryImg")
    public String titleImgUpload (@RequestParam("file") MultipartFile file, HttpSession session){

        if (file.isEmpty()){
            return "error";
        }
        User user= (User) session.getAttribute("user");
        Integer userId=user.getId();
        String filePath=fileUpload(file,userId,"CommentaryImg");
        System.out.println("上传路径："+filePath);
        return  filePath;
    }

    @PostMapping("/deleteCommentaryImg")
    public String deleteHouseTitleImg(@RequestParam("filePath") String filePath){
        System.out.println("删除路径:"+filePath);
        if (deleteFile(filePath)){
            return "success";
        }else {
            return "error";
        }
    }


    public String fileUpload(MultipartFile file, Integer userId, String fileName){
        if (file.isEmpty()){
            return "error";
        }
        StringBuilder filePath=new StringBuilder("C:\\Users\\hasee\\Desktop\\DayRentHouse\\DayRentHouseView\\dayrenthouseview\\src\\assets\\CommentaryImgs");
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




    public boolean deleteFile(String filePath){
        File file=new File(filePath);
        if (file.exists()){
            return file.delete();
        }else {
            return false;
        }

    }

}
