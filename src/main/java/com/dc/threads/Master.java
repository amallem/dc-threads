package com.dc.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public Master(int N, int K) {
        this.N = N;
        this.K = K;
        numThreads = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(numThreads);
        //executorService = Executors.newFixedThreadPool(1);
    }

    public void performOperations() {
        try {
            int workUnit = Math.min(N / 5, MAX_WORK_UNIT);
            //int workUnit = N/numThreads;
            int offset = 1;
            long startTime = System.nanoTime();
            CountDownLatch latch = new CountDownLatch(N/workUnit);

            while (offset <= (N - K)) {
                executorService.submit(new Slave(offset, workUnit, K, latch));
                offset += workUnit;
            }
            //latch.await();
            executorService.shutdown();
            executorService.awaitTermination(30, TimeUnit.SECONDS);
            long endTime = System.nanoTime();
            System.out.println("***************** " + (endTime - startTime));
            //System.out.println(squares.toString());
        } catch(Exception e) {
            System.out.println("Exception Occurred in shutting down Executor");
        }
    }
}
