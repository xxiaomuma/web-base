package pers.xiaomuma.framework.utils.date.format;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class FormatterCache {

	private final static ConcurrentHashMap<String, DateTimeFormatter> formatterInstanceCache = new ConcurrentHashMap<>();

	public static DateTimeFormatter getSingleInstance(FormatterPattern formatterPattern) {
		DateTimeFormatter formatter = formatterInstanceCache.get(formatterPattern.name());
		if (Objects.isNull(formatter)) {
			formatter = DateTimeFormatter.ofPattern(formatterPattern.getPatternValue());
			formatterInstanceCache.put(formatterPattern.name(), formatter);
		}
		return formatter;
	}


}
