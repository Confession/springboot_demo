package com.cjl.springboot_demo.mapper;

import com.cjl.springboot_demo.dto.QuestionDTO;
import com.cjl.springboot_demo.model.Question;
import com.cjl.springboot_demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,comment_count,view_count,like_count,tag,creator) values (#{title},#{description},#{gmtCreate},#{gmtModified},0,0,0,#{tag},#{creator})")
    void create(Question question);
    @Select("select * from question")
    List<Question> List();
    //分页查询
    @Select("SELECT * FROM question LIMIT #{offset},#{size}")
    List<Question> getQuestionList(@Param("offset") Integer offset, @Param("size") Integer size);
    @Select("select count(1) from question")
    Integer count();
    @Select("SELECT * FROM question where creator=#{userId} LIMIT #{offset},#{size}")
    List<Question> getQuestionListByUserId(@Param("offset") Integer offset, @Param("size") Integer size, @Param("userId") Integer userId);
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);
    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);
    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}")
    void update(Question question);
}
