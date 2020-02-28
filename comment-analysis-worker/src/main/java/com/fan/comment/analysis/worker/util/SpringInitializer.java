package com.fan.comment.analysis.worker.util;

import com.fan.comment.analysis.worker.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringInitializer {

    private static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

    public static ApplicationContext getApplicaitonContext(){
        return applicationContext;
    }

    public static <T> T getBean(String name, Class<T> tClass){
        return applicationContext.getBean(name, tClass);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);

    }
}
