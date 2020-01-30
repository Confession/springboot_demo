package com.cjl.springboot_demo.controller;

import com.cjl.springboot_demo.dto.PaginationDTO;
import com.cjl.springboot_demo.model.User;
import com.cjl.springboot_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          Model model,
                          @PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }
        else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO questionList =questionService.getQuestionDTOListByUserId(page,size,user.getId());
        model.addAttribute("questionList", questionList);
        return "profile";
    }
}
