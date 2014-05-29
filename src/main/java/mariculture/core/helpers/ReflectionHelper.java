package mariculture.core.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHelper {
	public static void setFinalStatic(Field field, Object newValue) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}
	
	public static boolean setFinalStatic(Class clazz, String field1, String field2, Object newValue) {
		try {
			setFinalStatic(clazz.getDeclaredField(field1), newValue);
			return true;
		} catch(Exception e) {
			try {
				if(!field2.equals(""))setFinalStatic(clazz.getDeclaredField(field2), newValue);
				return true;
			} catch (Exception e2) {
				e2.printStackTrace();
				return false;
			}
		}
	}
}
