package pers.xiaomuma.base.support.oos;

import java.io.InputStream;

public class OSSClient {

    private OSSClientConfig ossClientConfig;
    private OSSClientAdapter ossClientAdapter;

    public OSSClient(OSSClientAdapter ossClientAdapter) {
        OSSClientConfig config = new OSSClientConfig();
        config.setDefaultBucket("default");
        this.ossClientConfig = config;
        this.ossClientAdapter = ossClientAdapter;
    }

    public OSSClient(OSSClientAdapter ossClientAdapter, OSSClientConfig config) {
        this.ossClientConfig = config;
        this.ossClientAdapter = ossClientAdapter;
    }

    public String upload(InputStream is, String filename) {
        return this.upload(is, ossClientConfig.getDefaultBucket(), filename);
    }

    public String upload(InputStream is, String bucket, String filename) {
        return ossClientAdapter.upload(is, bucket, filename);
    }

    public boolean delete(String filename) {
        return this.delete(ossClientConfig.getDefaultBucket(), filename);
    }

    public boolean delete(String bucket, String filename) {
        return ossClientAdapter.delete(bucket, filename);
    }

    public InputStream fetchInputStream(String filename) {
        return this.fetchInputStream(ossClientConfig.getDefaultBucket(), filename);
    }

    public InputStream fetchInputStream(String bucket, String filename) {
        return ossClientAdapter.fetchInputStream(bucket, filename);
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
