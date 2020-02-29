package com.fan.comment.demo01;

import com.fan.comment.demo01.dep.model.UserInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStartService {

    private static final Logger logger = LoggerFactory.getLogger(TestStartService.class);

    @Test
    public void test01(){
        StarterService starterService = new StarterService();
        UserInfo userInfo = starterService.getUser(100L);
        logger.info("{}", userInfo);
    }
}
