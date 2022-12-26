package pers.xiaomuma.framework.thirdparty.oss;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class OSSClient {

    private final OSSClientConfig clientConfig;
    private final OSSClientAdapter clientAdapter;

    public OSSClient(OSSClientAdapter clientAdapter) {
        OSSClientConfig clientConfig = new OSSClientConfig();
        clientConfig.setDefaultBucket("default");
        this.clientConfig = clientConfig;
        this.clientAdapter = clientAdapter;
    }

    public OSSClient(OSSClientConfig clientConfig, OSSClientAdapter clientAdapter) {
        this.clientConfig = clientConfig;
        this.clientAdapter = clientAdapter;
    }

    public String upload(InputStream is, String filename) {
        return this.upload(is, clientConfig.getDefaultBucket(), filename);
    }

    public String upload(InputStream is, String bucket, String filename) {
        return clientAdapter.upload(is, bucket, filename);
    }

    public boolean delete(String filename) {
        return this.delete(clientConfig.getDefaultBucket(), filename);
    }

    public boolean delete(String bucket, String filename) {
        return clientAdapter.delete(bucket, filename);
    }

    public InputStream fetchInputStream(String filename) {
        return this.fetchInputStream(clientConfig.getDefaultBucket(), filename);
    }

    public InputStream fetchInputStream(String bucket, String filename) {
        return clientAdapter.fetchInputStream(bucket, filename);
    }

    public boolean expire(String filename, int day) {
        return clientAdapter.expire(clientConfig.getDefaultBucket(), filename, day);
    }

    public boolean expire(String bucket, String filename, int day) {
        return clientAdapter.expire(bucket, filename, day);
    }

    public String fetchMultipartUploadId(String filename) {
        return this.fetchMultipartUploadId(clientConfig.getDefaultBucket(), filename);
    }

    public String fetchMultipartUploadId(String bucket, String filename) {
        return clientAdapter.fetchMultipartUploadId(bucket, filename);
    }

    public Map<String, Object> uploadMultipart(String filename, String uploadId, byte[] data, Integer index) {
        return this.uploadMultipart(clientConfig.getDefaultBucket(), filename, uploadId, data, index);
    }

    public Map<String, Object> uploadMultipart(String bucket, String filename, String uploadId, byte[] data, Integer index) {
        return clientAdapter.uploadMultipart(bucket, filename, uploadId, data, index);
    }

    public String completeUploadMultipart(String filename, String uploadId, List<Map<String, Object>> parts) {
        return this.completeUploadMultipart(clientConfig.getDefaultBucket(), filename, uploadId, parts);
    }

    public String completeUploadMultipart(String bucket, String filename, String uploadId, List<Map<String, Object>> parts) {
        return clientAdapter.completeUploadMultipart(bucket, filename, uploadId, parts);
    }

    public static class OSSClientConfig {

        private String defaultBucket;

        public String getDefaultBucket() {
            return defaultBucket;
        }

        public void setDefaultBucket(String defaultBucket) {
            this.defaultBucket = defaultBucket;
        }
    }

}
