/**
 * Created by Ilya Rogatkin, Apr 2020
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
    public static void main(String[] args) {
        System.out.println("Starting pid : " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();

        ArrayListWorker lw = new ArrayListWorker();
        try{
            repeat(lw);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        lw.deleteElements(10);
    }

    public static void repeat(ArrayListWorker lw) throws InterruptedException {
        Thread.sleep(3000);
        lw.addRandomElements(1000000);
        lw.deleteElements(10000);
        if(lw.getSizeList() < Integer.MAX_VALUE) repeat(lw);
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean gcbean : gcbeans){
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

                    System.out.print("start: " + startTime + " Name: " + gcName + ", action: " + gcAction + ", gcCause:" + gcCause + "(" + duration + "ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
