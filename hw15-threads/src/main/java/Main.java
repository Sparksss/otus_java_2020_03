/**
 * Created by ilya on Aug, 2020
 */
public class Main {
    public static void main(String[] args) {
        Test test = new Test();
        Thread thread = new Thread(test);
        Thread thread1 = new Thread(test);
        thread.setName("Поток 1");
        thread1.setName("Поток 2");
        thread.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }

        thread1.start();
    }
}
