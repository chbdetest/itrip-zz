package cn.itrip.service.userlinkuser;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.service.user.ItripUserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestItripUserLinkUserService {

    @Test
    public void testGetItripUserLinkUserListByMap() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext-mybatis.xml");
        ItripUserLinkUserService itripUserLinkUserService = ctx.getBean(ItripUserLinkUserService.class);
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", "12");
            param.put("linkUserName","张");
            List<ItripUserLinkUser> list = itripUserLinkUserService.getItripUserLinkUserListByMap(param);
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
