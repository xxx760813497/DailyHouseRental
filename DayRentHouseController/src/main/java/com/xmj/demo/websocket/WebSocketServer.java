package com.xmj.demo.websocket;


import com.google.gson.Gson;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
//    private AtomicInteger i=new AtomicInteger(0);
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static HashMap<String,WebSocketServer> webSocketMap = new HashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId){
        this.session=session;
        this.userId=userId;
        if (webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
        }else {
            webSocketMap.put(userId,this);
            addOnlineCount();
        }
        System.out.println("用户"+this.userId+"连接成功！，当前在线人数："+getOnlineCount());


    }

    @OnClose
    public void onClose(){
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        System.out.println("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }


    //接收到客户端的消息后的动作
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("用户"+userId+"发来消息："+message);
        Gson gson=new Gson();
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
//                JSONObject jsonObject =gson.fromJson(message,JSONObject.class);
                //追加发送人(防止串改)
//                jsonObject.put("fromUserId",this.userId);


                //传送给对应toUserId用户的websocket
//                String toUserId=jsonObject.getString("toUserId");
//                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
//                    webSocketMap.get(toUserId).sendMessage(gson.toJson(jsonObject));
//                }else{
//                    System.out.println("用户不在线，消息已暂时存入服务器中，用户上线后自动发送");
//                    //否则不在这个服务器上，发送到mysql或者redis
//                }
                HashMap<String,String> map=new HashMap<>();
                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                String datestr=simpleDateFormat.format(date);

                map.put("userId",userId);
                map.put("message",message);
                map.put("date",datestr);
                //群体发送
                sendMesageToAll(gson.toJson(map));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //服务器的主动推送方法
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMesageToAll(String message) throws IOException {

        for (Map.Entry<String,WebSocketServer> entry:webSocketMap.entrySet()){
            entry.getValue().session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {

        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            System.out.println("用户"+userId+",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
