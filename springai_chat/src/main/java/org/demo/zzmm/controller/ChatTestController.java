package org.demo.zzmm.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ChatTestController
 * @Description
 * @Author zzmm
 * @Date 2026/5/13 17:43
 */
@RestController
@RequestMapping("/chat/test")
public class ChatTestController {

    @Autowired
    private ChatClient chatClient;


   @GetMapping("/hello")
    public String chat(@RequestParam(value = "massage",defaultValue = "hello") String massage){
       String content = chatClient.prompt().  //提示词
               user(massage)  //用户输入的聊天信息
               .call()   //请求大模型
               .content();  //返回文本
       return content;
   }


}
