package io.github.zhangrongshun.util.sm;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.common.util.concurrent.RateLimiter;

import java.time.Duration;

@SuppressWarnings("UnstableApiUsage")
public class RateLimiterWrapper {

    private final RateLimiter rateLimiter;
    private final AtomicDouble permitsPerSecond;

    public RateLimiterWrapper(double permitsPerSecond) {
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
        this.permitsPerSecond = new AtomicDouble(permitsPerSecond);
    }

    public void updateRate(double permitsPerSecond) {
        double currentPermitsPerSecond = this.permitsPerSecond.get();
        if (currentPermitsPerSecond != permitsPerSecond) {
            if (this.permitsPerSecond.compareAndSet(currentPermitsPerSecond, permitsPerSecond)) {
                rateLimiter.setRate(permitsPerSecond);
            }
        }
    }

    public double getPermitsPerSecond() {
        return permitsPerSecond.get();
    }

    public void acquire() {
        rateLimiter.acquire();
    }

    public void acquire(int permits) {
        rateLimiter.acquire(permits);
    }

    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }

    public boolean tryAcquire(Duration timeout) {
        return rateLimiter.tryAcquire(timeout);
    }

}
