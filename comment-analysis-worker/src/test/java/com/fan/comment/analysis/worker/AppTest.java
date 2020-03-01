package com.fan.comment.analysis.worker;

import com.fan.comment.analysis.worker.method.MethodHolder;
import com.fan.comment.analysis.worker.output.CommentPrinter;
import com.fan.comment.analysis.worker.output.ConsoleCommentPrinter;
import com.fan.comment.analysis.worker.service.CommentTraceService;
import com.fan.comment.analysis.worker.util.SpringInitializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AppTest {

    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void test01(){
        logger.info("Hello Analysis Starter");
        logger.info("user.dir:{}", System.getProperties().get("user.dir"));

        String fileName = "../comment-demo01/src/main/java/com/fan/comment/demo01/StarterService.java";
        String className = "StarterService";
//        String methodName = "checkUser";
//        String methodArgs = " Long,   String  ";

        String methodName = "getDate";
        String methodArgs = "";

        File file = new File(fileName);
        logger.trace("fileName:\n{}", fileName);

        CommentTraceService commentTraceService = SpringInitializer.getBean(CommentTraceService.class);
        CommentPrinter commentPrinter = SpringInitializer.getBean("swingCommentPrinter", ConsoleCommentPrinter.class);
        MethodHolder methodHolder = commentTraceService.getCommentHolderList(file, className, methodName, methodArgs, "代码入口");
//        if(methodHolder!=null){
//            logger.info(methodHolder.toString());
//        }
        commentPrinter.print(methodHolder);
        logger.info("Exit Analysis Starter");
    }
}
