package com.fan.comment.analysis.worker.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jdt.core.dom.CompilationUnit;

@Getter
@Setter
@AllArgsConstructor
public class FileWrapper {
    private String javaSource;
    private CompilationUnit compilationUnit;
}
