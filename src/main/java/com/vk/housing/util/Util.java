package com.vk.housing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.json.JSONObject;

public class Util {

	public static String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static String getDateTimeFormat() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static Date getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date currentDate = new Date();
		try {
			currentDate = dateFormat.parse(dtf.format(now));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentDate;
	}

	public static JSONObject errorJsonObject(String errorMessage, boolean status, int error_id) {
		JSONObject errorObject = new JSONObject();
		errorObject.put("errorStatus", status);
		errorObject.put("errorMessage", errorMessage);
		errorObject.put("errorId", error_id);
		return errorObject;
	}

	public static Date convertToDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date convertDate = null;
		try {
			convertDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertDate;
	}

}
