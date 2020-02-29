package com.fan.comment.analysis.worker.comment.converter;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.util.List;

@Getter
@Setter
public class CommentConvertAdapter {

    private List<CommentConverter> commentConverterList;

    public CommentHolder convert(Comment comment, CompilationUnit compilationUnit, String javaSource) {
        CommentHolder commentHolder = null;
        for(CommentConverter commentConverter : commentConverterList){
            commentHolder = commentConverter.convert(comment, compilationUnit, javaSource);
            if(commentHolder!=null){
                break;
            }
        }
        return commentHolder;
    }
}