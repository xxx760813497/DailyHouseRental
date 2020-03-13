package com.xmj.demo.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class NoLoginController {

    @RequestMapping("/nologin")
    public Map userLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("接收到未登录请求");
        Map map=new HashMap();
        map.put("msg","noLogin");
        return map;
    }

}
