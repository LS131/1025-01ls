package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录的方法
     * @param user
     * @param session
     * @return
     */
    @PostMapping("login")
    public String login(User user, HttpSession session){
        User userDB = userService.login(user);
        if(userDB!=null){
            session.setAttribute("user",userDB);
            return "redirect:/file/showAll";
        }else {
            return "redirect:/index";//直接重定向HTML页面会导致Thymeleaf语法失效
        }
    }


}
