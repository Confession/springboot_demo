package com.cjl.springboot_demo.controller;

import com.cjl.springboot_demo.dto.PaginationDTO;
import com.cjl.springboot_demo.model.User;
import com.cjl.springboot_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "2") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length==0) {
            return "redirect:/";
        }
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        //获取页面数据（包括分页）
        PaginationDTO questionList = questionService.getQuestionDTOList(page, size);
        model.addAttribute("questionList", questionList);
        return "index";
    }
}
