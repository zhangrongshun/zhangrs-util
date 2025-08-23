package io.github.zhangrongshun.util.sm;

import com.google.common.util.concurrent.RateLimiter;

import java.time.Duration;

@SuppressWarnings("UnstableApiUsage")
public class RateLimiterWrapper {

    private final RateLimiter rateLimiter;
    private volatile double permitsPerSecond;

    public RateLimiterWrapper(double permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
    }

    public void updateRate(double permitsPerSecond) {
        if (this.permitsPerSecond != permitsPerSecond) {
            synchronized (this) {
                if (this.permitsPerSecond != permitsPerSecond) {
                    this.permitsPerSecond = permitsPerSecond;
                    this.rateLimiter.setRate(permitsPerSecond);
                }
            }
        }
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
