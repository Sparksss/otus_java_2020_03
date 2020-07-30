import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;



public class MyCacheTest {
    @Test
    public void testCleanCacheAfterRunGC() throws InterruptedException {
        HwCache<Long, Integer> cache = new MyCache<>();
        for (int i = 0; i < 10000; i++) {
            long l = i;
            cache.put(l, i);
        }
        System.gc();
        Thread.sleep(200);
        int cacheSize = 0;
        for (long i = 0; i < 10000; i++) {
            Integer intFromCache = cache.get(i);
            if (intFromCache != null) {
                cacheSize++;
            }
        }
        Assertions.assertEquals(0, cacheSize);
    }
}
