package pers.xiaomuma.framework.thirdparty.oss;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


public interface OSSClientAdapter {

    String upload(InputStream is, String bucket, String filename);

    boolean delete(String bucket, String filename);

    InputStream fetchInputStream(String bucket, String filename);

    boolean expire(String bucket, String filename, int day);

    String fetchMultipartUploadId(String bucket, String filename);

    Map<String, Object> uploadMultipart(String bucket, String filename, String uploadId, InputStream is, Integer index);

    String completeUploadMultipart(String bucket, String filename, String uploadId, List<Map<String, Object>> parts);

    boolean deleteUploadMultipart(String bucket, String uploadId);
}
