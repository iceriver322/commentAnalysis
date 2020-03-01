package com.fan.comment.analysis.worker.output;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import com.fan.comment.analysis.worker.method.MethodHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleCommentPrinter implements CommentPrinter{

    private static final Logger logger = LoggerFactory.getLogger(ConsoleCommentPrinter.class);
    private static final String step = "|     ";
    private static final String innerCommentstep = "+- ";
    private static final String lastCommentstep  = "\\- ";

    @Value("${firstLaneWidth:100}")
    private  int firstLaneWidth;

    @Override
    public StringBuilder print(MethodHolder methodHolder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = getOutPut(methodHolder, stringBuilder, "");
        logger.info("result:\n{}", stringBuilder.toString());
        return stringBuilder;
    }

    private StringBuilder getOutPut(MethodHolder methodHolder, StringBuilder stringBuilder, String initStep){
        if(methodHolder == null){
            return stringBuilder;
        }
        String packageName = methodHolder.getPackageName();
        String className = methodHolder.getClassName();
        String methodName = methodHolder.getMethodName();
        String args = getArgs(methodHolder.getMethodArgs());
        String methodMessage = methodHolder.getMessage();
        stringBuilder.append(initStep).append(className)
                .append(".")
                .append(methodName)
                .append(args)
                .append(" - ")
//                .append(packageName)
                .append(methodMessage)
                .append("\n");
        List<CommentHolder> commentHolderList = methodHolder.getCommentHolderList();
        if(commentHolderList!=null && commentHolderList.size()>0){
            int length = commentHolderList.size();
            for(int i=0; i< length; i++){
                String commentstep = innerCommentstep;
                if(i == length -1){
                    commentstep = lastCommentstep;
                }
                CommentHolder commentHolder = commentHolderList.get(i);
                String message = commentHolder.getMessage();
                String db = commentHolder.getDb();
                String outCall = commentHolder.getOutCall();
                int cardinal = commentHolder.getCardinal();
                List<MethodHolder> methodHolderList = commentHolder.getMethodHolderList();

                StringBuilder lineSb = new StringBuilder();
                lineSb.append(initStep)
                        .append(commentstep)
                        .append(getCardinalFlag(cardinal))
                        .append(message);

                if(outCall != null){
                    addInfo(lineSb, outCall);
                }else if(db != null){
                    addInfo(lineSb, db);
                }
                lineSb.append("\n");
                stringBuilder.append(lineSb);
                if(methodHolderList!=null && methodHolderList.size()>0){
                    for(MethodHolder tmpMethod : methodHolderList){
                        getOutPut(tmpMethod, stringBuilder, initStep+step);
                    }
                }
            }
        }
        return stringBuilder;
    }

    private StringBuilder addInfo(StringBuilder stringBuilder, String info){
        int length = stringBuilder.length();
        if(length < firstLaneWidth){
            for(int i=length; i< firstLaneWidth; i++){
                stringBuilder.append("-");
            }
        }else{
            stringBuilder.append("-----");
        }
        logger.trace("stringBuilder length {}", stringBuilder.length());
        info = info.replaceAll("\\s", "");
        stringBuilder.append("|--->").append(info);
        return stringBuilder;
    }
}
