package pers.xiaomuma.framework.thirdparty.oss.minio;

import io.minio.*;
import lombok.SneakyThrows;
import pers.xiaomuma.framework.exception.InternalServerErrorException;
import pers.xiaomuma.framework.thirdparty.oss.OSSClientAdapter;
import pers.xiaomuma.framework.thirdparty.oss.OSSProperties;
import java.io.InputStream;


public class MinioOSSClientAdapter implements OSSClientAdapter {

    private final MinioClient minioClient;

    public MinioOSSClientAdapter(OSSProperties properties) {
        this.minioClient = MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

    @SneakyThrows
    @Override
    public String upload(InputStream is, String filename, String bucket) {
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucket)
                .object(filename)
                .stream(is, is.available(), -1)
                .build();
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
        return objectWriteResponse.object();
    }

    @SneakyThrows
    @Override
    public boolean delete(String bucket, String filename) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filename)
                .build();
        minioClient.removeObject(removeObjectArgs);
        return true;
    }

    @SneakyThrows
    @Override
    public InputStream fetchInputStream(String bucket, String filename) {
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucket)
                .object(filename)
                .build();
        return minioClient.getObject(args);
    }

    @Override
    public boolean expire(String bucket, String filename, int day) {
        throw new InternalServerErrorException("暂不支持设置minio过期时间");
    }
}
