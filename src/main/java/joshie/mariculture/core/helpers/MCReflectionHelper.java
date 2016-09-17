package joshie.mariculture.core.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MCReflectionHelper {
    public static void setFinalStatic(Object value, Field field) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, value);
    }

    @SuppressWarnings("unchecked")
    public static Method getMethod(Class clazz, String method) {
        try {
            return clazz.getMethod(method);
        } catch (NoSuchMethodException e) { return null; }
    }

    @SuppressWarnings("unchecked")
    public static Field getField(Class clazz, String field) {
        try {
            return clazz.getField(field);
        } catch (NoSuchFieldException e) { return null; }
    }
}
