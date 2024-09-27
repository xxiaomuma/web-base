package pers.xiaomuma.framework.rpc.feign.interceptor;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.xiaomuma.framework.constant.GlobalRequestHeader;
import pers.xiaomuma.framework.core.global.ApplicationConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

public class RpcFeignInterceptor implements RequestInterceptor {

	private final String appName;

	public RpcFeignInterceptor(ApplicationConstant constant) {
		this.appName = Optional.ofNullable(constant.applicationName).orElse("N/A");
	}

	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (Objects.nonNull(attributes)) {
			HttpServletRequest request = attributes.getRequest();
			String token = request.getHeader(GlobalRequestHeader.Authorization);
			if (StrUtil.isNotBlank(token)) {
				template.header(GlobalRequestHeader.Authorization, token);
			}
		}
		template.header(GlobalRequestHeader.APP_INFO, appName);
	}
}
