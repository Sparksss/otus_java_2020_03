/**
 * Created by Ilya Rogatkin, May 2020
 */

package ru.otus;

import ru.otus.annotations.Log;

import java.lang.invoke.StringConcatFactory;

public class TestLogging {

    public int getInt() {
        return 100;
    }

    @Log
    public void calc(int param1, float param2, double param3) {
    }

    @Log
    public void calculation() {
    }

}
