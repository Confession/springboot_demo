package com.cjl.springboot_demo.controller;

import com.alibaba.fastjson.JSON;
import com.cjl.springboot_demo.dto.AccessTokenDTO;
import com.cjl.springboot_demo.dto.GithubUser;
import com.cjl.springboot_demo.mapper.UserMapper;
import com.cjl.springboot_demo.model.User;
import com.cjl.springboot_demo.provider.GithubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 通过github授权登录
 */
@Controller
public class AuthController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.Redirect_uri}")
    private String redirect_uri;

    @Value("${github.Client_id}")
    private String client_id;

    @Value("${github.Client_secret}")
    private String client_secret;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        try {
            String accessToken = githubProvider.getAccessToken(accessTokenDTO);
            GithubUser githubUser=githubProvider.getUser(accessToken);
            //System.out.println(user);
            if (githubUser!=null){
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                if (githubUser.getName()==null){
                    user.setName(githubUser.getLogin());//暂时使用GitHub的login属性当作name
                }else {
                    user.setName(githubUser.getName());
                }
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userMapper.insertUser(user);
                response.addCookie(new Cookie("token",token));
                return "redirect:/";
            }else {
                //登录失败，重新登录
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
