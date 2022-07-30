package pers.xiaomuma.framework.thirdparty.email;

import java.util.Map;

public class ThymeleafBody {

    private String templateName;
    private Map<String, Object> contentVarMap;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getContentVarMap() {
        return contentVarMap;
    }

    public void setContentVarMap(Map<String, Object> contentVarMap) {
        this.contentVarMap = contentVarMap;
    }
}
