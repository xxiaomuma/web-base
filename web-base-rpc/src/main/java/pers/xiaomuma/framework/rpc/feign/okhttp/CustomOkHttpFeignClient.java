package pers.xiaomuma.framework.rpc.feign.okhttp;

import feign.Client;
import feign.Response;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 自定义依赖Okhttp的FeignClient请求客户端
 * 拓展点:
 * 1. 优化: 如果Okhttp超时等配置与feign参数不一致,
 * 在请求时会频繁创建代理类, 所以这里直接使用Okhttp配置
 */
public class CustomOkHttpFeignClient implements Client {

    private final OkHttpClient delegate;

    public CustomOkHttpFeignClient() {
        this(new OkHttpClient());
    }

    public CustomOkHttpFeignClient(OkHttpClient delegate) {
        this.delegate = delegate;
    }

    static Request toOkHttpRequest(feign.Request input) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(input.url());
        MediaType mediaType = null;
        boolean hasAcceptHeader = false;
        Iterator var4 = input.headers().keySet().iterator();

        while(var4.hasNext()) {
            String field = (String)var4.next();
            if (field.equalsIgnoreCase("Accept")) {
                hasAcceptHeader = true;
            }

            Iterator var6 = ((Collection)input.headers().get(field)).iterator();

            while(var6.hasNext()) {
                String value = (String)var6.next();
                requestBuilder.addHeader(field, value);
                if (field.equalsIgnoreCase("Content-Type")) {
                    mediaType = MediaType.parse(value);
                    if (input.charset() != null) {
                        mediaType.charset(input.charset());
                    }
                }
            }
        }

        if (!hasAcceptHeader) {
            requestBuilder.addHeader("Accept", "*/*");
        }

        byte[] inputBody = input.requestBody().asBytes();
        boolean isMethodWithBody = feign.Request.HttpMethod.POST == input.httpMethod() || feign.Request.HttpMethod.PUT == input.httpMethod() || feign.Request.HttpMethod.PATCH == input.httpMethod();
        if (isMethodWithBody) {
            requestBuilder.removeHeader("Content-Type");
            if (inputBody == null) {
                inputBody = new byte[0];
            }
        }

        RequestBody body = inputBody != null ? RequestBody.create(mediaType, inputBody) : null;
        requestBuilder.method(input.httpMethod().name(), body);
        return requestBuilder.build();
    }

    private static Response toFeignResponse(okhttp3.Response response, feign.Request request) throws IOException {
        return Response.builder()
                .status(response.code())
                .reason(response.message())
                .request(request)
                .headers(toMap(response.headers()))
                .body(toBody(response.body())).build();
    }

    private static Map<String, Collection<String>> toMap(Headers headers) {
        return (Map) headers.toMultimap();
    }

    private static Response.Body toBody(final ResponseBody input) throws IOException {
        if (input != null && input.contentLength() != 0L) {
            final Integer length = input.contentLength() >= 0L && input.contentLength() <= 2147483647L ? (int)input.contentLength() : null;
            return new Response.Body() {
                @Override public void close() {input.close();}
                @Override public Integer length() {return length;}
                @Override public boolean isRepeatable() {return false;}
                @Override public InputStream asInputStream() {return input.byteStream();}
                @Override public Reader asReader() {return input.charStream();}
                @Override public Reader asReader(Charset charset) {return this.asReader();}
            };
        } else {
            if (input != null) {
                input.close();
            }
            return null;
        }
    }

    @Override
    public Response execute(feign.Request input, feign.Request.Options options) throws IOException {
//      放弃因配置不同而创建新的http client
//        okhttp3.OkHttpClient requestScoped;
//        if (this.delegate.connectTimeoutMillis() == options.connectTimeoutMillis() && this.delegate.readTimeoutMillis() == options.readTimeoutMillis()) {
//            requestScoped = this.delegate;
//        } else {
//            requestScoped = this.delegate.newBuilder().connectTimeout((long)options.connectTimeoutMillis(), TimeUnit.MILLISECONDS).readTimeout((long)options.readTimeoutMillis(), TimeUnit.MILLISECONDS).followRedirects(options.isFollowRedirects()).build();
//        }
        Request request = toOkHttpRequest(input);
        okhttp3.Response response = this.delegate.newCall(request).execute();
        return toFeignResponse(response, input).toBuilder().request(input).build();
    }

}
