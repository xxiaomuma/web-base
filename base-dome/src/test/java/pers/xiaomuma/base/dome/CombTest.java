package pers.xiaomuma.base.dome;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.xiaomuma.base.dome.service.biz.CombBizService;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DomeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CombTest {

    @Autowired
    private CombBizService combBizService;

    @Test
    public void test() {
        combBizService.findVehicle();
        combBizService.findAddress();
    }

}
