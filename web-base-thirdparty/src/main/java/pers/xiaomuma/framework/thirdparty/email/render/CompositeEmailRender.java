package pers.xiaomuma.framework.thirdparty.email.render;

import com.google.common.collect.Maps;

import java.util.Map;

public class CompositeEmailRender {

    private static Map<EmailBodyType, EmailBodyRenderer> rendererMap;

    static {
        rendererMap = Maps.newHashMap();
        rendererMap.put(EmailBodyType.TEXT, new TextBodyRenderer());
        rendererMap.put(EmailBodyType.RAW_HTML, new HtmlBodyRenderer());
        rendererMap.put(EmailBodyType.THYMELEAF, new ThymeleafBodyRenderer());
    }

    public String render(EmailBodyType renderType, Object body) {
        return rendererMap.get(renderType).render(body);
    }

}
