/**
 * Created by Ilya Rogatkin, Apr 2020
 */

/*

-Xms256m
-Xmx256m
-Xlog:gc=debug:file=./hw04-garbage_collector/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./hw04-garbage_collector/logs/dump
-XX:+UseG1GC
*/

package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import ru.otus.lists.ArrayListWorker;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;


public class PerformanceTest {

    private static int count = 0;

    public static void main(String[] args) {
        System.out.println("Starting pid : " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        ArrayListWorker lw = new ArrayListWorker();
        try{
            repeat(lw);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void repeat(ArrayListWorker lw) throws InterruptedException {
        lw.addRandomElements(1_000_000);
        lw.deleteElements(1_000);
        count++;
        System.out.println(count);
        if(lw.getSizeList() < Integer.MAX_VALUE) repeat(lw);
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name: " + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if(notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start: " + startTime + " Name: " + gcName + ", action: " + gcAction + ", gcCause:" + gcCause + "(" + duration + "ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
