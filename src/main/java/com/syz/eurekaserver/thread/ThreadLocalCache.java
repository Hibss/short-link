package com.syz.eurekaserver.thread;

import java.util.Map;

public class ThreadLocalCache {

    public static ThreadLocal<RequestVO>
            baseSignatureRequestThreadLocal = new ThreadLocal<>();

    public static ThreadLocal<Map<String,String[]>>
            requestMapLocalThread = new ThreadLocal<>();
}
