package org.demo.zzmm.config;

import org.demo.zzmm.func.RecruitServiceFunction;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

/**
 * @ClassName RagConfig
 * @Description
 * @Author zzmm
 * @Date 2026/6/15 11:40
 */
@Configuration
public class RagConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel){
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        String filePath = "张三简历.txt";
        //TextReader 是Spring AI提供的一个文本读取器,用于从ResourceLoader中读取指定路径的文件的纯文本内容。
        //主要功能：打开来文件，读取文本字符串.TextReader将整个文件内容读入内存，因此它可能不适合非常大的文件。
        TextReader textReader = new TextReader(filePath);
        textReader.getCustomMetadata().put("filePath",filePath); //textReader.getCustomMetadata() 返回的是一个Map对象，用于存储自定义的元数据信息。
        //TextReader 将纯文本文件转换为 Document 列表。
        List<Document> documents = textReader.get();
        //TokenTextSplitter文本分割器，将文本分割成更小的块。
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(
                1200,
                350,
                5,
                10,
                true
        );
        tokenTextSplitter.apply(documents);
        //向量化存储
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }

    @Bean
    @Description("函数：xx是否有资格进行面试")
    public Function<RecruitServiceFunction.Request, RecruitServiceFunction.Response> recruitServiceFunction(){
        return new RecruitServiceFunction();
    }


}
