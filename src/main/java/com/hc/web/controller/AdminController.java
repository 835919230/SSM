package com.hc.web.controller;

import com.hc.model.Role;
import com.hc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * Created by 诚 on 2016/9/17.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/student")
    public String viewStudent(Model model) {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        Set<Role> userRoles = userService.findUserRoles(username);
        for (Role role : userRoles) {
            if (role.getRole().equals("student"));
            return "student/student";
        }
        model.addAttribute("type","student");
        return "login";
    }

    @GetMapping("/teacher")
    public String viewTeacher(Model model){
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        Set<Role> userRoles = userService.findUserRoles(username);
        for (Role role : userRoles) {
            if (role.getRole().equals("student"));
            return "teacher/teacher";
        }
        model.addAttribute("type","teacher");
        return "login";
    }
}
