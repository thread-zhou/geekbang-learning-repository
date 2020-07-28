package com.zhoujian.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于实现线程池
 * @author zhoujian
 */
public class ExecutorServicePool {

    /**
     * 初始化线程池
     */
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

}
