package com.dc.threads;

/**
 * Created by mallem on 2/7/17.
 */
public class Test {
    public static void main(String[] args) {
         int n = Integer.parseInt(args[0]);
         int k = Integer.parseInt(args[1]);

         int sum = 0;
         for(int i=n; i < n+k; i++) {
             sum += (i*i);
         }

         System.out.println(sum);
    }
}
