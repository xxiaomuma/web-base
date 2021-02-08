package pers.xiaomuma.base.thirdparty.wx.mp.bo;

import pers.xiaomuma.base.common.utils.JsonUtils;


public class WxAcodeUnlimitedBO {

    private String scene;
    private String page;
    private String width;

    public String toJson() {
        return JsonUtils.object2Json(this);
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
