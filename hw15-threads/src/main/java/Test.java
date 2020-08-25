/**
 * Created by ilya on Aug , 2020
 */
public class Test implements Runnable {
    private int number = 0;

    @Override
    public void run() {
        increment(Thread.currentThread().getName());
    }

    private void increment(String name) {
        for(int i = 0; i < 10; i++) {
            System.out.println(name + "->" + i);
        }
    }

    public int getNumber() {
        return number;
    }
}
