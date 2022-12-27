package pers.xiaomuma.framework.thirdparty.oss.qiniu;

import pers.xiaomuma.framework.thirdparty.oss.OSSClientAdapter;
import pers.xiaomuma.framework.thirdparty.oss.OSSProperties;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class QiniuOSSClientAdapter implements OSSClientAdapter {

    private final CustomQiniuClient qiniuClient;

    public QiniuOSSClientAdapter(OSSProperties properties) {
        this.qiniuClient = new CustomQiniuClient(properties.getAccessKey(), properties.getSecretKey());
    }

    @Override
    public String upload(InputStream is, String bucket, String filename) {
        return qiniuClient.upload(is, filename, bucket);
    }

    @Override
    public boolean delete(String bucket, String filename) {
        return qiniuClient.delete(bucket, filename);
    }

    @Override
    public InputStream fetchInputStream(String bucket, String filename) {
        return qiniuClient.fetchInputStream(bucket, filename);
    }

    @Override
    public boolean expire(String bucket, String filename, int day) {
        return qiniuClient.expire(bucket, filename, day);
    }

    @Override
    public String fetchMultipartUploadId(String bucket, String filename) {
        return qiniuClient.fetchMultipartUploadId(bucket, filename);
    }

    @Override
    public Map<String, Object> uploadMultipart(String bucket, String filename, String uploadId, byte[] data, Integer index) {
        return qiniuClient.uploadMultipart(bucket, filename, uploadId, data, index, 0);
    }

    @Override
    public String completeUploadMultipart(String bucket, String filename, String uploadId, List<Map<String, Object>> parts) {
        return qiniuClient.completeUploadMultipart(bucket, filename, uploadId, parts);
    }

    @Override
    public boolean deleteUploadMultipart(String bucket, String uploadId) {
        return qiniuClient.deleteUploadMultipart(bucket, uploadId);
    }


}
