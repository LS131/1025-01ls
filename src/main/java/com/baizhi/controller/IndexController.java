package com.baizhi.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping("index")
    public String toLogin(){
        return "login";
    }

}
