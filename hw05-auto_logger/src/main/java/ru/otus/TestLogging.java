/**
 * Created by Ilya Rogatkin, May 2020
 */

package ru.otus;

import ru.otus.annotations.Log;

public class TestLogging {

    public int getInt() {
        return 100;
    }

    @Log
    public void calc(int param1, String param2, double param3) {
    }

    @Log
    public void calculation(int param) {
    }

}
