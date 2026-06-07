package org.demo.zzmm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * 定义两个方法：两数相加、两数相乘
 */
@Configuration
public class CalculatorService {
    /**
     * 记录(Record) 是一种特殊的类的类型。旨在简化不可变数据载体的创建，它自动生成构建函数、访问器方法、equals() hashcode() toString() 方法。 大幅减少样板代码。
     * 使用场景：
     * 1、DTO(数据传输对象)：代替大量样板代码的类
     * 2、临时的数据容器：如 方法返回多个值
     * 3、不可变配置对象:如 数据库配置对象
     * Record 使用注意事项：
     * 1、不可变性：Record中字段默认不可变，意味着不能修改他们的值，一旦创建，Record的状态就是固定的。
     * 2、不支持继承：Record 不支持继承，Record 不能继承其他类。只能实现接口。
     * 3、字段的访问权限：Record 的字段默认是 private final 的，不能修改。
     * 4、字段的初始化：Record 的字段必须通过构造函数进行初始化。
     * 5、字段的顺序：Record 的字段的顺序不能改变。
     * 6、字段的命名：Record 的字段的命名不能改变。
     * 7、不允许字段缺失：Record的所有字段必须在构建函数中提供值，不允许缺少字段，且不能有默认值。
     *
     */
    public record AddOperation(int a, int b) {

    }

    public record MulOperation(int m, int n) {

    }

    //将定义的函数注册到大模型中
    @Bean
    @Description("加法运算")
    public Function<AddOperation, Integer> addOperation() {
        System.out.println("=====addOperation===");
        return operation -> operation.a() + operation.b();
    }

    @Bean
    @Description("乘法运算")
    public Function<MulOperation, Integer> mulOperation() {
        System.out.println("=====mulOperation===");
        return request -> request.m * request.n;
    }
}
