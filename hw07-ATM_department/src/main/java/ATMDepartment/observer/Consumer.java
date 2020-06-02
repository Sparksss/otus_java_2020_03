/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.observer;

import ATMDepartment.Command.Command;

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
