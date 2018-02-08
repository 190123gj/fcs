package com.bornsoft.utils.adapters;

import java.lang.reflect.Type;

import com.bornsoft.utils.base.BornInfoBase;
import com.bornsoft.utils.base.BornOrderBase;
import com.bornsoft.utils.base.BornResultBase;
import com.bornsoft.utils.base.IBornEnum;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class BornAdapterFactory implements TypeAdapterFactory {

	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        Class<? super T> rawType = type.getRawType();  
        if(rawType!=null){
        	if(rawType.isEnum()){  
        		Type[] types = rawType.getGenericInterfaces();  
        		for(Type item:types){  
        			if(item == IBornEnum.class){  
        				return  new EnumTypeAdapter<T>();
        			}  
        		}  
        	}else if(rawType.getPackage()!=null &&
        			(rawType.isAssignableFrom(BornOrderBase.class)||
        			rawType.isAssignableFrom(BornInfoBase.class)||
        			rawType.isAssignableFrom(BornResultBase.class)||
        			rawType.getPackage().toString().contains("fcs"))){
        		return new FcsTypeAdapter<T>(gson);
        	}  
        }
        return null;  
	}
        
 
}