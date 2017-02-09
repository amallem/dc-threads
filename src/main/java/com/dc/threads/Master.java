package com.dc.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by mallem on 2/5/17.
 * K = 96
 */
public class Master {
    private long N;

    private int K;

    private static final int MAX_WORK_UNIT = 50000;

    private static int numThreads;

    private ExecutorService executorService;

    public Master(long N, int K) {
        this.N = N;
        this.K = K;
        numThreads = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(numThreads);
    }

    public void performOperations() {
        try {
            long workUnit = Math.min(N / numThreads, MAX_WORK_UNIT);
            int offset = 1;
            long startTime = System.nanoTime();
            while (offset <= N) {
                executorService.submit(new Slave(offset, workUnit, K));
                offset += workUnit;
            }
            executorService.shutdown();
            executorService.awaitTermination(30, TimeUnit.SECONDS);
            long endTime = System.nanoTime();
            System.out.println("Total Time: " + ((double)(endTime-startTime)/(double) 1000000000) + " secs");
        } catch(Exception e) {
            System.out.println("Exception Occurred in shutting down Executor");
        }
    }
}
