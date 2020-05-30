/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.chain;

public interface Handler {
    void setNext(Handler next);
    void process(ATMDepartment department);
}
