package org.demo.zzmm.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

    /**
     * 1 call和stream的区别
     *
     * （1）非流式输出 call：等待大模型把回答结果全部生成后输出给用户；
     * （2）流式输出stream：逐个字符输出，一方面符合大模型生成方式的本质，另一方面当模型推理效率不是很高时，流式输出比起全部生成后再输出大大提高用户体验。
     */

    //非流式输出
   @GetMapping("/hello")
    public String chat(@RequestParam(value = "massage",defaultValue = "hello") String massage){
       String content = chatClient.prompt().  //提示词
               user(massage)  //用户输入的聊天信息
               .call()   //请求大模型
               .content();  //返回文本

       System.out.println("=====>"+ content);
       return content;
   }


    //流式输出
    @GetMapping(value = "/stream",produces = "text/html;charset=UTF-8")
    public Flux<String> chatStream(@RequestParam(value = "massage",defaultValue = "hello") String massage){
        Flux<String> content = chatClient.prompt().  //提示词
                user(massage)  //用户输入的聊天信息
                .stream()   //请求大模型
                .content();//返回文本

       return content;
    }
}
