package com.fan.comment.analysis.worker.visitor;

import com.fan.comment.analysis.worker.comment.CommentHolder;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockCommentVisitor extends ASTVisitor {

    private static final Logger logger = LoggerFactory.getLogger(BlockCommentVisitor.class);

    private CompilationUnit cu;
    private String source;
    private CommentHolder commentHolder;


    public BlockCommentVisitor(CompilationUnit cu, String source, CommentHolder commentHolder) {
        super();
        this.cu = cu;
        this.source = source;
        this.commentHolder = commentHolder;
    }

    public boolean visit(BlockComment node) {
        int start = node.getStartPosition();
        int end = start + node.getLength();
        String comment = source.substring(start, end);
        logger.trace("comment:{}", comment);
        commentHolder.setRawMessage(comment);
        return true;
    }
}
