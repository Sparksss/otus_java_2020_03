



public class Main {

    public static void main(String[] args) {
        Test test = new Test();
        Thread thread = new Thread(test);
        Thread thread1 = new Thread(test);
        thread.start();
        thread1.start();
    }
}
