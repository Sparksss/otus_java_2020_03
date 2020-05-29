/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.observer;

import ATM_department.Command.Command;

import java.util.ArrayList;
import java.util.List;

public class Producer {
    private final List<Command> listners = new ArrayList<>();

    void addListener(Command command) {
        listners.add(command);
    }

    void removeListener(Command command) {
        listners.remove(command);
    }

    void event () {
        for(Command command : listners) {
            command.execute();
        }
    }
}
