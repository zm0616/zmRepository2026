package org.demo.zzmm.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;

/**
 * @ClassName SpringAiOtherController
 * @Description Spring AI的其他模型 : 图像模型、语音模型
 * @Author zzmm
 * @Date 2026/6/12 16:10
 */
@RestController
@RequestMapping("/alibaba/other")
public class SpringAiOtherController {
    @Autowired
    private DashScopeImageModel imageModel;

    @Autowired
    private DashScopeSpeechSynthesisModel speechSynthesisModel;

    private static final String TEXT = "床前明月光， 疑是地上霜。 举头望明月， 低头思故乡。";
    private static final String FILE_PATH = "E:/zm-workspace/zmRepository2026/springai_other/src/main/resources/tts";

    /**
     *  图像模型
     *  在Spring AI框架中，Image API提供了基于DashScope的图像生成模型，该模型允许开发者生成各种类型的图像，如生成图片、生成图片和文字、生成图片和音频等。
     *  DashScopeImageModel 类是Spring AI Alibaba框架中用于表示和管理图像生成模型核心组件之一。
     *  DashScopeImageOptions 类通常用于配置图像生成服务的选项，这个类允许开发者指定一系列参数（比如：模型、N、H、W、S、T)
     */
    @GetMapping("/image")
    public void getImage(@RequestParam(value = "msg",defaultValue = "生成一只小猫") String msg, HttpServletResponse response){
        ImageResponse imageResponse = imageModel.call(new ImagePrompt(msg,
                DashScopeImageOptions.builder()
                        .withModel(DashScopeImageApi.DEFAULT_IMAGE_MODEL)
                        .withN(1)
                        .withHeight(1024)
                        .withWidth(1024).build())

        );
        //获取输出流在浏览器输出
        String imageurl = imageResponse.getResult().getOutput().getUrl();
        try {
            URL url = URI.create(imageurl).toURL();
            InputStream in = url.openStream();
            response.setHeader("Content-type", MediaType.IMAGE_PNG_VALUE);
            response.getOutputStream().write(in.readAllBytes());
            response.getOutputStream().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 语音模型
     * 在Spring AI框架中，Text-to-Speech API提供了一个基于OpenAI的TTS（文本转语音）模型的语音端点.
     * SpeechSynthesisModel 类是Spring AI Alibaba框架中用于表示和管理文本转语音模型的核心组件之一。
     * DashScopeSpeechSynthesisOptions 类通常用于配置文本转语音（TTS）服务的选项，这个类允许开发者指定一系列参数（比如：语速、音调、音量等）来定制化语音合成的结果，从而满足不同的应用场景需求。
     */
    @GetMapping("/tts")
    public void tts(){
        DashScopeSpeechSynthesisOptions options = DashScopeSpeechSynthesisOptions.builder()
                .withSpeed(1.0) //设置语速
                .withPitch(0.9) //设置音调
                .withVolume(60) //设置音量
                .build();
        SpeechSynthesisPrompt prompt = new SpeechSynthesisPrompt(TEXT, options);
        SpeechSynthesisResponse response = speechSynthesisModel.call(prompt);
        //生成一个mp3文件，用于存储生成的文本转语音的内容
        File file = new File(FILE_PATH+"/output.mp3");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ByteBuffer byteBuffer = response.getResult().getOutput().getAudio();
            fos.write(byteBuffer.array());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
