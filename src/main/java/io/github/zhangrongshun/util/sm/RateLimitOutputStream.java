package io.github.zhangrongshun.util.sm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 限速OutputStream包装类
 * 通过控制写入数据的速度来限制输出流的传输速率
 */
public class RateLimitOutputStream extends OutputStream {
    
    private final OutputStream delegate;
    private final long maxBytesPerSecond;
    private long bytesWritten = 0;
    private long lastTime = System.nanoTime();
    
    /**
     * 构造函数
     * 
     * @param delegate 被包装的输出流
     * @param maxBytesPerSecond 每秒最大字节数
     */
    public RateLimitOutputStream(OutputStream delegate, long maxBytesPerSecond) {
        if (delegate == null) {
            throw new IllegalArgumentException("OutputStream cannot be null");
        }
        if (maxBytesPerSecond <= 0) {
            throw new IllegalArgumentException("Max bytes per second must be positive");
        }
        this.delegate = delegate;
        this.maxBytesPerSecond = maxBytesPerSecond;
    }
    
    @Override
    public void write(int b) throws IOException {
        checkRate(1);
        delegate.write(b);
        bytesWritten++;
    }
    
    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }
    
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        checkRate(len);
        delegate.write(b, off, len);
        bytesWritten += len;
    }
    
    @Override
    public void flush() throws IOException {
        delegate.flush();
    }
    
    @Override
    public void close() throws IOException {
        delegate.close();
    }
    
    /**
     * 检查并控制写入速率
     * 
     * @param bytesToWrite 即将写入的字节数
     * @throws IOException
     */
    private void checkRate(int bytesToWrite) throws IOException {
        if (bytesToWrite <= 0) return;
        
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;
        
        // 计算在给定时间内应该写入的最大字节数
        long expectedBytes = (elapsedTime * maxBytesPerSecond) / TimeUnit.SECONDS.toNanos(1);
        
        // 如果已经写入的字节超过了预期，则需要等待
        if (bytesWritten > expectedBytes) {
            long excessBytes = bytesWritten - expectedBytes;
            long waitTime = (excessBytes * TimeUnit.SECONDS.toNanos(1)) / maxBytesPerSecond;
            
            if (waitTime > 0) {
                try {
                    TimeUnit.NANOSECONDS.sleep(waitTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Rate limit interrupted", e);
                }
                
                // 更新时间，因为已经等待过了
                lastTime = System.nanoTime();
            }
        } else if (elapsedTime > TimeUnit.SECONDS.toNanos(1)) {
            // 超过1秒重置计数器，避免整数溢出和长时间累积误差
            lastTime = currentTime;
            bytesWritten = 0;
        }
    }
    
    /**
     * 获取每秒最大字节数限制
     * 
     * @return 每秒最大字节数
     */
    public long getMaxBytesPerSecond() {
        return maxBytesPerSecond;
    }
    
    /**
     * 获取已写入的总字节数
     * 
     * @return 已写入的总字节数
     */
    public long getBytesWritten() {
        return bytesWritten;
    }
}
