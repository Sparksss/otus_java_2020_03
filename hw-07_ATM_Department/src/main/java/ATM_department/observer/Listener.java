/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.observer;

import ATM_department.Command.Command;

public interface Listener {
    void onUpdate(Command command);
}
