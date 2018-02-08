package com.bornsoft.utils.adapters;

import java.io.IOException;
import java.lang.reflect.Method;

import com.bornsoft.utils.exception.BornApiException;
import com.google.gson.stream.JsonWriter;

public class EnumTypeAdapter<T> extends BornGsonAdapter<T> {

	@Override
	public void write(JsonWriter out, T value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		Class<?> clazz = value.getClass(); 
		if (clazz.isEnum()) {
			Method m = getMethod(value,"getCode");
			if(m==null){
				m = getMethod(value,"code");
			}
			try {
				Object objVal = m.invoke(value, new Object[0]);
				if(objVal==null){
					out.nullValue();
				}else{
					out.value(objVal.toString());
				}
			} catch (Exception e) {
				throw new BornApiException(e);
			} 
		}
	}
	
	private static Method getMethod(Object object, String methodName){
		Method method = null ;
		Class<?> clazz = object.getClass();
		try {    
			method = clazz.getMethod(methodName, new Class[0]) ;    
			return method ;   
		} catch (Exception e) {  
		}
		return null;
	}

}