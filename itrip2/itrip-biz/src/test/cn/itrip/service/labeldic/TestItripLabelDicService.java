package cn.itrip.service.labeldic;

import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.service.user.ItripUserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestItripLabelDicService {

    @Test
    public void testGetItripLabelDicListByMap() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "applicationContext-mybatis.xml");
        ItripLabelDicService itripLabelDicService = ctx.getBean(ItripLabelDicService.class);
        Map param = new HashMap();
        param.put("parentId", 16);
        List<ItripLabelDic> list = itripLabelDicService.getItripLabelDicListByMap(param);
        System.out.println(list.size());
    }
}
