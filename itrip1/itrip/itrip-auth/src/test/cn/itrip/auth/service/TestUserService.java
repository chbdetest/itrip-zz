package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestUserService {

/*    @Test
    public void testAddUser() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        UserService userService = ctx.getBean(UserService.class);
        ItripUser user=new ItripUser();
        user.setUserCode("itrip@163.com");
        user.setUserPassword("123456");
        user.setUserName("");
        try {
            userService.itriptxCreateUser(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    @Test
    public void testfindAll() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        UserService userService = ctx.getBean(UserService.class);
        List<ItripUser> list = null;
        try {
            list = userService.findAll();
            for (ItripUser itripUser : list) {
                System.out.println(itripUser.getId() + " " + itripUser.getUserCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
