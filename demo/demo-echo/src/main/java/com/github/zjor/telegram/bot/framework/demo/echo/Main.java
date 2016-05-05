package com.github.zjor.telegram.bot.framework.demo.echo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath*:com/github/zjor/telegram/bot/framework/**/spring-context-*.xml"
        );

    }
}
