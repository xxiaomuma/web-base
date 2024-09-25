package pers.xiaomuma.framework.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * HTTP 状态码
 * 6xx 业务异常
 * 7xx 自定义认证异常
 * 9xx 参数异常
 *
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ResponseCode {

    SUCCESS(HttpResponseCode.SC_OK, "OK"),
    UN_AUTHORIZED(HttpResponseCode.SC_UNAUTHORIZED, "未授权"),
    NOT_FOUND(HttpResponseCode.SC_NOT_FOUND, "页面不存在"),
    METHOD_NOT_ALLOWED(HttpResponseCode.SC_METHOD_NOT_ALLOWED, "请求方法不支持"),
    INTERNAL_SERVER_ERROR(HttpResponseCode.SC_INTERNAL_SERVER_ERROR, "服务器错误"),
    APP_BIZ_ERROR(601, "普通业务异常"),
    EXPIRED_AUTHORIZE(701, "认证失效"),
    DENIED_AUTHORIZE(701, "权限不足"),
    PARAM_IS_VALID(901, "参数无效"),
    PARAM_IS_BLANK(902, "参数为空"),
    PARAM_TYPE_BIND_ERROR(903, "参数类型错误"),
    PARAM_MISS(904, "参数缺失"),
    SERVER_RPC_ERROR(999, "服务RPC调用异常");

    private final int code;

    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCode valueOf(int codeNum) {
        for (ResponseCode resultCode : values()) {
            if (resultCode.code == codeNum) {
                return resultCode;
            }
        }
        return null;
    }

}
