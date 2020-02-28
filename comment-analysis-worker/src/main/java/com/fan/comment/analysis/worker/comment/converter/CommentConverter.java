package com.fan.comment.analysis.worker.comment.converter;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;

public interface CommentConverter {

    final String MESSAGE="message";
    final String PACKAGENAME="packageName";
    final String CLASSNAME="className";
    final String METHOD="method";
    final String ARGS="args";

    CommentHolder convert(Comment comment, CompilationUnit compilationUnit, String javaSource);

    default CommentHolder newCommentHolder(){
        return new CommentHolder();
    }

    default String getCommentHead(String rawMessage){
        String[] stringArr = rawMessage.split("\n", 2);
        return stringArr[0].trim();
    }

    default String getCommentBody(String rawMessage){
        String[] stringArr = rawMessage.split("\n", 2);
        if(stringArr.length>1){
            String tmpBody = stringArr[1];
            int last = tmpBody.lastIndexOf("\n");
            String body =  tmpBody.substring(0, last);
            return body.trim();
        }
        return null;
    }

}
