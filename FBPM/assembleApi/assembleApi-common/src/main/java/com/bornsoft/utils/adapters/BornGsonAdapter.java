package com.bornsoft.utils.adapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * @Description: Gson 适配器基类 
 * @author taibai@yiji.com
 * @date 2016-9-14 上午10:50:09
 * @version V1.0
 */
public abstract class BornGsonAdapter<T> extends TypeAdapter<T> {

	@Override
	public T read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		} else {
			throw new UnsupportedOperationException("该对象的反序列化暂未实现");
		}
	}

}