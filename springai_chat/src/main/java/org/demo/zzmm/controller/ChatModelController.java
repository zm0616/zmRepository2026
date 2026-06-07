package org.demo.zzmm.controller;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ChatModel接口作为核心，定义了与AI模型交互的基本方法。它继承自Model<Prompt, ChatResponse>，提供了两个重载的call方法：
 * 在ChatModel接口中，带有String参数的call()方法简化了实际的使用，避免了更复杂的Prompt和 ChatResponse类的复杂性。
 * 但是在实际应用程序中，更常见的是使用ChatResponse call()方法，该方法采用Prompt实例并返回ChatResponse。
 * 我们使用的ChatClient底层是使用ChatModel作为属性的，在初始化ChatClient的时候可以指定ChatModel。
 */
@RestController
@RequestMapping("/chat/chatModel")
public class ChatModelController {

    @Autowired
    private ChatModel chatModel;
    @GetMapping("/test")
    public void test(@RequestParam(value = "massage",defaultValue = "hello") String massage){
        System.out.println("11111");
    }
    @GetMapping("/chat01")
    public String chat(@RequestParam(value = "massage",defaultValue = "hello") String massage){
        System.out.println("11111");
        String content = chatModel.call(massage);
        return content;
    }

    @GetMapping("/openAI")
    public String openAI(@RequestParam(value = "massage",defaultValue = "hello") String massage){
        ChatResponse call = chatModel.call(new Prompt(
                massage,
                OpenAiChatOptions.builder()
                        .model("deepseek-chat")    //指定大模型，此处用的此项目配置的deepseek大模型
                        .temperature(0.8)
                        .build()
        ));
        String content = call.getResult().getOutput().getContent();
        return content;
    }

    /**
     * 提示词：Prompt
     * 提示词是引导大模型生成特定输出的输入，提示词的设计和措辞极大的影响模型的响应结果。
     * prompt提示词是与大模型交互的一张输入数据的组织方式，本质是一种符合结构的输入。
     * 在prompt我们可以组装多组不用角色（system user Aissistant）的信息。
     * spring AI 提供了prompt template 提示词模板管理抽象，开发者可以预先定义好模板，并在运行时替换模板中的关键词。
     * 在spring AI 与大模型交互的过程中，处理关键词首先要创建好包含动态内容的占位符{占位符}的模板，然后占位符会根据用户请求
     * 或者应用程序的其他代码进行替换。在提示词模板中{占位符}可以用Map中的变量动态替换。
     */

    @GetMapping("/prompt")
    public String prompt(@RequestParam(value = "name") String name,@RequestParam(value = "voice") String voice){
        String userText = """
                给我推荐北京至少三种美食
                """;
        UserMessage userMessage = new UserMessage(userText);
        String systemText = """
                你是一个美食咨询助手，可以帮助人们查询美食信息。
                你的名字是{name},
                你应该用你的名字和{voice}的饮食习惯回复用户的请求。
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText); //系统提示词模板
        //替换系统提示词模板占位符{name} {voice}
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
        //使用Prompt进行封装
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        //调用chatmodel方法
        ChatResponse call = chatModel.call(prompt);
        List<Generation> results = call.getResults();
        String collect = results.stream().map(x -> x.getOutput().getContent()).collect(Collectors.joining(""));
        System.out.println("=====>prompt:"+collect);
        return collect;
    }













}
