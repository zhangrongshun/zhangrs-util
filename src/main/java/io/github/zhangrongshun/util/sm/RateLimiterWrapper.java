package io.github.zhangrongshun.util.sm;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.common.util.concurrent.RateLimiter;

import java.time.Duration;

@SuppressWarnings("UnstableApiUsage")
public class RateLimiterWrapper {

    private final RateLimiter rateLimiter;
    private final AtomicDouble permitsPerSecond;

    private RateLimiterWrapper(Builder builder) {
        this.permitsPerSecond = new AtomicDouble(builder.permitsPerSecond);
        if (builder.warmupPeriod != null) {
            this.rateLimiter = RateLimiter.create(builder.permitsPerSecond, builder.warmupPeriod);
        } else {
            this.rateLimiter = RateLimiter.create(builder.permitsPerSecond);
        }
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private double permitsPerSecond;
        private Duration warmupPeriod;

        private Builder() {
        }

        public Builder permitsPerSecond(double permitsPerSecond) {
            this.permitsPerSecond = permitsPerSecond;
            return this;
        }

        public Builder warmupPeriod(Duration warmupPeriod) {
            this.warmupPeriod = warmupPeriod;
            return this;
        }

        public RateLimiterWrapper build() {
            if (permitsPerSecond <= 0) {
                throw new IllegalArgumentException("permitsPerSecond must be positive");
            }
            return new RateLimiterWrapper(this);
        }
    }
}
