package com.sang.common.core;

import org.slf4j.MDC;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 带追踪id的线程池
 *     private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 16, 5L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(),  new TraceIdThreadFactory());
 */
public class TraceIdThreadFactory implements ThreadFactory {
    private final ThreadFactory delegate = Executors.defaultThreadFactory();

    public Thread newThread(Runnable runnable) {
        String traceId = MDC.get("traceId");
        return this.delegate.newThread(new TraceIdRunnable(runnable, traceId));
    }
}