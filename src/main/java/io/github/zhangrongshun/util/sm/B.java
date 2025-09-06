package io.github.zhangrongshun.util.sm;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class B {

    static volatile String s;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Interner<Object> objectInterner = Interners.newWeakInterner();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Future<?> submit = executorService.submit(() -> {
                if (s == null) {
                    synchronized (objectInterner.intern("aaa")) {
                        if (s == null) {
                            s = "aaa";
                            System.out.println("aaa");
                        }
                    }
                }
            });
            futures.add(submit);
        }
        for (Future<?> future : futures) {
            future.get();
        }
        System.out.println(1);
    }

}
