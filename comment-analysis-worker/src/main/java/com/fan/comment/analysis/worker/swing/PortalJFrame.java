package com.fan.comment.analysis.worker.swing;

import com.fan.comment.analysis.worker.method.MethodHolder;
import com.fan.comment.analysis.worker.output.CommentPrinter;
import com.fan.comment.analysis.worker.output.SwingCommentPrinter;
import com.fan.comment.analysis.worker.service.CommentTraceService;
import com.fan.comment.analysis.worker.util.CycleDetectionUtil;
import com.fan.comment.analysis.worker.util.SpringInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

@Component
public class PortalJFrame extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(PortalJFrame.class);

    @Resource
    private CycleDetectionUtil cycleDetectionUtil;

    private JLabel jlabelFile = new JLabel("file");
    private JLabel jlabelClass = new JLabel("class");
    private JLabel jlabelMethod = new JLabel("method");
    private JLabel jlabelParas = new JLabel("paras");

    private JTextField jtfFile = new JTextField(50);
    private JTextField jtfClass = new JTextField(50);
    private JTextField jtfMethod = new JTextField(50);
    private JTextField jtfParams = new JTextField(50);

    private JButton jbFile = new JButton("choose");
    private JButton jbProcess = new JButton("Process");

    private JTextArea jTextArea = new JTextArea(52, 120);

    private File file;

    public PortalJFrame() {

    }

    @PostConstruct
    public void init(){
        setProperties();
        addAction();
        addJFrame();
        SwingConsole.run(this, 1500, 950);
    }

    private void addAction() {
        jbFile.addActionListener(e -> {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(PortalJFrame.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                file = c.getSelectedFile();
                jtfFile.setText(file.getName());
            }
        });

        jbProcess.addActionListener(e -> {
            logger.info("Start Analysis");
            cycleDetectionUtil.initStack();
            String className = jtfClass.getText();
            String methodName = jtfMethod.getText();
            String methodArgs = jtfParams.getText();

            if (file == null || className == null || methodName == null) {
                JOptionPane.showMessageDialog(PortalJFrame.this,"填写信息不完整");
                return;
            }
            logger.debug("fileName:{}", file.getAbsolutePath());
            logger.debug("className:{}", className);
            logger.debug("methodName:{}", methodName);
            logger.debug("methodArgs:{}", methodArgs);
            CommentTraceService commentTraceService = SpringInitializer.getBean(CommentTraceService.class);
            CommentPrinter commentPrinter = SpringInitializer.getBean(SwingCommentPrinter.class);
            MethodHolder methodHolder = commentTraceService.getCommentHolderList(file, className, methodName, methodArgs, "代码入口");
            if(methodHolder!=null){
                StringBuilder stringBuilder = commentPrinter.print(methodHolder);
                jTextArea.setText(stringBuilder.toString());
            }else{
                JOptionPane.showMessageDialog(PortalJFrame.this,"未找到对应分析方法");
                jTextArea.setText("");
            }
            logger.info("Exit Analysis");
        });
    }

    private void setProperties() {
        jtfFile.setEditable(false);
        setTitle("comment analysis");
    }

    private void addJFrame() {
        JPanel pNorth = new JPanel();
        JPanel pUp = new JPanel();
        pUp.setLayout(new FlowLayout(FlowLayout.LEFT));
        jlabelFile.setBorder(new EmptyBorder(0, 20, 0, 0));
        pUp.add(jlabelFile);
        pUp.add(jtfFile);
        pUp.add(jbFile);
        pUp.add(jlabelClass);
        pUp.add(jtfClass);
        JPanel pDown = new JPanel();
        pDown.setLayout(new FlowLayout(FlowLayout.LEFT));
        jlabelMethod.setBorder(new EmptyBorder(0, 20, 0, 0));
        pDown.add(jlabelMethod);
        pDown.add(jtfMethod);
        pDown.add(jlabelParas);
        pDown.add(jtfParams);
        pDown.add(jbProcess);
        pNorth.setLayout(new GridLayout(2, 1));
        pNorth.add(pUp);
        pNorth.add(pDown);
        add(pNorth, BorderLayout.NORTH);
        JPanel pCenter = new JPanel();
        jTextArea.setAutoscrolls(true);
        JScrollPane scroll = new JScrollPane(jTextArea);
        pCenter.add(scroll);
        add(pCenter, BorderLayout.CENTER);
    }

}
