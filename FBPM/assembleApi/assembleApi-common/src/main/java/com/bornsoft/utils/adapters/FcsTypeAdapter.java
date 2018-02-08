package com.bornsoft.utils.adapters;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bornsoft.utils.tool.ReflectUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class FcsTypeAdapter<T> extends BornGsonAdapter<T> {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Gson gson = null;

	public FcsTypeAdapter(Gson useGson) {
		super();
		this.gson = useGson;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void write(JsonWriter out, T obj) throws IOException {
		if (obj == null) {
			out.nullValue();
			return;
		} else {
			out.beginObject();
			Map<Field, Object> valueMap = ReflectUtils.getValueMap(obj);
			Object value = null;
			String name = null;
			for (Field field : valueMap.keySet()) {
				name = field.getName();
				if(StringUtils.equals(name, "serialVersionUID")){
					continue;
				}
				try {
					int modifiers = field.getModifiers();
					if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
						continue;
					}
				} catch (Exception e) {
					logger.error("获取修饰符失败",e);
				}

				value = valueMap.get(field);
				if (value != null && StringUtils.isNotBlank(value.toString())) {
					TypeAdapter adapter = gson.getAdapter(value.getClass());
					out.name(name);
					adapter.write(out, value);
				}
			}
			out.endObject();
		}

	}
}