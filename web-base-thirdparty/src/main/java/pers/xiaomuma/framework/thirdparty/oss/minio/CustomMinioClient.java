package pers.xiaomuma.framework.thirdparty.oss.minio;

import io.minio.CreateMultipartUploadResponse;
import io.minio.ListPartsResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.messages.ListPartsResult;
import io.minio.messages.Part;
import lombok.SneakyThrows;

public class CustomMinioClient extends MinioClient {

    protected CustomMinioClient(MinioClient client) {
        super(client);
    }

    @SneakyThrows
    public CreateMultipartUploadResponse createMultipartUpload(String bucketName, String filename) {
        return super.createMultipartUpload(bucketName, null, filename, null, null);
    }

    @SneakyThrows
    public ObjectWriteResponse completeMultipartUpload(String bucketName, String filename, String uploadId, Integer maxParts) {
        ListPartsResponse listMultipart = this.listMultipart(bucketName, filename, uploadId, maxParts);
        ListPartsResult result = listMultipart.result();
        Part[] parts = result.partList().toArray(new Part[]{});
        return super.completeMultipartUpload(bucketName, null, filename, uploadId, parts, null, null);
    }

    @SneakyThrows
    public ObjectWriteResponse completeMultipartUpload(String bucketName, String filename, String uploadId, Part[] parts) {
        return super.completeMultipartUpload(bucketName, null, filename, uploadId, parts, null, null);
    }

    @SneakyThrows
    public ListPartsResponse listMultipart(String bucketName, String filename, String uploadId, Integer maxParts) {
        return super.listParts(bucketName, null, filename, maxParts, 0, uploadId, null, null);
    }
}
