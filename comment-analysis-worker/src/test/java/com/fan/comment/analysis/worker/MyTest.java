package com.fan.comment.analysis.worker;

import com.fan.comment.analysis.worker.comment.converter.CommCommentConverter;
import org.junit.Test;

import java.io.File;

public class MyTest {

    String str= "\n\n/* 1\n" +
            "hello:adf\n" +
            "hello2:adf2\n" +
            "hello2:adf2\n" +
            "*/\n\n" ;
    @Test
    public void test01(){


        System.out.println(str);
        byte[] bytes = System.getProperty("line.separator").getBytes();
        for(byte b : bytes){
            System.out.println(b);
        }
    }

    @Test
    public void test02(){
        CommCommentConverter commCommentConverter = new CommCommentConverter();
        System.out.println(commCommentConverter.getCommentHead(str));
    }

    @Test
    public void test03(){
        CommCommentConverter commCommentConverter = new CommCommentConverter();
        System.out.println(commCommentConverter.getCommentBody(str));
    }

    @Test
    public void test04(){
        System.out.println(str);
        System.out.println(str.trim());
    }

    @Test
    public void test05(){
        String str = "M^M";
        for(String tmp: str.split("\\^")){
            System.out.println(tmp);
        }
        System.out.println(str);
        System.out.println(str.trim());
    }

    @Test
    public void test06(){
        String str = "long,  int";
        for(String tmp: str.split("\\^")){
            System.out.println(tmp);
        }
        System.out.println(str);
        System.out.println(str.trim());
    }

    @Test
    public void test07(){
        File file = new File("/tmp/fan.txt");
        File file02 = new File("/tmp/fan.txt");
        assert file.equals(file02);
    }

    @Test
    public void test08(){
        String str1=null;
        String str2=null;
        System.out.println(str1+str2);
    }
}
