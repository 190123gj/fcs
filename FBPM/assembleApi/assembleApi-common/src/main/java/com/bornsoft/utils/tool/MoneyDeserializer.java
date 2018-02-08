package com.bornsoft.utils.tool;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.yjf.common.lang.util.money.Money;

/**
 * money类的反序列化器
 */
public class MoneyDeserializer implements ObjectDeserializer {
	public final static MoneyDeserializer instance = new MoneyDeserializer();
	
	@SuppressWarnings("unchecked")
	public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
		return (T) deserialze(parser);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialze(DefaultJSONParser parser) {
		int token = parser.getLexer().token();
		if (token == JSONToken.NULL) {
			parser.getLexer().nextToken(JSONToken.COMMA);
			return null;
		}
		if (token == JSONToken.COMMA) {
			JSONScanner lexer = (JSONScanner) parser.getLexer();
			String val;
			lexer.nextToken();
			lexer.nextTokenWithColon(JSONToken.LITERAL_STRING);
			val = lexer.stringVal();
			lexer.nextToken(JSONToken.RBRACE);
			lexer.nextToken();
			if(StringUtils.isBlank(val)){
				return (T) Money.zero();
			}else{
				return (T) new Money(val);
			}
			
		} else {
			Object value = parser.parse();
			if (value == null) {
				return null;
			}
			if(StringUtils.isBlank(value.toString())){
				return (T) Money.zero();
			}else{
				return (T) new Money(value.toString());
			}
			
		}
	}
	
	public int getFastMatchToken() {
		return 100;
	}
	
}
