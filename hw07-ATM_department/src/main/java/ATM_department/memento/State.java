/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.memento;

import ATM_department.chain.ATMDepartment;

public class State {

    private ATMDepartment department;

    public State(ATMDepartment atmDepartment) {
        this.department = atmDepartment;
    }

    State(State state) {
        this.department = state.getDepartment().copy();
    }

    ATMDepartment getDepartment() {
        return department;
    }
}