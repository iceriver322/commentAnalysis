package com.fan.comment.analysis.worker.comment.converter;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import com.fan.comment.analysis.worker.comment.CommentType;
import com.fan.comment.analysis.worker.comment.MethodCommenHolder;
import com.fan.comment.analysis.worker.visitor.BlockCommentVisitor;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.springframework.beans.BeanUtils;

public class CommCommentConverter implements CommentConverter {

    private final String COMMENTHEAD = "/* ==========";
//    private final String COMMENTTAIL = "========== */";
    @Override
    public CommentHolder convert(Comment comment, CompilationUnit compilationUnit, String javaSource) {
        CommentHolder commentHolder = newCommentHolder();
        comment.accept(new BlockCommentVisitor(compilationUnit, javaSource, commentHolder));
        if(commentHolder.getRawMessage() == null){
            return null;
        }else{
            commentHolder = convert(commentHolder);
        }
        return commentHolder;
    }

    private CommentHolder convert(CommentHolder commentHolder){
        String rawMessage = commentHolder.getRawMessage();
        String commentHead = getCommentHead(rawMessage);
        if(!COMMENTHEAD.equals(commentHead)){
            return null;
        }

        MethodCommenHolder methodCommenHolder = null;

        String commentBody = getCommentBody(rawMessage);
        String[] commentBodyArr = commentBody.split("\\^");
        for(String str : commentBodyArr){
            String[] strArr = str.split(":");
            if(strArr.length==2){
                String key = strArr[0].trim();
                String value = strArr[1].trim();
                if(MESSAGE.equals(key)){
                    commentHolder.setMessage(value);
                }else if(DB.equals(key)){
                    commentHolder.setDb(value);
                }else if(OUTCALL.equals(key)){
                    commentHolder.setOutCall(value);
                } else if(METHOD.equals(key)){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setMethodName(value);
                }else if(PACKAGENAME.equals(key)){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setPackageName(value);
                }else if(CLASSNAME.equals(key)){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setClassName(value);
                }else if(ARGS.equals(key)){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setArgs(value);
                }

            }
        }

        if(methodCommenHolder!=null){
            BeanUtils.copyProperties(commentHolder, methodCommenHolder);
            methodCommenHolder.setCommentType(CommentType.METHOD_REF);
            return methodCommenHolder;
        }else{
            commentHolder.setCommentType(CommentType.TEXT);
        }

        if(commentHolder.getMessage() == null){
            commentHolder.setMessage(commentBody);
        }

        return commentHolder;
    }
}
