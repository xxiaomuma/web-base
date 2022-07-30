package pers.xiaomuma.framework.thirdparty.email.render;

public enum EmailBodyType {

    TEXT(false, "text"),
    RAW_HTML(true, "raw_html"),
    THYMELEAF(true, "thymeleaf");

    private boolean isHtml;
    private String value;

    EmailBodyType(boolean isHtml, String value) {
        this.isHtml = isHtml;
        this.value = value;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public String value() {
        return value;
    }
}
