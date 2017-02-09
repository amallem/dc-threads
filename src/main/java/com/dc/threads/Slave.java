package com.dc.threads;


/**
 * Created by mallem on 2/5/17.
 */
public class Slave implements Runnable {

    private final long workUnit;
    private final long offset;
    private final int K;

    public Slave(long offset, long workUnit, int K) {
        this.offset = offset;
        this.workUnit = workUnit;
        this.K = K;
    }

    public void run() {
       // System.out.println("Started Thread with name : " + Thread.currentThread().getName()
         //       + " with offset: " + offset + " with work Unit: " + workUnit);
        try {
            long sum_so_far = 0;
            for(long i=offset; i<offset+K; i++) {
                sum_so_far += (i*i);
            }

            for(long i=offset; i < offset+workUnit; i++) {
                if(isSquare(sum_so_far)) {
                    System.out.println(i);
                    //System.out.println(Thread.currentThread().getName() + " " + sum_so_far + " ===========================> " + i);
                }
                sum_so_far -=  (i*i);
                sum_so_far +=  ((i+K) * (i+K));
            }
        } catch(Exception e) {
            System.out.println("Exception Occurred in Slave.");
        }
    }

    private boolean isSquare(long sum_so_far) {
        int num = (int) Math.sqrt(sum_so_far);
        return (sum_so_far == (num*num));
    }
}
