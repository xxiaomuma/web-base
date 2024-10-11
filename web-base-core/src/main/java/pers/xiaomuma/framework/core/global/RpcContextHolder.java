package pers.xiaomuma.framework.core.global;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.web.context.request.RequestAttributes;

public class RpcContextHolder {

    private static final TransmittableThreadLocal<RequestAttributes> context = new TransmittableThreadLocal<>();

    public static void setContext(RequestAttributes attributes) {
        context.set(attributes);
    }

    public static RequestAttributes getContext() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
