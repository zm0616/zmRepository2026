package org.demo.zzmm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeepSeekController
 * @Description
 * @Author zzmm
 * @Date 2026/5/13 15:04
 */
@RestController
public class DeepSeekController {

    @GetMapping("/test")
    public void test(){
        System.out.println("===== 测试测试 =======");
    }




}
