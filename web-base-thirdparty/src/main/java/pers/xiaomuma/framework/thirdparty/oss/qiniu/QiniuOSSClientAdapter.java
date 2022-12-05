package pers.xiaomuma.framework.thirdparty.oss.qiniu;

import lombok.SneakyThrows;
import pers.xiaomuma.framework.thirdparty.oss.OSSClientAdapter;
import pers.xiaomuma.framework.thirdparty.oss.OSSProperties;

import java.io.InputStream;

public class QiniuOSSClientAdapter implements OSSClientAdapter {

    private final QiniuClient qiniuClient;

    public QiniuOSSClientAdapter(OSSProperties properties) {
        this.qiniuClient = new QiniuClient(properties.getAccessKey(), properties.getSecretKey());
    }

    @SneakyThrows
    @Override
    public String upload(InputStream is, String filename, String bucket) {
        return qiniuClient.upload(is, filename, bucket);
    }

    @SneakyThrows
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
}
