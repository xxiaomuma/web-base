package pers.xiaomuma.framework.thirdparty.oss.qiniu;

import pers.xiaomuma.framework.thirdparty.oss.OSSClientAdapter;
import pers.xiaomuma.framework.thirdparty.oss.OSSProperties;
import java.io.InputStream;

public class QiniuOSSClientAdapter implements OSSClientAdapter {

    private final CustomQiniuClient qiniuClient;

    public QiniuOSSClientAdapter(OSSProperties properties) {
        this.qiniuClient = new CustomQiniuClient(properties.getAccessKey(), properties.getSecretKey());
    }

    @Override
    public String upload(InputStream is, String filename, String bucket) {
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
    public String createMultipartUpload(String bucket, String filename) {
        return null;
    }
}
