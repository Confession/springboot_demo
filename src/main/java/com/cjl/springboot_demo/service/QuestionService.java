package com.cjl.springboot_demo.service;

import com.cjl.springboot_demo.dto.PaginationDTO;
import com.cjl.springboot_demo.dto.QuestionDTO;
import com.cjl.springboot_demo.mapper.QuestionMapper;
import com.cjl.springboot_demo.mapper.UserMapper;
import com.cjl.springboot_demo.model.Question;
import com.cjl.springboot_demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 实现分页
     *
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO getQuestionDTOList(Integer page, Integer size) {
        //前端展示分页需要用的元素封装对象
        PaginationDTO paginationDTO = new PaginationDTO();
        //数据总条数
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        Integer offset = (page - 1) * size;
        //获取数据库分页查询的数据
        List<Question> questions = questionMapper.getQuestionList(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            //通过question表中的createor字段去user表获取question发布人的信息
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //使用spring内置的beanUtiils类将question对象的属性set到questiondto里
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }
}
