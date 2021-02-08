package pers.xiaomuma.base.thirdparty.wx.mp.bo;


import java.util.List;

public class WxMessageBodyBO {

    private String appId;
    private String templateId;
    private String url;
    private WxMessageMpPageBO miniProgram;
    private List<WxMessageContextParamBO> data;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WxMessageMpPageBO getMiniProgram() {
        return miniProgram;
    }

    public void setMiniProgram(WxMessageMpPageBO miniProgram) {
        this.miniProgram = miniProgram;
    }

    public List<WxMessageContextParamBO> getData() {
        return data;
    }

    public void setData(List<WxMessageContextParamBO> data) {
        this.data = data;
    }
}
