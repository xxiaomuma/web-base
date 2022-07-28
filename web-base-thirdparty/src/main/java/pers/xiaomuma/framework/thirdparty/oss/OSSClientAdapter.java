package pers.xiaomuma.framework.thirdparty.oss;

import java.io.InputStream;


public interface OSSClientAdapter {

    String upload(InputStream is, String filename, String bucket);

    boolean delete(String bucket, String filename);

    InputStream fetchInputStream(String bucket, String filename);

}
