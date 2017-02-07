package com.dc.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

/**
 * Created by mallem on 2/5/17.
 */
public class Slave implements Runnable {

    private final int workUnit;
    private final int offset;
    private CountDownLatch latch;
    private final int K;

    public Slave(int offset, int workUnit, int K, CountDownLatch latch) {
        this.offset = offset;
        this.workUnit = workUnit;
        this.latch = latch;
        this.K = K;
    }

    public void run() {
       // System.out.println("Started Thread with name : " + Thread.currentThread().getName()
         //       + " with offset: " + offset + " with work Unit: " + workUnit);
        try {
            long sum_so_far = 0;
            for(int i=offset; i<offset+K; i++) {
                sum_so_far += (long) (i*i);
            }

            for(int i=offset; i < offset+workUnit; i++) {
                if(isSquare(sum_so_far)) {
                    System.out.println(Thread.currentThread().getName() + " ===========================> " + i);
                }
                sum_so_far -= (long) (i*i);
                sum_so_far += (long) ((i+K) * (i+K));
            }
            //System.out.println(Thread.currentThread().getName() + " " + offset);
        } finally {
            latch.countDown();
            //System.out.println("-----------> " + latch.getCount());
        }
    }

    private boolean isSquare(long sum_so_far) {
        int num = (int) Math.sqrt(sum_so_far);

        return (sum_so_far == (num*num));
    }
}
