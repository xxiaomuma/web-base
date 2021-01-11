package pers.xiaomuma.base.support.oos.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import java.io.IOException;
import java.io.InputStream;


public class QiniuClient {

    private final BucketManager bucketManager;
    private final UploadManager uploadManager;
    private final Auth auth;
    private static final Configuration configuration = new Configuration(Zone.autoZone());
    private static final Gson GSON = new Gson();

    public QiniuClient(String accessKey, String secretKey) {
        this.auth = Auth.create(accessKey, secretKey);
        this.uploadManager = new UploadManager(configuration);
        this.bucketManager = new BucketManager(this.auth, configuration);
    }

    public String upload(InputStream is, String bucket, String fileName) {
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(is, fileName, upToken, null, null);
            DefaultPutRet putRet = GSON.fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException var1) {
            throw new RuntimeException("上传七牛云失败", var1);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public boolean delete(String bucket, String fileName) {
        Response response;
        try {
            response = bucketManager.delete(bucket, fileName);
        } catch (QiniuException var) {
            throw new RuntimeException("七牛云删除文件失败", var);
        }
        return response.isOK();
    }

    public InputStream fetchInputStream(String bucket, String fileName) {
        throw new RuntimeException("暂不支持七牛云文件下载");
    }
}
