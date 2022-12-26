package pers.xiaomuma.framework.thirdparty.oss.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.*;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import pers.xiaomuma.framework.exception.InternalServerErrorException;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomQiniuClient {

    private static final String UPLOAD_URL = "https://up-z2.qiniup.com";
    private final BucketManager bucketManager;
    private final UploadManager uploadManager;
    private final Auth auth;
    private final ApiUploadV2InitUpload initUploadApi;
    private final ApiUploadV2UploadPart uploadPartApi;
    private final ApiUploadV2CompleteUpload completeUploadApi;
    private static final Configuration configuration = new Configuration(Zone.autoZone());

    public CustomQiniuClient(String accessKey, String secretKey) {
        this.auth = Auth.create(accessKey, secretKey);
        Client client = new Client(configuration);

        this.uploadManager = new UploadManager(configuration);
        this.bucketManager = new BucketManager(this.auth, configuration);
        this.initUploadApi = new ApiUploadV2InitUpload(client);
        this.uploadPartApi = new ApiUploadV2UploadPart(client);
        this.completeUploadApi = new ApiUploadV2CompleteUpload(client);
    }

    public String upload(InputStream is, String bucket, String filename) {
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(is, filename, upToken, null, null);
            DefaultPutRet putRet = JsonUtils.json2Object(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException var) {
            throw new InternalServerErrorException("七牛云上传失败", var);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public boolean delete(String bucket, String filename) {
        Response response;
        try {
            response = bucketManager.delete(bucket, filename);
        } catch (QiniuException var) {
            throw new InternalServerErrorException("七牛云删除文件失败", var);
        }
        return response.isOK();
    }

    public InputStream fetchInputStream(String bucket, String filename) {
        throw new InternalServerErrorException("暂不支持七牛云文件下载");
    }

    public boolean expire(String bucket, String filename, int day) {
        Response response;
        try {
            response = bucketManager.deleteAfterDays(bucket, filename, day);
        } catch (QiniuException var) {
            throw new InternalServerErrorException("设置七牛云文件过期时间失败", var);
        }
        return response.isOK();
    }

    public String fetchMultipartUploadId(String bucket, String filename) {
        String upToken = auth.uploadToken(bucket);
        ApiUploadV2InitUpload.Request request = new ApiUploadV2InitUpload.Request(UPLOAD_URL, upToken)
                .setKey(filename);
        try {
            ApiUploadV2InitUpload.Response initUploadResponse = initUploadApi.request(request);
            return initUploadResponse.getUploadId();
        } catch (QiniuException var) {
            throw new InternalServerErrorException("七牛云分片初始化上传失败", var);
        }
    }

    public Map<String, Object> uploadMultipart(String bucket, String filename, String uploadId, byte[] data, Integer index, Integer offset) {
        String upToken = auth.uploadToken(bucket);
        ApiUploadV2UploadPart.Request request = new ApiUploadV2UploadPart.Request(UPLOAD_URL, upToken, uploadId, index)
                .setKey(filename)
                .setUploadData(data, offset, data.length, null);
        try {
            ApiUploadV2UploadPart.Response response = uploadPartApi.request(request);
            String etag = response.getEtag();

            Map<String, Object> currentPartMap = new HashMap<>();
            currentPartMap.put(ApiUploadV2CompleteUpload.Request.PART_NUMBER, index);
            currentPartMap.put(ApiUploadV2CompleteUpload.Request.PART_ETG, etag);
            return currentPartMap;
        } catch (QiniuException var) {
            throw new InternalServerErrorException("七牛云分片初始化上传失败", var);
        }
    }

    public String completeUploadMultipart(String bucket, String filename, String uploadId, List<Map<String, Object>> parts) {
        Map<String, Object> customParam = new HashMap<>();
        customParam.put("x:foo", "foo-Value");

        String upToken = auth.uploadToken(bucket);
        ApiUploadV2CompleteUpload.Request request = new ApiUploadV2CompleteUpload.Request(UPLOAD_URL, upToken, uploadId, parts)
                .setKey(filename)
                .setCustomParam(customParam);
        try {
            ApiUploadV2CompleteUpload.Response response = completeUploadApi.request(request);
            return response.getKey();
        } catch (QiniuException var) {
            throw new InternalServerErrorException("七牛云分片完成上传失败", var);
        }
    }

}
