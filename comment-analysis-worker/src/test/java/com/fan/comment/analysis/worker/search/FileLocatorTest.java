package com.fan.comment.analysis.worker.search;

import com.fan.comment.analysis.worker.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class)
public class FileLocatorTest {

    @Resource
    private FileLocator fileLocator;

    @Test
    public void test01(){

        fileLocator.packageSearch("com.fan.comment.demo01.dep.dao", "UserInfoDao" );
    }

}
