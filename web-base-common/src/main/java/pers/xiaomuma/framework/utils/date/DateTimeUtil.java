package pers.xiaomuma.framework.utils.date;


import pers.xiaomuma.framework.utils.date.format.FormatterCache;
import pers.xiaomuma.framework.utils.date.format.FormatterPattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeUtil {

	public static String formatDateTime(LocalDateTime dateTime) {
		return formatDateTime(dateTime, FormatterPattern.NORM_DATETIME);
	}

	public static String formatDateTime(LocalDateTime dateTime, FormatterPattern formatterPattern) {
		if (Objects.isNull(formatterPattern)) {
			return null;
		}
		return formatDateTime(dateTime, FormatterCache.getSingleInstance(formatterPattern));
	}

	public static String formatDateTime(LocalDateTime dateTime, DateTimeFormatter dateTimeFormatter) {
		if (Objects.isNull(dateTime) || Objects.isNull(dateTimeFormatter)) {
			return null;
		}
		return dateTime.format(dateTimeFormatter);
	}

}
