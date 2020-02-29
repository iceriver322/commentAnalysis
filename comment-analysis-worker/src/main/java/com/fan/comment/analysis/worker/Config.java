package com.fan.comment.analysis.worker;

import com.fan.comment.analysis.worker.comment.converter.CommCommentConverter;
import com.fan.comment.analysis.worker.comment.converter.CommentConvertAdapter;
import com.fan.comment.analysis.worker.comment.converter.CommentConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;

@Configuration
@ComponentScan
@PropertySource("classpath:app.properties")
public class Config {

    @Bean
    CommentConvertAdapter commentConvertAdapter(){
        CommentConvertAdapter commentConvertAdapter = new CommentConvertAdapter();
        commentConvertAdapter.setCommentConverterList(new ArrayList<CommentConverter>(){
            {
                add(new CommCommentConverter());
            }
        });
        return commentConvertAdapter;
    }
}
