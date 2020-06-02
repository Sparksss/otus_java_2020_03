/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.observer;

import java.util.ArrayList;
import java.util.List;

public class Producer {
    private final List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void run () {
        for(Listener listener : listeners) {
            listener.onUpdate();
        }
    }
}
