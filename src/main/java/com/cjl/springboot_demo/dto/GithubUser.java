package com.cjl.springboot_demo.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String login;
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;
}
