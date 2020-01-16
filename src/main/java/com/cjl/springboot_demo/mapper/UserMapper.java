package com.cjl.springboot_demo.mapper;

import com.cjl.springboot_demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insertUser(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
