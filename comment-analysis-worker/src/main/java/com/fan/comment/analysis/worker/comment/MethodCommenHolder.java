package com.fan.comment.analysis.worker.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MethodCommenHolder extends CommentHolder{
    private String packageName;
    private String className;
    private String methodName;
    private String args;

}
