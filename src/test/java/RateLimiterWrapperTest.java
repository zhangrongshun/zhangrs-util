import com.google.common.util.concurrent.AtomicDouble;
import io.github.zhangrongshun.util.sm.RateLimiterWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class RateLimiterWrapperTest {

    @Test
    public void testMultiThreadUpdateRate() throws InterruptedException {
        // 创建一个RateLimiterWrapper实例
        RateLimiterWrapper rateLimiter = RateLimiterWrapper.builder()
                .permitsPerSecond(1.0)
                .build();

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        // 用于统计成功更新次数
        AtomicInteger updateCount = new AtomicInteger(0);
        
        // 启动多个线程同时更新速率
        for (int i = 0; i < 100; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    double newRate = 0.3;
                    // 每个线程更新不同的速率值
                    rateLimiter.updateRate(newRate);
                    updateCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        // 关闭线程池
        executorService.shutdown();
        Assertions.assertTrue(executorService.awaitTermination(10, TimeUnit.SECONDS));
        
        // 验证所有更新都已完成
        System.out.println("Total updates: " + updateCount.get());
        System.out.println("Final rate: " + rateLimiter.getPermitsPerSecond());
    }

    @Test
    public void testConcurrentAcquireAndUpdateRate() throws InterruptedException {
        // 创建一个RateLimiterWrapper实例
        RateLimiterWrapper rateLimiter = RateLimiterWrapper.builder()
                .permitsPerSecond(5.0)
                .build();

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        
        // 用于统计获取许可的次数
        AtomicInteger acquireCount = new AtomicInteger(0);
        // 用于统计更新速率的次数
        AtomicInteger updateCount = new AtomicInteger(0);
        
        // 启动线程获取许可
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        rateLimiter.acquire();
                        acquireCount.incrementAndGet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        
        // 启动线程更新速率
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        rateLimiter.updateRate(5.0 + (index * j));
                        updateCount.incrementAndGet();
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        // 关闭线程池
        executorService.shutdown();
        Assertions.assertTrue(executorService.awaitTermination(30, TimeUnit.SECONDS));
        
        // 验证结果
        System.out.println("Total acquires: " + acquireCount.get());
        System.out.println("Total updates: " + updateCount.get());
        System.out.println("Final rate: " + rateLimiter.getPermitsPerSecond());
    }


    @Test
    public void testConcurrentAcquireAndUpdateRate2() throws InterruptedException {
        AtomicDouble rate = new AtomicDouble(0.1);
        double andAdd = rate.addAndGet(0.2);
        System.out.println(andAdd);
        AtomicReference<Double> rate2 = new AtomicReference<>(0.1);
        double updateAndGet = rate2.updateAndGet(d -> d + 0.2);
        System.out.println(updateAndGet);
        boolean b = rate2.compareAndSet(0.1, 0.2);
    }
}
