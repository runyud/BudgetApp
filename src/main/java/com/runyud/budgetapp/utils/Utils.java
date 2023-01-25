package com.runyud.budgetapp.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static LocalDate convertStringToDate(String date) throws ParseException {

		return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
