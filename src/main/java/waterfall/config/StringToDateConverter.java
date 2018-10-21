package waterfall.config;

import java.sql.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateConverter implements Converter<String, Date>{
	
 	@Override
	public Date convert(String str) {
		return null;
	}
 }