/**
 * Created by Ilya Rogatkin, Jun 2020
 */

package Checker;

import java.lang.reflect.Modifier;
import java.util.Objects;

public class Check implements Cheker {

    @Override
    public boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || String.class.isAssignableFrom(clazz) ||
                Number.class.isAssignableFrom(clazz) ||
                Boolean.class.isAssignableFrom(clazz) ||
                Character.class.isAssignableFrom(clazz);
    }


    @Override
    public boolean isSerializedField(int modifiers) {
        return Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers);
    }

    @Override
    public boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }
}
