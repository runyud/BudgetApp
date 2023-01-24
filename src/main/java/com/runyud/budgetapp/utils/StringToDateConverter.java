package com.runyud.budgetapp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateConverter implements Converter<String, Date> {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Date convert(String source) {
		String dateFormat = "yyyy-MM-dd";
		DateFormat format = new SimpleDateFormat(dateFormat);
		Date convertedDate = null;
		try {
			convertedDate = format.parse(source);
		} catch (ParseException e) {
			LOGGER.error("Error parsing the date (" + source + ") it wasn't in the proper format of: " + dateFormat, e);
		}
		return convertedDate;
	}
}
