package com.github.flux.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

/**
 * springMVC返回json时间格式化
 * 解决SpringMVC使用@ResponseBody返回json时，日期格式默认显示为时间戳的问题。
 * 需要在get方法上加上@JsonSerialize(using=JsonDateSerializer.class)
 */
@Component
public class JsonDate4MMDD extends JsonSerializer<Date>{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {

		String formattedDate = dateFormat.format(date);

		gen.writeString(formattedDate);
	}
}
