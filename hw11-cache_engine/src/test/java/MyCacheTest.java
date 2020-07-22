import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;



public class MyCacheTest {
    @Test
    public void testCleanCacheAfterRunGC() throws InterruptedException {
        HwCache<String, Integer> cache = new MyCache<>();
        for (int i = 0; i < 10000; i++) {
            cache.put("" + i, i);
        }
        System.gc();
        Thread.sleep(200);
        int cacheSize = 0;
        for (int i = 0; i < 10000; i++) {
            Integer intFromCache = cache.get("" + i);
            if (intFromCache != null) {
                cacheSize++;
            }
        }
        Assertions.assertEquals(0, cacheSize);
    }
}
