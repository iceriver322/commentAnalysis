package com.fan.comment.analysis.worker;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import com.fan.comment.analysis.worker.visitor.BlockCommentVisitor;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws IOException {
        logger.info("Hello Analysis Starter");
        logger.info("user.dir:{}", System.getProperties().get("user.dir"));

        String javaSource = FileUtils.readFileToString(new File("comment-demo01/src/main/java/com/fan/comment/demo01/StarterService.java"));
        logger.trace("file-text:\n{}", javaSource);


        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(javaSource.toCharArray());
        parser.setBindingsRecovery(true);
        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit result = (CompilationUnit) parser.createAST(null);

        for (Comment comment : (List<Comment>) result.getCommentList()) {
            comment.accept(new BlockCommentVisitor(result, javaSource, new CommentHolder()));
        }


        TypeDeclaration typeTmp = (TypeDeclaration) result.types().get(0);
        FieldDeclaration[] f = typeTmp.getFields();
        for (int j = 0; j < f.length; j++) {
            try {
                logger.info("f[j].getJavadoc().toString():{}",f[j].getJavadoc().toString());//获取每一个属性的注释
                logger.info("f[j].fragments().get(0).toString():{}",f[j].fragments().get(0).toString());//获取属性字段
                //还需要获取属性字段名称.

            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }

        logger.info("Exiting Analysis Starter");
        List<ImportDeclaration> list = result.imports();
        for(ImportDeclaration importDeclaration : list){
            System.out.println(importDeclaration);
        }
        logger.info("import:{}", list);

        result.imports();
        result.getPackage();
        result.getCommentList();

        System.out.println(result.getCommentList().toString());

        TypeDeclaration type = (TypeDeclaration) result.types().get(0);
        System.out.println("---------Type---------");
        System.out.println(type.toString());

        MethodDeclaration method = type.getMethods()[0];
        method.parameters();
        method.isConstructor();

        System.out.println("---------Method---------");
        System.out.println(method.toString());
        method.getName();
        method.getModifiers();
        Type returnType = method.getReturnType2();
        System.out.println("returnType = " + returnType.toString());

        Block methodBody = method.getBody();
        List<Statement> statementList = methodBody.statements();

        System.out.println(statementList.toString());

        statementList.get(0);

        ExpressionStatement ifs = (ExpressionStatement) method.getBody().statements().get(1);
        Assignment expression = (Assignment) ifs.getExpression();
        Expression exp = expression.getRightHandSide();

        System.out.println(result.toString());
    }
}
