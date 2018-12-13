package cn.itrip.dao.user;

import cn.itrip.beans.pojo.ItripUser;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestItripUserMapper {


    @Test
    public void testGetItripUserById() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        ItripUserMapper itripUserMapper = ctx.getBean(ItripUserMapper.class);
        try {
            ItripUser itripUser = itripUserMapper.getItripUserById((long) 12);
            System.out.println(itripUser.getId() + " " + itripUser.getUserCode() + " " + itripUser.getUserPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
