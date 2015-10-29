package org.defence.tools;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by root on 29.10.15.
 */
public class DateConverter {
	public static LocalDate toLocalDate(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
				.toLocalDate();
	}

	public static Date toDate(LocalDate date) {
		Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
		return Date.from(instant);
	}
}
