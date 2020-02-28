package com.fan.comment.analysis.worker.search;

import com.fan.comment.analysis.worker.util.ASTCheckUtil;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Component
public class FileLocater {

    private static final Logger logger = LoggerFactory.getLogger(FileLocater.class);

    @Resource
    private ASTCheckUtil astCheckUtil;

    public File locateClassFile(String pacakgeName, String className, String methodName, String methodArgs, CompilationUnit compilationUnit, String thisClassName, File thisFile){

        if(methodName == null){
            logger.error("methodName is null");
            return null;
        }

        //本文件搜索
        if(className == null){
            return localSearch(thisClassName, methodName, methodArgs, compilationUnit, thisFile);
        }else{
            //通过import搜索
            if(pacakgeName == null){
                return importSearch(className, methodName, methodArgs, compilationUnit);
            }else{ //通过全路径搜索
                return pathSearch(pacakgeName, className, methodName, methodArgs, compilationUnit);
            }
        }
    }

    private File localSearch(String thisClassName, String methodName, String methodArgs, CompilationUnit compilationUnit, File thisFile){
        for(TypeDeclaration type : (List<TypeDeclaration>)compilationUnit.types()){
            if(thisClassName.equals(type.getName().getIdentifier())){
                logger.debug("class found:{}", thisClassName);
                for(MethodDeclaration methodDeclaration : type.getMethods()) {
                    if (methodName.equals(methodDeclaration.getName().getIdentifier()) && astCheckUtil.argsCheck(methodArgs, methodDeclaration.parameters())) {
                        return thisFile;
                    }
                }
                break;
            }
        }
        return null;
    }

    private File importSearch(String className, String methodName, String methodArgs, CompilationUnit compilationUnit){
        return null;
    }

    private File pathSearch(String pacakgeName, String className, String methodName, String methodArgs, CompilationUnit compilationUnit){
        return null;
    }
}
