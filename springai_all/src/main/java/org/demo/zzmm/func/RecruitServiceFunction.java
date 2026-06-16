package org.demo.zzmm.func;

import java.util.function.Function;

/**
 * @ClassName RecruitServiceFunction
 * @Description
 * @Author zzmm
 * @Date 2026/6/16 10:57
 */
public class RecruitServiceFunction implements Function<RecruitServiceFunction.Request, RecruitServiceFunction.Response> {

    @Override
    public Response apply(Request request) {
        String position = "未知";
        if (request.name().equals("张三")){
            position = "算法工程师";
        }
        return new Response(position);
    }

    public record Request(String name){}

    public record Response(String message){}
}
