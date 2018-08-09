package guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

import java.util.concurrent.TimeUnit;

/**
 * 执行结果
 * no cache
 * 123
 * 123
 * key:123 value:123 has been removed
 * no cache
 * 123
 *
 * @author tracy.
 * @create 2018-08-06 14:34
 **/
public class GuavaCacheTester {
    public static void main(String[] args) throws Exception {
        LoadingCache<String, Integer> cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS)
                .removalListener((RemovalListener<String, Integer>) removalNotification -> {
                    System.out.println("key:" + removalNotification.getKey() + " value:" + removalNotification.getValue() + " has been removed");
                }).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        System.out.println("no cache");
                        return Integer.valueOf(key);
                    }
                });
        System.out.println(cache.get("123"));
        System.out.println(cache.get("123"));
        Thread.sleep(5000);
        System.out.println(cache.get("123"));
    }
}
