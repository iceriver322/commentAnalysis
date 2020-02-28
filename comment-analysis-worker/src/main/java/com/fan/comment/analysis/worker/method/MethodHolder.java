package com.fan.comment.analysis.worker.method;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MethodHolder {
    private String packageName;
    private String className;
    private String methodName;
    private String[] methodArgs;
    private String message;
    private List<CommentHolder> commentHolderList;

    public List<CommentHolder> getCommentHolderList() {
        if(commentHolderList == null){
            commentHolderList = new ArrayList<>();
        }
        return commentHolderList;
    }
}
