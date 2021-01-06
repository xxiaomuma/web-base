package pers.xiaomuma.web.support.oos.minio;

import io.minio.*;
import lombok.SneakyThrows;
import pers.xiaomuma.web.support.oos.OSSClientAdapter;
import java.io.InputStream;


public class MinioOSSClientAdapter implements OSSClientAdapter {

    private MinioClient minioClient;

    public MinioOSSClientAdapter(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @SneakyThrows
    @Override
    public String upload(InputStream is, String bucket, String fileName) {
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucket)
                .object(fileName)
                .stream(is, is.available(), -1)
                .build();
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
        return objectWriteResponse.object();
    }

    @SneakyThrows
    @Override
    public boolean delete(String bucket, String fileName) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(fileName)
                .build();
        minioClient.removeObject(removeObjectArgs);
        return true;
    }

    @SneakyThrows
    @Override
    public InputStream fetchInputStream(String bucket, String fileName) {
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucket)
                .object(fileName)
                .build();
        return minioClient.getObject(args);
    }
}
