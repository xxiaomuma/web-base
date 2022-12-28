package pers.xiaomuma.framework.thirdparty.oss.minio;

import io.minio.*;
import lombok.SneakyThrows;
import pers.xiaomuma.framework.exception.InternalServerErrorException;
import pers.xiaomuma.framework.thirdparty.oss.OSSClientAdapter;
import pers.xiaomuma.framework.thirdparty.oss.OSSProperties;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class MinioOSSClientAdapter implements OSSClientAdapter {

    private final MinioClient minioClient;

    public MinioOSSClientAdapter(OSSProperties properties) {
        this.minioClient = MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

    @Override
    public String upload(InputStream is, String bucket, String filename) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(filename)
                    .stream(is, is.available(), -1)
                    .build();
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
            return objectWriteResponse.object();
        } catch (Exception var) {
            throw new InternalServerErrorException("minio上传失败", var);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public boolean delete(String bucket, String filename) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filename)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception var) {
            throw new InternalServerErrorException("minio删除文件失败", var);
        }
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

    @Override
    public String fetchMultipartUploadId(String bucket, String filename) {
        throw new InternalServerErrorException("暂不支持minio分片上传");
    }

    @Override
    public Map<String, Object> uploadMultipart(String bucket, String filename, String uploadId, byte[] data, Integer index) {
        throw new InternalServerErrorException("暂不支持minio分片上传数据");
    }

    @Override
    public String completeUploadMultipart(String bucket, String filename, String uploadId, List<Map<String, Object>> parts) {
        throw new InternalServerErrorException("暂不支持minio分片上传完成");
    }

    @Override
    public boolean deleteUploadMultipart(String bucket, String uploadId) {
        throw new InternalServerErrorException("暂不支持minio分片删除");
    }


}
