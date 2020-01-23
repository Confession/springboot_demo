package com.cjl.springboot_demo.dto;

import com.cjl.springboot_demo.model.User;
import lombok.Data;

/**
 * 用于将question对象和user对象结合在一起的一个类
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
