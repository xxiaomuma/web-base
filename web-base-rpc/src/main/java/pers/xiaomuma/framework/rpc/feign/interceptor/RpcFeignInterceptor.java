package pers.xiaomuma.framework.rpc.feign.interceptor;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.xiaomuma.framework.constant.GlobalRequestHeader;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RpcFeignInterceptor implements RequestInterceptor {


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
		//template.header(GlobalRequestHeader.APP_INFO, appName);
	}
}
