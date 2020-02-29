package com.fan.comment.analysis.worker.util;


import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CycleDetectionUtil {

    private ThreadLocal<Set<String>> threadLocal = new ThreadLocal<>();

    public boolean isCycle(String key){
        Set<String> stack = threadLocal.get();
        if(stack == null){
            stack = new HashSet<>();
            threadLocal.set(stack);
        }
        boolean notExist = stack.add(key);
        return !notExist;
    }

}
