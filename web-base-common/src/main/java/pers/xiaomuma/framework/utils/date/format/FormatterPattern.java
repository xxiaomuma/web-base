package pers.xiaomuma.framework.utils.date.format;

public enum  FormatterPattern {

	/** 标准日期格式：yyyy-MM-dd */
	NORM_DATE("yyyy-MM-dd"),
	/** 标准时间格式：HH:mm:ss */
	NORM_TIME("HH:mm:ss"),
	/** 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm */
	NORM_DATETIME_MINUTE("yyyy-MM-dd HH:mm"),
	/** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
	NORM_DATETIME( "yyyy-MM-dd HH:mm:ss"),
	/** 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS */
	NORM_DATETIME_MS("yyyy-MM-dd HH:mm:ss.SSS"),
	/** 标准日期格式：yyyy年MM月dd日 */
	CHINESE_DATE_PATTERN("yyyy年MM月dd日"),
	/** 标准日期格式：yyyyMMdd */
	PURE_DATE_PATTERN("yyyyMMdd"),
	/** 标准日期格式：HHmmss */
	PURE_TIME_PATTERN("HHmmss"),
	/** 标准日期格式：yyyyMMddHHmmss */
	PURE_DATETIME_PATTERN("yyyyMMddHHmmss"),
	/** 标准日期格式：yyyyMMddHHmmssSSS */
	PURE_DATETIME_MS_PATTERN("yyyyMMddHHmmssSSS"),
	//-------------------------------------------------------------------------------------------------------------------------------- Others
	/** HTTP头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z */
	HTTP_DATETIME_PATTERN("EEE, dd MMM yyyy HH:mm:ss z"),
	/** JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy */
	JDK_DATETIME_PATTERN("EEE MMM dd HH:mm:ss zzz yyyy"),
	/** UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z' */
	UTC_PATTERN("yyyy-MM-dd'T'HH:mm:ss'Z'"),
	/** UTC时间：yyyy-MM-dd'T'HH:mm:ssZ */
	UTC_WITH_ZONE_OFFSET_PATTERN("yyyy-MM-dd'T'HH:mm:ssZ"),
	/** UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z' */
	UTC_MS_PATTERN("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
	/** UTC时间：yyyy-MM-dd'T'HH:mm:ssZ */
	UTC_MS_WITH_ZONE_OFFSET_PATTERN("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	private String patternValue;

	FormatterPattern(String patternValue) {
		this.patternValue = patternValue;
	}

	public String getPatternValue() {
		return patternValue;
	}

}
