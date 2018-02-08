package com.bornsoft.utils.tool;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bornsoft.utils.exception.BornApiException;

/**
  * @Description: 枚举序列化
  * @author taibai@yiji.com 
  * @date  2016-8-22 上午10:52:43
  * @version V1.0
 */
public class BornEnumSerializer implements ObjectSerializer {

    public final static BornEnumSerializer instance = new BornEnumSerializer();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object == null) {
            serializer.getWriter().writeNull();
            return;
        }
        Method codeMethod = null;
		try {
			codeMethod = object.getClass().getDeclaredMethod("code");
		} catch (Exception e) {
			throw new BornApiException("获取枚举值方法查找失败：",e);
		}
        if (serializer.isEnabled(SerializerFeature.WriteEnumUsingToString)) {
            try {
				serializer.write(codeMethod.invoke(object, new Object[]{}).toString());
			} catch (Exception e) {
				throw new BornApiException("获取枚举值失败：",e);
			}
        } else {
            Enum<?> e = (Enum<?>) object;
            out.writeInt(e.ordinal());
        }
    }
}


