package pers.xiaomuma.framework.thirdparty.email.render;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import pers.xiaomuma.framework.thirdparty.email.ThymeleafBody;

import java.util.Map;


public class ThymeleafBodyRenderer implements EmailBodyRenderer {

    private final SpringTemplateEngine springTemplateEngine;

    public ThymeleafBodyRenderer() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("utf-8");
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(resolver);
        this.springTemplateEngine = springTemplateEngine;
    }

    public ThymeleafBodyRenderer(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    public String render(Object body) {
        ThymeleafBody thymeleafBody = (ThymeleafBody) body;
        String templateName = thymeleafBody.getTemplateName();
        Map<String, Object> contentMap = thymeleafBody.getContentVarMap();
        Context context = new Context();
        for (Map.Entry<String, Object> entry : contentMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        return springTemplateEngine.process(templateName, context);
    }

}
