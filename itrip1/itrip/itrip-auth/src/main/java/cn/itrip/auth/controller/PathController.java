package cn.itrip.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/path")
public class PathController {
    @RequestMapping("/register/index")
    public String register_Index() {
        return "register/index";
    }
    @RequestMapping("/register/phone")
    public String register_Phone() {
        return "register/phone";
    }
    @RequestMapping("/jsp/index")
    public String jsp_Index() {

        return "jsp/index";
    }
    @RequestMapping("/jsp/getUserList")
    public String jsp_GetUserList() {
        return "jsp/getUserList";
    }
    @RequestMapping("/jsp/refrToken")
    public String jsp_RefrToken() {
        return "jsp/refrToken";
    }
    @RequestMapping("/jsp/validateToken")
    public String jsp_ValidateToken() {
        return "jsp/validateToken";
    }

}
