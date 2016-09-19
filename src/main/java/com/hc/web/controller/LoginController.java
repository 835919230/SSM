package com.hc.web.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 诚 on 2016/9/17.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    @Qualifier("userRealm")
    private Realm userRealm;

    @GetMapping("/student")
    public String loginStudent(Model model) {
        model.addAttribute("type","student");
        return "login";
    }

    @GetMapping("/teacher")
    public String loginTeacher(Model model) {
        model.addAttribute("type","teacher");
        return "login";
    }

    @GetMapping("/manager")
    public String loginManager(Model model) {
        model.addAttribute("type","manager");
        return "login";
    }

    /**
     * ----------------------------------------------------------------------
     * |Post处理区域
     * ----------------------------------------------------------------------
     */

    @PostMapping("/student")
    public String loginStudent(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        long startTime = System.currentTimeMillis();
        Subject subject = SecurityUtils.getSubject();
        logger.debug("在Controller获得的密码:"+password);
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        try {
            logger.debug(token.getCredentials().toString());
            subject.login(token);
            logger.debug("启动用时："+(System.currentTimeMillis() - startTime));
            return "redirect:/admin/student";
        } catch (AuthenticationException e) {
            logger.error(e);
            model.addAttribute("type","student")
                .addAttribute("error","用户名或密码不对");
        }
        return "login";
    }

    @PostMapping("/teacher")
    public String loginTeacher(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        String rel = loginStudent(username, password, model);
        if (!rel.equals("login"))
            return "redirect:/admin/student";
        model.addAttribute("type","teacher");
        return "login";
    }

    @PostMapping("/manager")
    public String loginManager(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        String rel = loginStudent(username, password, model);
        if (!rel.equals("login"))
            return "redirect:/admin/student";
        model.addAttribute("type","manager");
        return "login";
    }
}
