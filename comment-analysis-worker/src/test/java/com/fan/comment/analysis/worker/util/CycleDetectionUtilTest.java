package com.fan.comment.analysis.worker.util;

import org.junit.Test;

public class CycleDetectionUtilTest {

    @Test
    public void test(){
        CycleDetectionUtil cycleDetectionUtil = new CycleDetectionUtil();
        assert false == cycleDetectionUtil.isCycle("aa");
        assert true == cycleDetectionUtil.isCycle("aa");
    }
}
