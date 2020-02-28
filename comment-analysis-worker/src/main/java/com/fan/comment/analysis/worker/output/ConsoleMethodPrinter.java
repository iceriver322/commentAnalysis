package com.fan.comment.analysis.worker.output;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import com.fan.comment.analysis.worker.method.MethodHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleMethodPrinter implements CommentPrinter{

    private static final String step = "|     ";
    private static final String innerCommentstep = "+- ";
    private static final String lastCommentstep  = "\\- ";

    @Override
    public void print(MethodHolder methodHolder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = getOutPut(methodHolder, stringBuilder, "");
        System.out.println(stringBuilder.toString());
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
                int cardinal = commentHolder.getCardinal();
                List<MethodHolder> methodHolderList = commentHolder.getMethodHolderList();
                stringBuilder.append(initStep)
                        .append(commentstep)
                        .append(getCardinalFlag(cardinal))
                        .append(message)
                        .append("\n");
                if(methodHolderList!=null && methodHolderList.size()>0){
                    for(MethodHolder tmpMethod : methodHolderList){
                        getOutPut(tmpMethod, stringBuilder, initStep+step);
                    }
                }
            }
        }
        return stringBuilder;
    }
}
