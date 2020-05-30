/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.observer;

import ATM_department.Command.Command;

public class Consumer implements Listener {

    private Command command;

    public Consumer(Command command){
        this.command = command;
    }

    @Override
    public void onUpdate() {
        command.execute();
    }
}
