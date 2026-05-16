package org.demo.zzmm.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeepSeekController
 * @Description
 * @Author zzmm
 * @Date 2026/5/13 15:04
 */
@RestController
public class DeepSeekController {

    @Autowired
    private OpenAiChatModel chatModel;
    @GetMapping("/test")
    public void test(){
        System.out.println("===== 测试测试 =======");
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "message",defaultValue = "hello") String message){
        String result = chatModel.call(message);
        System.out.println(result);
        return result;

    }


}
