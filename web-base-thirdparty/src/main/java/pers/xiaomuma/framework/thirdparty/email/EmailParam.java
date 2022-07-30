package pers.xiaomuma.framework.thirdparty.email;


import pers.xiaomuma.framework.thirdparty.email.render.EmailBodyType;
import java.io.File;
import java.util.List;

public interface EmailParam {

    EmailBodyType getRenderType();

    String getSubject();

    Object getBody();

    List<String> getReceivers();

    List<File> getAttachFiles();

}
