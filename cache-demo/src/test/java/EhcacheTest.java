package test.java;

import net.sf.ehcache.CacheManager;
import org.junit.Test;

public class EhcacheTest {
    /**
     * 单独使用ehcache的api
     */
    @Test
    public void test1() {
        String absPath = "D:\\idea_java\\micro-java-base\\cache-demo\\src\\main\\resources\\ehcache.xml";
        // 用来管理多个cache -> user_cache/item_cache/store_cache
        CacheManager cacheManager = CacheManager.create(absPath);
        String[] cacheNames = cacheManager.getCacheNames();
    }
}
