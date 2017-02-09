package com.dc.threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by mallem on 2/5/17.
 */
public class Master {
    private int N;

    private int K;

    private static final int MAX_WORK_UNIT = 500;

    private static int numThreads;

    private ExecutorService executorService;

    private final ThreadMXBean bean;

    public Master(int N, int K) {
        this.N = N;
        this.K = K;
        numThreads = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(numThreads);
        bean = ManagementFactory.getThreadMXBean();
    }

    public void performOperations() {
        try {
            int workUnit = Math.min(N / 5, MAX_WORK_UNIT);
            int offset = 1;
            long startTime = System.nanoTime();
            CountDownLatch latch = new CountDownLatch(N/workUnit);
            HashSet<Long> threadIds = new HashSet<>();

            while (offset <= N) {
                executorService.submit(new Slave(offset, workUnit, K, latch, threadIds));
                offset += workUnit;
            }
            latch.await();
            long cpuTime = threadIds.stream().mapToLong(bean::getThreadCpuTime).sum();
            executorService.shutdownNow();
            executorService.awaitTermination(30, TimeUnit.SECONDS);
            long endTime = System.nanoTime();
            System.out.println("Total Time: " + (endTime-startTime));
            System.out.println("CPU Time: " + cpuTime);
            double ratio = (double) (endTime - startTime) / (double) cpuTime;
            System.out.println("***************** " + ratio);
        } catch(Exception e) {
            System.out.println("Exception Occurred in shutting down Executor");
        }
    }
}
