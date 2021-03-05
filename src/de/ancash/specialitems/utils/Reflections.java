package de.ancash.specialitems.utils;

import java.lang.reflect.Field;

public class Reflections {
	
	public static void setPrivateField(Object clazz, String field,Object value) {
		try {
			Field b = clazz.getClass().getDeclaredField(field);
			b.setAccessible(true);
			b.set(clazz, value);
		} catch(NullPointerException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static void setPrivateField(Class<?> clazz, String field, Object obj, Object value) {
		try {
			Field b = clazz.getDeclaredField(field);
			b.setAccessible(true);
			b.set(obj, value);
		} catch(NullPointerException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static Object getPrivateField(Class<?> clazz, String field, Object o) {
		try {
			Field b = clazz.getDeclaredField(field);
			b.setAccessible(true);
			return b.get(o);
		} catch(NullPointerException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
