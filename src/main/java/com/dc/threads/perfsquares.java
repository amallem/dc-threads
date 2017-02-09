package com.dc.threads;

/**
 * Created by mallem on 2/5/17.
 */
public class perfsquares {

    public static void main(String[] args) {

        long N = Long.parseLong(args[0]);
        int K = Integer.parseInt(args[1]);

        Master master = new Master(N, K);

        master.performOperations();

    }
}
