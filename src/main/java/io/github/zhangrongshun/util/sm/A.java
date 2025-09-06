package io.github.zhangrongshun.util.sm;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Executors;

public class A {

    public static void main(String[] args) {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Futures.addCallback(service.submit(() -> {
                System.out.println("do" + finalI);
                throw new RuntimeException("do" + finalI);
            }), new FutureCallback<Object>() {
                @Override
                public void onSuccess(@Nullable Object result) {
                    System.out.println("success" + finalI);
                }

                @Override
                public void onFailure(@Nonnull Throwable t) {
                    System.err.println(String.valueOf(t));
                }
            }, service);
        }
        System.out.println(2);
    }

}
