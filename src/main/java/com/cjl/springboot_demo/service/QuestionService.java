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
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

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

    /**
     * 我的问题分页
     *
     * @param page
     * @param size
     * @param userId
     * @return
     */
    public PaginationDTO getQuestionDTOListByUserId(Integer page, Integer size, Integer userId) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        //查询为空的时候？
        Integer offset = (page - 1) * size;
        //获取数据库分页查询的数据
        List<Question> questions = questionMapper.getQuestionListByUserId(offset, size, userId);
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

    /**
     * 根据问题id获取问题详情
     *
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        User user = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
