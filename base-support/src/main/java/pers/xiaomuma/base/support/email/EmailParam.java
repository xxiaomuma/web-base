package pers.xiaomuma.base.support.email;


import pers.xiaomuma.base.support.email.render.EmailBodyType;

import java.io.File;
import java.util.List;

public interface EmailParam {

    EmailBodyType getRenderType();

    String getSubject();

    Object getBody();

    List<String> getReceivers();

    List<File> getAttachFiles();
}
