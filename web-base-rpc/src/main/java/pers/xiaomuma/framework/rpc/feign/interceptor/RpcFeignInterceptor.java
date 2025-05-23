package pers.xiaomuma.framework.rpc.feign.interceptor;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.xiaomuma.framework.constant.GlobalRequestHeader;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import pers.xiaomuma.framework.core.global.RpcContextHolder;
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
		ServletRequestAttributes attributes = (ServletRequestAttributes) RpcContextHolder.getContext();
		if (Objects.nonNull(attributes)) {
			HttpServletRequest request =  attributes.getRequest();
			String token = request.getHeader(GlobalRequestHeader.AUTHORIZATION);
			if (StrUtil.isNotBlank(token)) {
				template.header(GlobalRequestHeader.AUTHORIZATION, token);
			}
		}
		template.header(GlobalRequestHeader.APP_INFO, appName);
	}
}
