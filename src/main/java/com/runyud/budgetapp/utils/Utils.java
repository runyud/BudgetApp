package com.runyud.budgetapp.utils;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class Utils {

	public static Date convertStringToDate(String date) throws ParseException {
		return DateUtils.parseDate(date, "yyyy-MM-dd");
	}
}
