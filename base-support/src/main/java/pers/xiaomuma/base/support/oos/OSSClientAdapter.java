package pers.xiaomuma.base.support.oos;

import java.io.InputStream;

public interface OSSClientAdapter {

    String upload(InputStream is, String bucket, String fileName);

    boolean delete(String bucket, String fileName);

    InputStream fetchInputStream(String bucket, String fileName);
}
