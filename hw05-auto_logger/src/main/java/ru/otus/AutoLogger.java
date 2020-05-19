/**
 * Created by Ilya Rogatkin, May 2020
 */

package ru.otus;


public class AutoLogger {
    public static void main(String[] args) {

        TestLogging test = new TestLogging();
        test.calculation(10);
        test.calculation(5);
        test.calc(10,"param2",10000.43432D);

    }
}
