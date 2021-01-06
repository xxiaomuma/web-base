package pers.xiaomuma.web.support.oos.qiniu;

import lombok.SneakyThrows;
import pers.xiaomuma.web.support.oos.OSSClientAdapter;
import java.io.InputStream;


public class QiniuOSSClientAdapter implements OSSClientAdapter {

    private QiniuClient qiniuClient;

    public QiniuOSSClientAdapter(QiniuClient qiniuClient) {
        this.qiniuClient = qiniuClient;
    }

    @SneakyThrows
    @Override
    public String upload(InputStream is, String bucket, String fileName) {
        return qiniuClient.upload(is, bucket, fileName);
    }

    @SneakyThrows
    @Override
    public boolean delete(String bucket, String fileName) {
        return qiniuClient.delete(bucket, fileName);
    }

    @SneakyThrows
    @Override
    public InputStream fetchInputStream(String bucket, String fileName) {
        return qiniuClient.fetchInputStream(bucket, fileName);
    }
}
