package com.assemble.ad.http;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义的线程池，用于执行Runnable任务
 */
public class HttpThreadPoolExecutor extends ThreadPoolExecutor {

    private static HttpThreadPoolExecutor httpThreadPoolExecutor;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 3 + 1;
    private static final long KEEP_ALIVE = 10L;
    //用于给线程池创建线程的线程工厂类
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "NetWork#" + mCount.getAndIncrement());
        }
    };

    private HttpThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public static synchronized HttpThreadPoolExecutor getInstance() {
        if (httpThreadPoolExecutor == null) {
            httpThreadPoolExecutor = new HttpThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), sThreadFactory);
        }
        return httpThreadPoolExecutor;
    }
}
