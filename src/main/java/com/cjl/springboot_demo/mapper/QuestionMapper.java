package com.cjl.springboot_demo.mapper;

import com.cjl.springboot_demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
