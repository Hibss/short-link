package com.syz.eurekaserver.thread;


import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.*;

/**
 * @ProjectName: javaTest
 * @Package: com.syz.java.test.thread
 * @ClassName: ExecutorTest
 * @Author: Administrator
 * @Description:
 * @Date 2020/1/3/00317:06
 */
public class ExecutorTest {

    static ExecutorService service1 = newFixedThreadPool(3);
    static ExecutorService service2 = newSingleThreadExecutor();
    static ExecutorService service3 = newCachedThreadPool();
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    private static void test3() {
        for (int i = 0; i < 100; i++) {
            service3.execute(new Thread1());
        }
        service3.shutdown();
    }


    private static void test2() {
        for (int i = 0; i < 100; i++) {
            service2.execute(new Thread1());
        }
        service2.shutdown();
    }

    private static void test1() {
        for (int i = 0; i < 100; i++) {
            service1.execute(new Thread1());
        }
        service1.shutdown();
    }

    static class Thread1 implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try{
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
