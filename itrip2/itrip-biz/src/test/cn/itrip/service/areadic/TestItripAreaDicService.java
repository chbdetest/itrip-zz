package cn.itrip.service.areadic;

import cn.itrip.beans.pojo.ItripAreaDic;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestItripAreaDicService {

    @Test
    public void testGetItripAreaDicListByMap() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext-mybatis.xml");
        ItripAreaDicService itripAreaDicService = ctx.getBean(ItripAreaDicService.class);
        Map param = new HashMap();
        param.put("isHot", 1);
        param.put("isChina", 1);
        List<ItripAreaDic> list = itripAreaDicService.getItripAreaDicListByMap(param);
        System.out.println(list.size());
    }
}
