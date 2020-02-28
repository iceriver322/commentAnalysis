package com.fan.comment.analysis.worker.util;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ASTCheckUtil {

    private static final Logger logger = LoggerFactory.getLogger(ASTCheckUtil.class);

    public boolean argsCheck(String[] argsArr, List<SingleVariableDeclaration> paraList){
        int argsLength = 0;
        if(argsArr!=null){
            argsLength = argsArr.length;
        }
        if(argsLength != paraList.size()){
            return false;
        }
        for(int i=0; i<paraList.size(); i++){
            String paraType = paraList.get(i).getType().toString();
            String inputType = argsArr[i];
            logger.trace("para check:{}, {}", argsArr[i], paraType);
            if(!paraType.equals(inputType)){
                return false;
            }
        }
        return true;
    }

    public boolean argsCheck(String args, List<SingleVariableDeclaration> paraList){
        String[] argsArr = getArgArr(args);
        return argsCheck(argsArr, paraList);
    }

    public String[] getArgArr(String args){
        String[] argsArr = null;
        if(args!=null){
            argsArr = args.split(",");
            for(int i=0; i<argsArr.length; i++){
                argsArr[i] = argsArr[i].trim();
            }
        }
        return argsArr;
    }
}
