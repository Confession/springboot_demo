package com.cjl.springboot_demo.controller;

import com.cjl.springboot_demo.dto.QuestionDTO;
import com.cjl.springboot_demo.mapper.QuestionMapper;
import com.cjl.springboot_demo.model.Question;
import com.cjl.springboot_demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jws.WebParam;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO=questionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
