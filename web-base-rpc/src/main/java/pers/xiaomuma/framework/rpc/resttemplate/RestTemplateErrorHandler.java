package pers.xiaomuma.framework.rpc.resttemplate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseErrorHandler;
import pers.xiaomuma.framework.rpc.error.ErrorInfo;
import pers.xiaomuma.framework.rpc.error.RemoteCallException;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        int statusCode = clientHttpResponse.getRawStatusCode();
        return statusCode>=400 && statusCode<600;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Charset charset = getCharset(response);
        byte[] responseBody = getResponseBody(response);
        String responseText = null;
        if (responseBody != null && responseBody.length > 0) {
            responseText = new String(responseBody, charset).trim();
        }
        ErrorInfo error = new ErrorInfo(500, "",  "响应失败!");
        if (StringUtils.isNotBlank(responseText)) {
            try {
                error = JsonUtils.json2Object(responseText, ErrorInfo.class);
            } catch (Exception ignore) {
            }
        }
        throw new RemoteCallException(error);
    }

    private byte[] getResponseBody(ClientHttpResponse response) {
        try {
            InputStream responseBody = response.getBody();
            if (responseBody != null) {
                return FileCopyUtils.copyToByteArray(responseBody);
            }
        }
        catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }


    private Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return (contentType != null && contentType.getCharset() != null) ? contentType.getCharset() : Charset.forName("UTF-8");
    }

}
