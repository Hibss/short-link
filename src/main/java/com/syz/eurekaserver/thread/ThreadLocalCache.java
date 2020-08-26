package com.syz.eurekaserver.thread;

public class ThreadLocalCache {

    public static ThreadLocal<RequestVO>
            baseSignatureRequestThreadLocal = new ThreadLocal<>();

}
