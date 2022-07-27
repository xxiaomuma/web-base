package pers.xiaomuma.framework.encrypt;

import org.apache.commons.lang3.StringUtils;

public class EncryptUtil {

	public static String hidePartContent(String content, int showLength) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		if (content.length()>= showLength) {
			return content;
		}
		StringBuilder builder = new StringBuilder(content.substring(0, showLength));
		builder.append(StringUtils.repeat("*", content.length() - showLength));
		builder.append(content.substring(content.length() - showLength));
		return builder.toString();
	}


}
