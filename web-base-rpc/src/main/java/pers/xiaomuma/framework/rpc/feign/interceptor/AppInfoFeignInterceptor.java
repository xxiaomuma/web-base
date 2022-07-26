package pers.xiaomuma.framework.rpc.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import pers.xiaomuma.framework.constant.GlobalRequestHeader;

public class AppInfoFeignInterceptor implements RequestInterceptor {

	private String appName;

	public AppInfoFeignInterceptor(String appName) {
		this.appName = StringUtils.isNotBlank(appName)? appName : "";
	}

	@Override
	public void apply(RequestTemplate template) {
		template.header(GlobalRequestHeader.APP_INFO, appName);
	}
}
