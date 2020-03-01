package com.fan.comment.analysis.worker;

import com.fan.comment.analysis.worker.util.SpringInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppStarter {

    private static final Logger logger = LoggerFactory.getLogger(AppStarter.class);

    public static void main(String[] args){
        logger.info("Analysis Starter starting");
        logger.info("user.dir:{}", System.getProperties().get("user.dir"));
        SpringInitializer.getApplicaitonContext();
    }
}
