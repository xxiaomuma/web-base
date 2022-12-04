package pers.xiaomuma.framework.thirdparty.email.render;

import com.google.common.collect.Maps;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

public class CompositeEmailRender {

    private final Map<EmailBodyType, EmailBodyRenderer> rendererMap;

    public CompositeEmailRender() {
        this.rendererMap = Maps.newHashMap();
        this.rendererMap.put(EmailBodyType.TEXT, new TextBodyRenderer());
        this.rendererMap.put(EmailBodyType.RAW_HTML, new HtmlBodyRenderer());
        this.rendererMap.put(EmailBodyType.THYMELEAF, new ThymeleafBodyRenderer());
    }

    public CompositeEmailRender(SpringTemplateEngine springTemplateEngine) {
        this.rendererMap = Maps.newHashMap();
        this.rendererMap.put(EmailBodyType.TEXT, new TextBodyRenderer());
        this.rendererMap.put(EmailBodyType.RAW_HTML, new HtmlBodyRenderer());
        this.rendererMap.put(EmailBodyType.THYMELEAF, new ThymeleafBodyRenderer(springTemplateEngine));
    }

    public String render(EmailBodyType renderType, Object body) {
        return rendererMap.get(renderType).render(body);
    }

}
