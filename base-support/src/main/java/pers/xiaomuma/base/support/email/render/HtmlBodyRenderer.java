package pers.xiaomuma.base.support.email.render;



public class HtmlBodyRenderer implements EmailBodyRenderer {

    @Override
    public String render(Object body) {
        return (String) body;
    }

}
