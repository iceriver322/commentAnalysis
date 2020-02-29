package com.fan.comment.analysis.worker.service;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import com.fan.comment.analysis.worker.comment.CommentType;
import com.fan.comment.analysis.worker.comment.MethodCommenHolder;
import com.fan.comment.analysis.worker.method.MethodHolder;
import com.fan.comment.analysis.worker.comment.converter.CommentConvertAdapter;
import com.fan.comment.analysis.worker.search.FileLocator;
import com.fan.comment.analysis.worker.search.FileWrapper;
import com.fan.comment.analysis.worker.util.ASTCheckUtil;
import com.fan.comment.analysis.worker.util.CycleDetectionUtil;
import com.fan.comment.analysis.worker.util.FileParser;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommentTraceService {

    private static final Logger logger = LoggerFactory.getLogger(CommentTraceService.class);

    private Map<File, FileWrapper> compilationUnitMap = new ConcurrentHashMap<>();

    @Resource
    private FileParser fileParser;

    @Resource
    private FileLocator fileLocator;

    @Resource
    private ASTCheckUtil astCheckUtil;

    @Resource
    private CycleDetectionUtil cycleDetectionUtil;

    @Resource
    private CommentConvertAdapter commentConvertAdapter;

    public MethodHolder getCommentHolderList(File file,String className, String methodName, String args, String message){

        if(file == null || className == null || methodName == null){
            return null;
        }

        MethodHolder methodHolder = null;
        String javaSource = null;
        CompilationUnit compilationUnit = null;

        if(args!=null){
            args = args.replaceAll("\\s", "");
        }
        String[] argsArr = astCheckUtil.getArgArr(args);

        String cycleDetectionKey = file.toString()+"_"+className+"_"+methodName+"_"+args;
        if(cycleDetectionUtil.isCycle(cycleDetectionKey)){
            return newMethodHolder(null, className, methodName, argsArr, "循环节点，参见之上流程");
        }

        FileWrapper fileWrapper = compilationUnitMap.get(file);
        if(fileWrapper == null){
            try {
                javaSource = fileParser.fileToText(file);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return methodHolder;
            }
            compilationUnit = fileParser.parse(javaSource);
            compilationUnitMap.put(file, new FileWrapper(javaSource, compilationUnit));
        }else{
            javaSource = fileWrapper.getJavaSource();
            compilationUnit = fileWrapper.getCompilationUnit();
        }

        for(TypeDeclaration type : (List<TypeDeclaration>)compilationUnit.types()){
            if(className.equals(type.getName().getIdentifier())){
                logger.trace("class found:{}", className);
                int commentCardinal = 1;
                for(MethodDeclaration methodDeclaration : type.getMethods()){
                    if(methodName.equals(methodDeclaration.getName().getIdentifier()) && astCheckUtil.argsCheck(argsArr, methodDeclaration.parameters())){
                        logger.trace("method found:{}", methodName);
                        int methodStart = methodDeclaration.getStartPosition();
                        int methodEnd = methodStart + methodDeclaration.getLength();
                        List<Comment> commentList = compilationUnit.getCommentList();
                        String packageName = compilationUnit.getPackage().getName().getFullyQualifiedName();
                        methodHolder = newMethodHolder(packageName, className,methodName, argsArr, message);
                        CommentHolder lastCommentHolder = null;
                        for(Comment comment : commentList){
                            int commentStart = comment.getStartPosition();
                            if(commentStart>methodStart && commentStart<methodEnd){
                                CommentHolder commentHolder = commentConvertAdapter.convert(comment, compilationUnit, javaSource);
                                if(commentHolder!=null){
                                    if(commentHolder.getCommentType()!=null && commentHolder.getCommentType().equals(CommentType.METHOD_REF)){
                                        MethodCommenHolder methodCommenHolder = (MethodCommenHolder)commentHolder;
                                        String methodPackageName = methodCommenHolder.getPackageName();
                                        String methodClassName = methodCommenHolder.getClassName() ;
                                        String methodMethodName = methodCommenHolder.getMethodName();
                                        String methodArgs = methodCommenHolder.getArgs();
                                        String methodMessage = methodCommenHolder.getMessage();
                                        File methodFile = fileLocator.locateClassFile(methodPackageName, methodClassName, methodMethodName, methodArgs, compilationUnit, className, file);
                                        if(methodClassName == null){
                                            methodClassName = className;
                                            methodCommenHolder.setClassName(className);
                                            logger.trace("change local className:{}", methodClassName);
                                        }
                                        MethodHolder childMethodHolder = getCommentHolderList(methodFile, methodClassName, methodMethodName, methodArgs, methodMessage);
                                        if(childMethodHolder != null){
                                            if(lastCommentHolder == null){
                                                lastCommentHolder = new CommentHolder();
                                                lastCommentHolder.setMessage("第一条注释为系统调用类型");
                                                lastCommentHolder.setCardinal(commentCardinal++);
                                                lastCommentHolder.setCommentType(CommentType.TEXT);
                                                List<CommentHolder> commentHolderList = methodHolder.getCommentHolderList();
                                                commentHolderList.add(lastCommentHolder);
                                            }
                                            List methodHolderList = lastCommentHolder.getMethodHolderList();
                                            methodHolderList.add(childMethodHolder);
                                        }else{
                                            logger.warn("未找到methodCommenHolder对应的解析:{}, file:{}，转换为普通日志", methodCommenHolder, methodFile);
                                            if(methodMessage != null){
                                                methodCommenHolder.setCommentType(CommentType.TEXT);
                                                methodCommenHolder.setCardinal(commentCardinal++);
                                                List<CommentHolder> commentHolderList = methodHolder.getCommentHolderList();
                                                commentHolderList.add(methodCommenHolder);
                                                lastCommentHolder = methodCommenHolder;
                                            }
                                        }
                                    }else{
                                        commentHolder.setCardinal(commentCardinal++);
                                        List<CommentHolder> commentHolderList = methodHolder.getCommentHolderList();
                                        commentHolderList.add(commentHolder);
                                        lastCommentHolder = commentHolder;
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return methodHolder;
    }

    private MethodHolder newMethodHolder(String packageName, String className, String methodName, String[] methodArgs, String message){
        MethodHolder methodHolder = new MethodHolder();
        methodHolder.setPackageName(packageName);
        methodHolder.setClassName(className);
        methodHolder.setMethodName(methodName);
        methodHolder.setMethodArgs(methodArgs);
        methodHolder.setMessage(message);
        return methodHolder;
    }
}
