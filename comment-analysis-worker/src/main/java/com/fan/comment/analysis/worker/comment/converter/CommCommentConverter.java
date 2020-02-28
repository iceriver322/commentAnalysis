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
                if(MESSAGE.equals(strArr[0].trim())){
                    commentHolder.setMessage(strArr[1].trim());
                }else if(METHOD.equals(strArr[0].trim())){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setMethodName(strArr[1].trim());
                }else if(PACKAGENAME.equals(strArr[0].trim())){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setPackageName(strArr[1].trim());
                }else if(CLASSNAME.equals(strArr[0].trim())){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setClassName(strArr[1].trim());
                }else if(ARGS.equals(strArr[0].trim())){
                    if(methodCommenHolder == null){
                        methodCommenHolder = new MethodCommenHolder();
                    }
                    methodCommenHolder.setArgs(strArr[1].trim());
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
