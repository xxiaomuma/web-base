package pers.xiaomuma.framework.thirdparty.oss;

import java.io.InputStream;


public class OSSClient {

    private OSSClientConfig clientConfig;
    private OSSClientAdapter clientAdapter;

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

    public boolean expire(String bucket, String filename, int day) {
        return clientAdapter.expire(bucket, filename, day);
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
