package com.fan.comment.analysis.worker.comment;

import com.fan.comment.analysis.worker.method.MethodHolder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
public class CommentHolder {
    private int cardinal;
    private CommentType commentType;
    private String message;
    private String rawMessage;
    private List<MethodHolder> methodHolderList;

    public List<MethodHolder> getMethodHolderList() {
        if(methodHolderList == null){
            methodHolderList = new ArrayList<>();
        }
        return methodHolderList;
    }
}

