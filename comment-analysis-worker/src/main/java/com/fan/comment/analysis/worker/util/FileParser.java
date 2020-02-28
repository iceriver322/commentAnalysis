package com.fan.comment.analysis.worker.util;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FileParser {
    public CompilationUnit parse(File file) throws IOException {
        String javaSource = FileUtils.readFileToString(file);
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(javaSource.toCharArray());
//        parser.setBindingsRecovery(true);
//        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
        return compilationUnit;
    }

    public CompilationUnit parse(String javaSource) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(javaSource.toCharArray());
//        parser.setBindingsRecovery(true);
//        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
        return compilationUnit;
    }

    public String fileToText(File file)  throws IOException{
        return  FileUtils.readFileToString(file);
    }
}
