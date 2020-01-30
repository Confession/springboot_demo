package com.cjl.springboot_demo.mapper;

import com.cjl.springboot_demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update table user set name = #{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatorUrl} where account_id =#{accountId}")
    void update(User dbUser);
}
