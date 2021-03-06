/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class Originator {
    private final Deque<Memento> history = new ArrayDeque<>();

    public void saveStateATM(State state) throws Exception {
        history.push(new Memento(state));
    }

    public State restoreState() {
        return history.pop().getState();
    }
}
