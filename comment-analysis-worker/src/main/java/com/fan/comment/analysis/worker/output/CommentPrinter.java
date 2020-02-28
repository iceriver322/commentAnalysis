package com.fan.comment.analysis.worker.output;

import com.fan.comment.analysis.worker.method.MethodHolder;

public interface CommentPrinter {
    void print(MethodHolder methodHolder);

    default String getArgs(String[] methodArgs){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        if(methodArgs == null){
            stringBuilder.append(" ");
        }else{
            for(String para : methodArgs){
                stringBuilder.append(para).append(", ");
            }
            stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    default String getCardinalFlag(int cardinal){
        if(cardinal<10){
            return "0"+cardinal+".";
        }else{
            return cardinal+".";
        }
    }
}
