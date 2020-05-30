/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.memento;

class Memento {
    private final State state;

    Memento(State state) {
        this.state = new State(state);
    }

    State getState() {
        return state;
    }
}