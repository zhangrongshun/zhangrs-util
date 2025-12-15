package io.github.zhangrongshun.util;

/**
 * JDK版本工具类，用于判断当前运行的JDK版本
 */
public class JdkVersionUtil {
    
    private static final int MAJOR_VERSION;
    
    static {
        // 获取Java版本
        String version = System.getProperty("java.version");
        MAJOR_VERSION = parseMajorVersion(version);
    }
    
    /**
     * 解析Java版本字符串，获取主版本号
     * 
     * @param version Java版本字符串
     * @return 主版本号
     */
    private static int parseMajorVersion(String version) {
        if (version.startsWith("1.")) {
            // JDK 8 或更早版本，格式如 "1.8.0_292"
            return Integer.parseInt(version.substring(2, 3));
        } else {
            // JDK 9 或更新版本，格式如 "11.0.12" 或 "9.0.4"
            int dotIndex = version.indexOf(".");
            if (dotIndex != -1) {
                return Integer.parseInt(version.substring(0, dotIndex));
            } else {
                // 没有小数点，如 "17"
                return Integer.parseInt(version);
            }
        }
    }
    
    /**
     * 判断当前JDK版本是否为9以上
     * 
     * @return 如果是JDK 9及以上版本返回true，否则返回false
     */
    public static boolean isJdk9OrLater() {
        return MAJOR_VERSION >= 9;
    }
    
    /**
     * 获取当前JDK主版本号
     * 
     * @return JDK主版本号
     */
    public static int getMajorVersion() {
        return MAJOR_VERSION;
    }

    public static void main(String[] args) {
        System.out.println(JdkVersionUtil.isJdk9OrLater());
    }
}