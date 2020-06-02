/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.chain;

public interface Handler {
    void setNext(Handler next);
    void process(ATMDepartment department);
}
