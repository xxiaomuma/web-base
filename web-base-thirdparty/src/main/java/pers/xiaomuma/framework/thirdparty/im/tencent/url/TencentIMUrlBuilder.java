package pers.xiaomuma.framework.thirdparty.im.tencent.url;

public class TencentIMUrlBuilder {

    public static TencentIMAccountImportUrlBuilder accountImportUrlBuilder() {
        return new TencentIMAccountImportUrlBuilder();
    }

    public static TencentIMAccountModifyUrlBuilder accountModifyUrlBuilder() {
        return new TencentIMAccountModifyUrlBuilder();
    }

    public static TencentIMFriendAddBuilder friendAddBuilder() {
        return new TencentIMFriendAddBuilder();
    }

    public static TencentIMFriendRemoveBuilder friendRemoveBuilder() {
        return new TencentIMFriendRemoveBuilder();
    }

}
