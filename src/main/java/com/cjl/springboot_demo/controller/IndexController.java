package com.cjl.springboot_demo.controller;

import com.cjl.springboot_demo.dto.PaginationDTO;
import com.cjl.springboot_demo.dto.QuestionDTO;
import com.cjl.springboot_demo.mapper.UserMapper;
import com.cjl.springboot_demo.model.User;
import com.cjl.springboot_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    //获取浏览器中cookie存放的token在数据库查找是否有对应的用户
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            //System.out.println("111");
            return "index";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                //如果查到有该user，则存入session
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        //获取页面数据（包括分页）
        PaginationDTO questionList = questionService.getQuestionDTOList(page, size);
        model.addAttribute("questionList", questionList);
        return "index";
    }
}
