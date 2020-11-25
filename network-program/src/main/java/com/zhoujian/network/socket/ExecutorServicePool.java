package com.zhoujian.network.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类
 * @author zhoujian
 */
public class ExecutorServicePool {

    /**
     * 初始化线程池
     */
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

}
