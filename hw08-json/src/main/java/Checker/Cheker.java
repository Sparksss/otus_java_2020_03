/**
 * Created by Ilya Rogatkin, Jun 2020
 */

package Checker;

public interface Cheker {
    boolean isPrimitive(Class<?> clazz);
    boolean isSerializedField(int modifiers);
    boolean isNull(Object obj);
}
