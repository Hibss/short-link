package com.syz.eurekaserver.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @ProjectName: javaTest
 * @Package: com.syz.java.test.thread
 * @ClassName: RecursiveTaskTest
 * @Author: Administrator
 * @Description:
 * @Date 2020/1/6/00611:33
 */
public class RecursiveTaskTest extends RecursiveTask<Long> {

    private Long start,end;
    private Long threadHold = 10000L;
    RecursiveTaskTest(Long start,Long end){
        this.start = start;
        this.end = end;
    }
    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Long compute() {
        Long length = end - start;
        if(length < threadHold){
            return LongStream.rangeClosed(start,end).reduce(0L,Long::sum);
        }else{
            Long middle = (end + start) /2 ;
            RecursiveTaskTest left = new RecursiveTaskTest(start,middle);
            left.fork();
            RecursiveTaskTest right = new RecursiveTaskTest(middle+1,end);
            right.fork();
            return right.join()+ left.join();
        }
    }

    public static void main(String[] args) {
//        Long sum = 0L,start = 1L,end = 100L;
//        for (Long i = start;i<=end;++i){
//            sum+=i;
//            System.out.println(i);
//        }
//        System.out.println(sum);
        Instant now = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        RecursiveTaskTest taskTest = new RecursiveTaskTest(0L,1000000000L);
        Long sum = pool.invoke(taskTest);
        Instant finish = Instant.now();
        System.out.println("result:"+sum+"，时间:"+ Duration.between(now,finish).toMillis());
//        long sum1 = LongStream.rangeClosed(0, 1000000000L).parallel().reduce(0, Long::sum);
//        Instant end = Instant.now();
//        System.out.println("result:"+sum1+"，时间:"+ Duration.between(finish,end).toMillis());
    }
}
