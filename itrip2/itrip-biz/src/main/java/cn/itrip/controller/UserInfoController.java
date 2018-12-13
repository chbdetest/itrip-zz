package cn.itrip.controller;

import cn.itrip.beans.dtos.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.beans.vo.userinfo.ItripAddUserLinkUserVO;
import cn.itrip.beans.vo.userinfo.ItripModifyUserLinkUserVO;
import cn.itrip.beans.vo.userinfo.ItripSearchUserLinkUserVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.ValidationToken;
import cn.itrip.service.userlinkuser.ItripUserLinkUserService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * 用户信息Controller
 * <p>
 * 包括API接口：
 * 1、根据UserId、联系人姓名查询常用联系人接口
 * 2、指定用户下添加联系人
 * 3、修改指定用户下的联系人信息
 * 4、删除指定用户下的联系人信息
 * <p>
 * 注：错误码（100401 ——100500）
 * <p>
 * Created by hanlu on 2017/5/9.
 */

@Controller
@Api(value = "API", basePath = "/http://api.itrap.com/api")
@RequestMapping(value = "/api/userinfo")
public class UserInfoController {
    private Logger logger = Logger.getLogger(UserInfoController.class);


    @Resource
    private ValidationToken validationToken;

    @Resource
    private ItripUserLinkUserService itripUserLinkUserService;


    /**
     * 根据UserId,联系人姓名查询常用联系人-add by donghai
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询常用联系人接口", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "查询常用联系人信息(可根据联系人姓名进行模糊查询)" +
            "<p>若不根据联系人姓名进行查询，不输入参数即可 | 若根据联系人姓名进行查询，须进行相应的入参，比如：{\"linkUserName\":\"张三\"}</p>" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>" +
            "<p>100401 : 获取常用联系人信息失败 </p>" +
            "<p>100000 : token失效，请重登录</p>")
    @RequestMapping(value = "/queryuserlinkuser", method = RequestMethod.POST)
    @ResponseBody
    public Dto<ItripUserLinkUser> queryuserlinkuser(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO, HttpServletRequest request) {
        String tokenString = request.getHeader("token");
        logger.info("tokenString>>>>>>>>>>>>>" + tokenString);
        logger.info("linkUserName>>>>>>>>>" + itripSearchUserLinkUserVO.getLinkUserName());
        ItripUser currentUser = validationToken.getCurrentUser(tokenString);
        List<ItripUserLinkUser> userLinkUserList = new ArrayList<ItripUserLinkUser>();
        String linkUserName = (null == itripSearchUserLinkUserVO) ? null : itripSearchUserLinkUserVO.getLinkUserName();
        Dto dto = null;
        if (null != currentUser) {
            Map param = new HashMap();
            param.put("userId", currentUser.getId());
            param.put("linkUserName", linkUserName);
            try {
                userLinkUserList = itripUserLinkUserService.getItripUserLinkUserListByMap(param);
                logger.debug("userLinkUserList size " + userLinkUserList.size());
                return DtoUtil.returnSuccess("获取常用联系人信息成功", userLinkUserList);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("获取常用联系人信息失败", "100401");
            }
        } else {
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
    }


    @ApiOperation(value = "添加常用联系人接口", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "添加常用联系人信息，需要在head中设置token" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>" +
            "<p>100401 : 获取常用联系人信息失败 </p>" +
            "<p>100000 : token失效，请重登录</p>")
    @RequestMapping(value = "/adduserlinkuser", method = RequestMethod.POST)
    @ResponseBody
    public Dto adduserlinkuser(@RequestBody ItripAddUserLinkUserVO itripAddUserLinkUserVO, HttpServletRequest request) {
        String tokenString = request.getHeader("token");
        logger.info("tokenString>>>>>>>>>>>>>" + tokenString);
        logger.info("linkUserName>>>>>>>>>" + itripAddUserLinkUserVO.getLinkUserName());
        logger.info("linkIdCard>>>>>>>>>" + itripAddUserLinkUserVO.getLinkIdCard());
        logger.info("userId>>>>>>>>>" + itripAddUserLinkUserVO.getUserId());
        ItripUser currentUser = validationToken.getCurrentUser(tokenString);
        List<ItripUserLinkUser> userLinkUserList = new ArrayList<ItripUserLinkUser>();

        ItripUserLinkUser itripUserLinkUser = new ItripUserLinkUser();
        itripUserLinkUser.setLinkUserName(itripAddUserLinkUserVO.getLinkUserName());
        itripUserLinkUser.setLinkIdCardType(itripAddUserLinkUserVO.getLinkIdCardType());
        itripUserLinkUser.setLinkIdCard(itripAddUserLinkUserVO.getLinkIdCard());
        itripUserLinkUser.setLinkPhone(itripAddUserLinkUserVO.getLinkPhone());

        int count = 0;
        if (null != currentUser) {
            itripUserLinkUser.setUserId(currentUser.getId());
            try {
                count = itripUserLinkUserService.addItripUserLinkUser(itripUserLinkUser);
                logger.debug("count " + count);
                return DtoUtil.returnSuccess("新增联系人信息成功");
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("新增常用联系人信息失败", "100401");
            }
        } else {
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
    }

    @ApiOperation(value = "根据id查询常用联系人的接口", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "添加常用联系人信息，需要在head中设置token" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>" +
            "<p>100401 : 获取常用联系人信息失败 </p>" +
            "<p>100000 : token失效，请重登录</p>")
    @RequestMapping(value = "/getItripUserLinkUserById", method = RequestMethod.POST)
    @ResponseBody
 //   Dto<ItripUserLinkUser>
    public   Dto<ItripUserLinkUser> getItripUserLinkUserById(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO, HttpServletRequest request) throws Exception {
        System.out.println("id----------------"+itripSearchUserLinkUserVO.getId());

        List<ItripUserLinkUser> userLinkUserList = new ArrayList<ItripUserLinkUser>();
        userLinkUserList.add(itripUserLinkUserService.getItripUserLinkUserById(itripSearchUserLinkUserVO.getId()));
        System.out.println("userLinkUserList size " + userLinkUserList.size()+"//////////////"+userLinkUserList.get(0).getLinkPhone());
        return DtoUtil.returnSuccess("获取常用联系人信息成功", userLinkUserList);

        /*  List<ItripUserLinkUser> userLinkUserList = new ArrayList<ItripUserLinkUser>();
        userLinkUserList.add(itripUserLinkUserService.getItripUserLinkUserById(id));
        System.out.println(userLinkUserList.get(0).getLinkPhone()+"getLinkPhone------------");
        return DtoUtil.returnSuccess("获取常用联系人信息成功", userLinkUserList);*/
    }




    @ApiOperation(value = "修改常用联系人接口", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "添加常用联系人信息，需要在head中设置token" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>" +
            "<p>100401 : 获取常用联系人信息失败 </p>" +
            "<p>100000 : token失效，请重登录</p>")
    @RequestMapping(value = "/updateUserlinkuser", method = RequestMethod.POST)
    @ResponseBody
    public Dto updateUserlinkuser(@RequestBody ItripModifyUserLinkUserVO itripModifyUserLinkUserVO, HttpServletRequest request) {
        String tokenString = request.getHeader("token");
        System.out.println("tokenString>>>>>>>>>>>>>" + tokenString);
        System.out.println("linkUserName>>>>>>>>>" + itripModifyUserLinkUserVO.getLinkUserName());
        System.out.println("linkIdCard>>>>>>>>>" + itripModifyUserLinkUserVO.getLinkIdCard());
        System.out.println("userId>>>>>>>>>" + itripModifyUserLinkUserVO.getUserId());
        System.out.println("id>>>>>>>>>" + itripModifyUserLinkUserVO.getId());
        ItripUser currentUser = validationToken.getCurrentUser(tokenString);
        List<ItripUserLinkUser> userLinkUserList = new ArrayList<ItripUserLinkUser>();

        ItripUserLinkUser itripUserLinkUser = new ItripUserLinkUser();
        itripUserLinkUser.setLinkUserName(itripModifyUserLinkUserVO.getLinkUserName());
        itripUserLinkUser.setLinkIdCardType(itripModifyUserLinkUserVO.getLinkIdCardType());
        itripUserLinkUser.setLinkIdCard(itripModifyUserLinkUserVO.getLinkIdCard());
        itripUserLinkUser.setLinkPhone(itripModifyUserLinkUserVO.getLinkPhone());
        itripUserLinkUser.setId(itripModifyUserLinkUserVO.getId());
        itripUserLinkUser.setUserId(itripModifyUserLinkUserVO.getUserId())       ;
        int count = 0;
        if (null != currentUser) {
            itripUserLinkUser.setUserId(currentUser.getId());
            try {
                count = itripUserLinkUserService.modifyItripUserLinkUser(itripUserLinkUser);
                System.out.println("count " + count);
                return DtoUtil.returnSuccess("新增联系人信息成功");
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("新增常用联系人信息失败", "100401");
            }
        } else {
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
    }


    @ApiOperation(value = "删除常用联系人接口", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "添加常用联系人信息，需要在head中设置token" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码：</p>" +
            "<p>100401 : 获取常用联系人信息失败 </p>" +
            "<p>100000 : token失效，请重登录</p>")
    @RequestMapping(value = "/deleteItripUserLinkUserByIds", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteItripUserLinkUserByIds(@RequestBody Long [] check_val, HttpServletRequest request) throws Exception {
        System.out.println(check_val.length+">>>>>>>>>>>>>>");
        int i=itripUserLinkUserService.deleteItripUserLinkUserByIds(check_val);
        System.out.println(i+"!++++++++++++++++++1");
        return JSON.toJSON(i);
    }

}
