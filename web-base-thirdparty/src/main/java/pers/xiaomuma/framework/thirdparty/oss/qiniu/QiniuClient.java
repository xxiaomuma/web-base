package pers.xiaomuma.framework.thirdparty.oss.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import pers.xiaomuma.framework.exception.InternalServerErrorException;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.io.IOException;
import java.io.InputStream;


public class QiniuClient {

    private final BucketManager bucketManager;
    private final UploadManager uploadManager;
    private final Auth auth;
    private static final Configuration configuration = new Configuration(Zone.autoZone());

    public QiniuClient(String accessKey, String secretKey) {
        this.auth = Auth.create(accessKey, secretKey);
        this.uploadManager = new UploadManager(configuration);
        this.bucketManager = new BucketManager(this.auth, configuration);
    }

    public String upload(InputStream is, String bucket, String filename) {
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(is, filename, upToken, null, null);
            DefaultPutRet putRet = JsonUtils.json2Object(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException var1) {
            throw new InternalServerErrorException("上传七牛云失败", var1);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public boolean delete(String bucket, String filename) {
        Response response;
        try {
            response = bucketManager.delete(bucket, filename);
        } catch (QiniuException var) {
            throw new InternalServerErrorException("七牛云删除文件失败", var);
        }
        return response.isOK();
    }

    public InputStream fetchInputStream(String bucket, String filename) {
        throw new InternalServerErrorException("暂不支持七牛云文件下载");
    }

    public boolean expire(String bucket, String filename, int day) {
        Response response;
        try {
            response = bucketManager.deleteAfterDays(bucket, filename, day);
        } catch (QiniuException var) {
            throw new InternalServerErrorException("设置七牛云文件过期时间失败", var);
        }
        return response.isOK();
    }

}
