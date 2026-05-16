package org.demo.zzmm.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ChatApiConfig
 * @Description 配置类，配置ChatClient
 * @Author zzmm
 * @Date 2026/5/13 18:03
 */
@Configuration
public class ChatApiConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder.defaultSystem("你是聪明的人工智能小玩家，晓明").build();
    }
}
