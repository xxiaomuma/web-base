package pers.xiaomuma.base.dome;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.xiaomuma.base.thirdparty.wx.WxResult;
import pers.xiaomuma.base.thirdparty.wx.mp.WxMpAPI;
import pers.xiaomuma.base.thirdparty.wx.mp.bo.WxAccessTokenBO;

@ActiveProfiles("dev")
@SpringBootTest(classes = DomeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class WxMpTest {

    @Autowired
    private WxMpAPI wxMpAPI;

    @Test
    public void test() {
        WxResult<WxAccessTokenBO> result = wxMpAPI.getAccessToken();
        System.out.println(result);
    }
}
