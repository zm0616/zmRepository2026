package org.demo.zzmm.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RagController
 * @Description  在RAG工作时，其运行流程大致为：
 * 1. 用户输入问题
 * 2. 问题向量化
 * 3. 向量数据库检索
 * 4. 构建上下文（含系统提示词）
 * 5. 携带检索内容，调用大模型进行回答
 * 6. 返回最终答案给用户s
 * @Author zzmm
 * @Date 2026/6/15 10:10
 */
@RestController
@RequestMapping("/rag")
public class RagController {

    @Autowired
    private ChatClient dashScopeChatClient;
    @Autowired
    private VectorStore vectorStore;


    @GetMapping(value = "/generateAnswer",produces = "text/plain; charset=UTF-8")
    public String generateAnswer(@RequestParam(value = "question",defaultValue = "") String question){
        /*//1. 用户输入问题
        //2. 问题向量化
        //3. 向量数据库检索
        List<Document> documents = vectorStore.similaritySearch(question, 3);
        //4. 构建上下文（含系统提示词）
        String context = documents.stream().map(Document::getContent).collect(Collectors.joining("\n"));
        //5. 携带检索内容，调用大模型进行回答
        String answer = dashScopeChatClient.call(context + "\n" + question);*/

        String content = dashScopeChatClient.prompt()
                .user(question)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
        return content;
    }


























}
