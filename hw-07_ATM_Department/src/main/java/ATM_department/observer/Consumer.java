/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.observer;

import ATM_department.Command.Command;

public class Consumer implements Listener {

    @Override
    public void onUpdate(Command command) {
        command.execute();
    }
}
