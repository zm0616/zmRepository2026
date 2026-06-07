package org.demo.zzmm.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/function")
public class FunctionTestController {

    @Autowired
    private ChatModel chatModel;

    /**
     * * Spring AI 使自定义函数这个过程变得简单，只需定义一个返回 java.util.Function 的 @Bean 定义，
     *   并在调用 ChatModel 时将 bean 名称作为选项进行注册即可。在底层，Spring 会用适当的适配器代码包装你的函数，以便与 AI 模型进行交互，免去了编写繁琐的代码。
     */
    @GetMapping(value = "/test",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public String functionTest(@RequestParam("userMessage") String userMessage){
        return ChatClient.builder(chatModel).build().prompt()
                .system("""
                您是算术计算器的代理。
                您能够支持加法运算、乘法运算等操作，其余功能将在后续版本中添加，如果用户问的问题不支持请告知详情。
                在提供加法运算、乘法运算等操作之前，您必须从用户处获取如下信息：两个数字，运算类型。
                请调用自定义函数执行加法运算、乘法运算。
                请讲中文。
                """)
                .user(userMessage)
                .functions("addOperation", "mulOperation")
                .call().content();
    }




}
