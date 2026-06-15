package org.demo.zzmm.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName RagConfig
 * @Description RAG 配置类
 * @Author zzmm
 * @Date 2026/6/15 10:12
 */
@Configuration
public class RagConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder.defaultSystem("你作为一个java开发语言的专家，对于用户的使用需求做出专业的合理的科学的解答").build(); //设置系统提示信息
    }

    /**
     * VectorStore 向量数据库(向量存储)
     * EmbeddingModel 嵌入模型:
     */
    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel){
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        //生成一个java的说明文档
        List<Document> documents = List.of(new Document("产品说明:名称：Java开发语言\n" +
                "产品描述：Java是一种面向对象开发语言。\n" +
                "特性：\n" +
                "1. 封装\n" +
                "2. 继承\n" +
                "3. 多态\n"),
                new Document("Python 核心特性：\n" +
                        "1. 语法简洁易读；\n" +
                        "2. 解释型语言，无需编译；\n" +
                        "3. 动态弱类型；\n" +
                        "4. 完全面向对象；\n" +
                        "5. 丰富标准库 + 海量第三方库；\n" +
                        "6. 可扩展性、可嵌入。。。。"));
        //将文本批量存储内存限量存储，会自动调用EmbeddingModel将文本转为向量病存储。
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }


























}
