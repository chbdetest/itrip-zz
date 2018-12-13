package cn.itrip.controller.user;

import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.common.ValidationToken;
import cn.itrip.service.userlinkuser.ItripUserLinkUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-mybatis.xml")
public class TestUserInfoController {


    @Resource
    private ValidationToken validationToken;

    @Resource
    private ItripUserLinkUserService itripUserLinkUserService;


    @Test
    public void testUserInfo() throws Exception {
        testGetItripUserLinkUserById();
    }

    @Test
    public void testGetItripUserLinkUserById() throws Exception {
        ItripUserLinkUser itripUserLinkUser = itripUserLinkUserService.getItripUserLinkUserById((long) 22);
        System.out.println(itripUserLinkUser.getId() + " " + itripUserLinkUser.getLinkUserName());
    }
}
