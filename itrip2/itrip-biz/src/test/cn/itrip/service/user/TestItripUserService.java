package cn.itrip.service.user;

import cn.itrip.beans.pojo.ItripUser;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestItripUserService {
    @Test
    public void testAddUser() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext-mybatis.xml");
        ItripUserService itripUserService = ctx.getBean(ItripUserService.class);
        try {
            ItripUser itripUser = itripUserService.getItripUserById((long)12);
            System.out.println(itripUser.getId() + " " + itripUser.getUserCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
