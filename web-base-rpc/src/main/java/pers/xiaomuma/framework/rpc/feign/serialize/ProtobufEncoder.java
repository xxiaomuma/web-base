package pers.xiaomuma.framework.rpc.feign.serialize;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;

public class ProtobufEncoder implements Encoder {

    @Override
    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {

    }
}
