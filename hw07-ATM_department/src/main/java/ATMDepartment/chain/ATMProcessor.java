/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.chain;

abstract class ATMProcessor implements Handler{
    private Handler next;

    private Handler getNext() {return next;}

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public void process(ATMDepartment department) {
        before();
        processInternal(department);
        after();
        if(getNext() != null) {
            getNext().process(department);
        }
    }

    protected abstract void processInternal(ATMDepartment department);

    public abstract String getProcessorName();

    private void before() {
        System.out.println("Before: " + getProcessorName());
    }

    private void after() {
        System.out.println("After: " + getProcessorName());
    }
}
