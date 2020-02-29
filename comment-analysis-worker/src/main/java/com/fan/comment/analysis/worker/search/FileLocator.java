package com.fan.comment.analysis.worker.search;

import com.fan.comment.analysis.worker.util.ASTCheckUtil;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component
public class FileLocator {

    private static final Logger logger = LoggerFactory.getLogger(FileLocator.class);

    @Resource
    private ASTCheckUtil astCheckUtil;

    @Value("${search.src.dir}")
    private String searchSrcDir;

    private volatile URLClassLoader urlClassLoader;

    @PostConstruct
    private void init(){
        logger.debug("searchSrcDir:{}", searchSrcDir);
        refreshClassLoader(searchSrcDir);
    }

    public void refreshClassLoader(String searchSrcDir){
        URL[] urlArr = getUrlArr(searchSrcDir);
        urlClassLoader = new URLClassLoader(urlArr);
    }

    private URL[] getUrlArr(String searchSrcDir){

        if(searchSrcDir == null){
            searchSrcDir = (String) System.getProperties().get("user.dir");
        }
        String[] strArr = searchSrcDir.split(",");
        List<URL> urlList = new ArrayList();

        for(int i=0; i< strArr.length; i++){
            String str = strArr[i];
            try {
                URL url = new File(str.trim()).toURI().toURL();
                urlList.add(url);
            } catch (MalformedURLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return urlList.toArray(new URL[0]);
    }


    public File locateClassFile(String packageName, String className, String methodName, String methodArgs, CompilationUnit compilationUnit, String thisClassName, File thisFile){

        if(methodName == null){
            logger.error("methodName is null");
            return null;
        }

        //本文件,当前class 搜索
        if(className == null){
            return thisFile;
        }else{
            //通过import搜索
            if(packageName == null){
                return importSearch(className, methodName, methodArgs, compilationUnit);
            }else{ //通过全路径搜索
                return packageSearch(packageName, className);
            }
        }
    }

    public File importSearch(String className, String methodName, String methodArgs, CompilationUnit compilationUnit){
        return null;
    }

    public File packageSearch(String packageName, String className){
        logger.trace("packageSearch:{}", packageName);
        logger.trace("className:{}", className);
        String packageDirName = packageName.replace(".", "/");
        try {
            Enumeration<URL> dirs = urlClassLoader.getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                File dir = new File(url.getFile());
                String[] fileNameList = dir.list();
                for(String fileName : fileNameList){
                    String requireFileName = className+".java";
                    if(requireFileName.equals(fileName)){
                        String fullName = dir.getAbsolutePath()+"/"+fileName;
                        logger.debug("packageSearch match:{}", fullName);
                        File returnFile =  new File(fullName);
                        return returnFile;
                    }
                }

            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public File fileSearch(String fileName, String className, String methodName, String methodArgs, CompilationUnit compilationUnit){
        return null;
    }
}
